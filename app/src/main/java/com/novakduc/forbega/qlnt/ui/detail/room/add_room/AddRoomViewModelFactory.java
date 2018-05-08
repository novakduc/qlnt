package com.novakduc.forbega.qlnt.ui.detail.room.add_room;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

public class AddRoomViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepo mProjectRepo;

    public AddRoomViewModelFactory(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddRoomViewModel(mProjectRepo);
    }
}
