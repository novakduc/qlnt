package com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
