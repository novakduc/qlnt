package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;

public class EditRoomViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private RoomForRentRepo mRoomForRentRepo;

    public EditRoomViewModelFactory(RoomForRentRepo roomForRentRepo) {
        mRoomForRentRepo = roomForRentRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditRoomViewModel(mRoomForRentRepo);
    }
}
