package com.novakduc.forbega.qlnt.ui.config;

import com.novakduc.forbega.qlnt.data.QlntRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Nguyen Quoc Thanh on 1/30/2018.
 */

public class ProjectConfigViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private QlntRepository mRepository;

    public ProjectConfigViewModelFactory(QlntRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectConfigActivityViewModel(mRepository);
    }
}
