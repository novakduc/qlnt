package com.novakduc.forbega.qlnt.ui.config.finance.loan;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

/**
 * Created by Novak on 1/28/2018.
 */

public class LoanDeclareViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;
    private boolean isNew;

    public LoanDeclareViewModelFactory(ProjectRepo projectRepo, boolean isNew) {
        mProjectRepo = projectRepo;
        this.isNew = isNew;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoanDeclareFragmentViewModel(mProjectRepo, isNew);
    }
}
