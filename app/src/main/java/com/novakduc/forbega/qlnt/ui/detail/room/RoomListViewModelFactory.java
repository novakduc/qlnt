package com.novakduc.forbega.qlnt.ui.detail.room;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class RoomListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public RoomListViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RoomListFragmentViewModel();
    }
}
