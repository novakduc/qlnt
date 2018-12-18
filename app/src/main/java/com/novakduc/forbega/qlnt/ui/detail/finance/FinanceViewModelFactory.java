package com.novakduc.forbega.qlnt.ui.detail.finance;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import javax.annotation.Nonnull;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FinanceViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;

    public FinanceViewModelFactory(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    @Nonnull
    @Override
    public <T extends ViewModel> T create(@Nonnull Class<T> modelClass) {
        return (T) new FinanceFragmentViewModel(mProjectRepo);
    }
}
