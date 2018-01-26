package com.novakduc.forbega.qlnt.ui.config.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.QlntRepository;

/**
 * Created by Nguyen Quoc Thanh on 1/26/2018.
 */

public class ProjectBaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private QlntRepository mRepository;

    public ProjectBaseViewModelFactory(QlntRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectBaseFragmentViewModel(mRepository);
    }
}
