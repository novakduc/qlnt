package com.novakduc.forbega.qlnt.ui.detail.room;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

public class RoomListViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;

    public RoomListViewModelFactory(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RoomListFragmentViewModel(mProjectRepo);
    }
}
