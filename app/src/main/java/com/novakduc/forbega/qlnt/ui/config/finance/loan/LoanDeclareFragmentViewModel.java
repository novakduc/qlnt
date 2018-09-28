package com.novakduc.forbega.qlnt.ui.config.finance.loan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.Loan;

/**
 * Created by Novak on 1/28/2018.
 */

public class LoanDeclareFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<Loan> mLoanLiveData;
    private boolean isNew;

    public LoanDeclareFragmentViewModel(ProjectRepo projectRepo, boolean isNew) {
        mProjectRepo = projectRepo;
        //mLoanLiveData = mProjectRepo.createTempLoan();
        this.isNew = isNew;
    }

    public LiveData<Loan> getLoanLiveData() {
        mLoanLiveData = mProjectRepo.createTempLoan();
        return mLoanLiveData;
    }

    public LiveData<Loan> getLoanLiveData(long loanId) {
        mLoanLiveData = mProjectRepo.getLoan(loanId);
        return mLoanLiveData;
    }

    public void updateLoan(Loan loan) {
        mProjectRepo.updateLoan(loan);
        isNew = false;
    }

    @Override
    protected void onCleared() {
            mProjectRepo.cleanLoanData();
        super.onCleared();
    }
}
