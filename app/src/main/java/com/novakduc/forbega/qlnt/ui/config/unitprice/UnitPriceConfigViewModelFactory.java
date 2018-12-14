package com.novakduc.forbega.qlnt.ui.config.unitprice;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Nguyen Quoc Thanh on 1/30/2018.
 */

public class UnitPriceConfigViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;

    public UnitPriceConfigViewModelFactory(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UnitPriceConfigFragmentViewModel(mProjectRepo);
    }
}
