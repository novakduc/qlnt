package com.novakduc.forbega.qlnt.ui.config.finance;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

/**
 * Created by Novak on 1/27/2018.
 */

public class ProjectFinanceConfigViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;

    public ProjectFinanceConfigViewModelFactory(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectFinanceConfigViewModel(mProjectRepo);
    }
}
