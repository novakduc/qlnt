package com.novakduc.forbega.qlnt.ui.config.base;

import com.novakduc.forbega.qlnt.data.QlntRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
