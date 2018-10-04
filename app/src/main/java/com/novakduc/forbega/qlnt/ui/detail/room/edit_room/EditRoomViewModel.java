package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModel;

public class EditRoomViewModel extends CheckInViewModel {

    public EditRoomViewModel(RoomForRentRepo roomForRentRepo) {
        super(roomForRentRepo);
    }

    @Override
    public void updateElectricalService(long initIndex) {

    }

    @Override
    public void updateInternetService() {

    }

    @Override
    public void updateTvService() {

    }

    @Override
    public void updateWaterService(long initIndex) {

    }
}
