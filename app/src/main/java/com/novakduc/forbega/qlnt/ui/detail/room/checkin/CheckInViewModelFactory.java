package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;

public class CheckInViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private RoomForRentRepo mRoomForRentRepo;

    public CheckInViewModelFactory(RoomForRentRepo roomForRentRepo) {
        mRoomForRentRepo = roomForRentRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CheckInViewModel(mRoomForRentRepo);
    }
}
