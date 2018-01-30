package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.Cost;
import com.novakduc.forbega.qlnt.data.database.CostManager;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomList;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.thanh on 9/20/2016.
 */
public class QlntRepository {
    private static final String LOG_TAG = QlntRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static QlntRepository sInstance;
    private AppDao mAppDao;
    private AppExecutors mExecutors;

    private QlntRepository(AppDao appDao, AppExecutors executors) {
        this.mAppDao = appDao;
        this.mExecutors = executors;

    }

    public static synchronized QlntRepository getInstance(AppDao appDao, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new QlntRepository(appDao, executors);
                }
            }
        }
        return sInstance;
    }

    public void addProject(Project project) {
        mAppDao.insert(project);
    }


    public LiveData<List<Project>> getProjectList() {
        return mAppDao.getAllProjects();
    }

    public void deleteProject(final long projectId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                UnitPrice unitPrice = mAppDao.getUnitPrice(projectId);
                //Remove unit price data entity in project
                if (unitPrice != null) {
                    mAppDao.removeUnitPrice(unitPrice);
                }
                //remove loans entity in project
                deleteLoanList(projectId);

                //remove rooms entity in project
                deleteRoomList(projectId);

                //remove cost manager entity in project
                deleteCostManager(projectId);

                //remove project entity
                Project project = mAppDao.getProject(projectId);
                if (project != null) {
                    mAppDao.removeProject(project);
                }
            }
        });
    }

    private void deleteLoanList(long projectId) {
        List<Loan> loans = mAppDao.getLoanList(projectId);
        if (loans != null) {
            for (Loan loan :
                    loans) {
                        mAppDao.removeLoan(loan);  //remove loan entity
                }
        }
    }

    private void deleteRoomList(long projectId) {
        RoomList roomList = mAppDao.getRoomList(projectId);
        if (roomList != null) {
            ArrayList<Long> roomIdList = roomList.getIdList();
            if (roomIdList != null) {
                for (long roomId :
                        roomIdList) {
                    RoomForRent roomForRent = mAppDao.getRoomById(roomId);
                    if (roomForRent != null) {
                        mAppDao.removeRoomForRent(roomForRent);  //remove loan entity
                    }
                }
            }
            mAppDao.removeRoomList(roomList);  //remove loan list entity
        }
    }

    private void deleteCostManager(long projectId) {
        CostManager costManager = mAppDao.getCostManager(projectId);
        if (costManager != null) {
            ArrayList<Long> costIdList = costManager.getIdList();
            if (costIdList != null) {
                for (long costId :
                        costIdList) {
                    Cost cost = mAppDao.getCostById(costId);
                    if (cost != null) {
                        mAppDao.removeCost(cost);  //remove loan entity
                    }
                }
            }
            mAppDao.removeCostManger(costManager);  //remove loan list entity
        }
    }

    public void copyProject(final long projectId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Project project = (Project) mAppDao.getProject(projectId).clone();
                    mAppDao.insert(project);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
            }
        });
    }

    public LiveData<Project> createTempProject() {
        final MutableLiveData<Project> projectMutableLiveData = new MutableLiveData<Project>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Project project = Project.getInstance("name", "address", -1);
                project.setProjectId(mAppDao.insert(project));
                projectMutableLiveData.postValue(project);
            }
        });
        return projectMutableLiveData;
    }

    public void updateProject(final Project project) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.updateProject(project);
            }
        });
    }
}
