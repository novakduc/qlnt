package com.novakduc.forbega.qlnt.ui.detail.finance;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class FinanceFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<ProjectFinanceTab> mProjectFinanceInfo;
    private LiveData<List> mRecentItems;

    public FinanceFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mProjectFinanceInfo = mProjectRepo.getProjectFinanceInfo();
        mRecentItems = mProjectRepo.getRecentFinanceItem(3);
    }

    public LiveData<ProjectFinanceTab> getProjectFinanceInfo() {
        return mProjectFinanceInfo;
    }

    public LiveData<List> getRecentItems() {
        return mRecentItems;
    }
}
