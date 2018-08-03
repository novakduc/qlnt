package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import java.util.List;

public class FinanceFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<List<RecentBill>> mRecentBills;
    private LiveData<List<RecentCost>> mRecentCost;

    public FinanceFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mRecentBills = mProjectRepo.getRecentBills();
        mRecentCost = mProjectRepo.getRecentCosts();
    }
}
