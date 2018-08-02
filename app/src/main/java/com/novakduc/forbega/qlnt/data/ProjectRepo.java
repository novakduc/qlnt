package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;
import com.novakduc.forbega.qlnt.data.query.finance_tab.CostAmount;
import com.novakduc.forbega.qlnt.data.query.finance_tab.LoanAmountFinanceTabView;
import com.novakduc.forbega.qlnt.data.query.room_list_tab.GuestForRoomItemView;
import com.novakduc.forbega.qlnt.data.query.room_list_tab.ListViewRoomItem;

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
                    Log.d(LOG_TAG, "Create new product repo " + projectId);
                } else {
                    sInstance.setProjectId(projectId);
                    Log.d(LOG_TAG, "Lock repo to project id" + sInstance.getProjectId());
                }
            }
        } else {
            sInstance.setProjectId(projectId);
            Log.d(LOG_TAG, "Lock repo to project id" + sInstance.getProjectId());
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
        return mAppDao.getAllLoanInProject(mProjectId);
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

    public LiveData<Loan> createTempLoan() {
        final MutableLiveData<Loan> loanMutableLiveData = new MutableLiveData<Loan>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Loan loan = Loan.getInstance("name", mProjectId);
                loan.setId(mAppDao.insert(loan));
                loanMutableLiveData.postValue(loan);
            }
        });
        return loanMutableLiveData;
    }

    public void updateLoan(final Loan loan) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.updateLoan(loan);
                // TODO: 1/29/2018 update loan amount if needed
            }
        });
    }

    public LiveData<Loan> getLoan(long loanId) {
        return mAppDao.getLiveDataLoanById(loanId);
    }

    public void cleanLoanData() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Loan> loans = mAppDao.getInvalidLoans();
                if (loans != null) {
                    for (Loan l :
                            loans) {
                        mAppDao.removeLoan(l);
                    }
                }
            }
        });
    }

    public void addLoan(final Loan loan) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.insert(loan);
            }
        });
    }

    public LiveData<UnitPrice> getUnitPrice() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                UnitPrice unitPrice = mAppDao.getUnitPrice(mProjectId);
                if (unitPrice == null) {
                    mAppDao.insert(UnitPrice.getInstance(mProjectId));
                }
            }
        });
        return mAppDao.getUnitPriceLiveData(mProjectId);
    }

    public void updateUnitPrice(final UnitPrice unitPrice) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.updateUnitPrice(unitPrice);
            }
        });
    }

    public LiveData<List<ListViewRoomItem>> getListViewRoomItems() {
        return mAppDao.getListViewRoomItems(mProjectId);
    }

    public LiveData<List<GuestForRoomItemView>> getAllKeyContact() {
        return mAppDao.getAllKeyContact(mProjectId);
    }

    public void deleteRoom(final long roomId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Delete guest
                List<Guest> guests = mAppDao.getGuestsByRoom(roomId);
                if (guests != null) {
                    for (Guest guest :
                            guests) {
                        mAppDao.removeGuest(guest);
                    }
                }
                // TODO: 5/8/2018

                RoomForRent roomForRent = mAppDao.getRoomById(roomId);
                if (roomForRent != null) {
                    mAppDao.removeRoomForRent(roomForRent);
                }
            }
        });
    }

    public LiveData<RoomForRent> createTempRoom() {
        final MutableLiveData<RoomForRent> roomForRentMutableLiveData = new MutableLiveData<RoomForRent>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                RoomForRent roomForRent = RoomForRent.getInstance(mProjectId, "", -1);
                roomForRent.setId(mAppDao.insert(roomForRent));
                roomForRentMutableLiveData.postValue(roomForRent);
            }
        });
        return roomForRentMutableLiveData;
    }

    public void updateRoomForRent(final RoomForRent roomForRent) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.updateRoomForRent(roomForRent);
            }
        });
    }

    public LiveData<RoomForRent> getLiveDataRoomById(long roomId) {
        return mAppDao.getLiveDataRoomById(roomId);
    }

    public LiveData<List<LoanAmountFinanceTabView>> getAllLoanAmount() {

        return mAppDao.getAllLoanAmount(mProjectId);
    }

    public LiveData<List<CostAmount>> getAllCostAmount() {
        return mAppDao.getAllCostAmount(mProjectId);
    }
}
