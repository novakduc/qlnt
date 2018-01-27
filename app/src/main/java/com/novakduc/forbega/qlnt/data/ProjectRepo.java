package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Novak on 1/14/2018.
 */

public class ProjectRepo {
    private static final String LOG_TAG = ProjectRepo.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static ProjectRepo sInstance;
    private AppDao mAppDao;
    private AppExecutors mExecutors;
    private long mProjectId;

    private ProjectRepo(AppDao appDao, AppExecutors executors, long projectId) {
        this.mAppDao = appDao;
        this.mExecutors = executors;
        this.mProjectId = projectId;
    }

    public static synchronized ProjectRepo getInstance(
            AppDao appDao, AppExecutors executors, long projectId) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new ProjectRepo(appDao, executors, projectId);
                }
            }
        }
        return sInstance;
    }

    public long getProjectId() {
        return mProjectId;
    }

    public void setProjectId(long projectId) {
        mProjectId = projectId;
    }

    public LiveData<Project> getProject() {
        return mAppDao.getLiveDataProject(mProjectId);
    }

    public LiveData<List<Loan>> getLoanList() {
        final MutableLiveData<List<Loan>> liveData = new MutableLiveData<List<Loan>>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                LoanList loans = mAppDao.getLoanList(mProjectId);
                if (loans != null) {
                    ArrayList<Long> idList = loans.getIdList();
                    for (long id :
                            idList) {
                        loans.add(mAppDao.getLoanById(mProjectId));
                    }
                    liveData.postValue(loans);
                }
            }
        });
        return liveData;
    }

    public void deleteLoan(final long loanId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Loan loan = mAppDao.getLoanById(loanId);
                if (loan != null) {
                    mAppDao.removeLoan(loan);
                }
            }
        });
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
