package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.Project;
import com.novakduc.forbega.qlnt.data.database.UnitPrice;

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
}
