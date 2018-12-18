package com.novakduc.forbega.qlnt.ui.list;

import com.novakduc.forbega.qlnt.data.QlntRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Nguyen Quoc Thanh on 1/11/2018.
 */

public class ProjectListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private QlntRepository mRepository;

    public ProjectListViewModelFactory(final QlntRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectListFragmentViewModel(mRepository);
    }
}
