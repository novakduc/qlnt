package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.query.finance_tab.CostAmount;
import com.novakduc.forbega.qlnt.data.query.finance_tab.LoanAmountFinanceTabView;

import java.util.List;

public class FinanceFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<List<LoanAmountFinanceTabView>> mLoanAmounts;
    private LiveData<List<CostAmount>> mCostAmounts;

    public FinanceFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mLoanAmounts = mProjectRepo.getAllLoanAmount();
        mCostAmounts = mProjectRepo.getAllCostAmount();
    }

    public LiveData<List<LoanAmountFinanceTabView>> getAllLoanAmount() {
        return mLoanAmounts;
    }

    public LiveData<List<CostAmount>> getAllCostAmount() {
        return mCostAmounts;
    }
}
