package com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;

/**
 * Created by Novak on 1/28/2018.
 */

public class AddGuestViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private RoomForRentRepo mRoomRepo;
    private boolean isNew;

    public AddGuestViewModelFactory(RoomForRentRepo pRoomRepo, boolean isNew) {
        mRoomRepo = pRoomRepo;
        this.isNew = isNew;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddGuestFragmentViewModel(mRoomRepo, isNew);
    }
}
