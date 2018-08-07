package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.query.finance_tab.ProjectFinanceTab;

import java.util.List;

public class FinanceFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<ProjectFinanceTab> mProjectFinanceInfo;
    private LiveData<List> mRecentItem;

    public FinanceFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mProjectFinanceInfo = mProjectRepo.getProjectFinanceInfo();
        mRecentItem = mProjectRepo.getRecentFinanceItem(3);
    }
}
