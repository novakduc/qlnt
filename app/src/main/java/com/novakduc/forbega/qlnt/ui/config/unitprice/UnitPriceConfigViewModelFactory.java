package com.novakduc.forbega.qlnt.ui.config.unitprice;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

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
