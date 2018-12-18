package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
