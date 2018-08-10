package com.novakduc.forbega.qlnt.ui.detail.report;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

public class ReportViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;

    public ReportViewModelFactory(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ReportFragmentViewModel(mProjectRepo);
    }
}
