package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;

public class CheckInViewModel extends ViewModel {
    private RoomForRentRepo mRoomForRentRepo;
    private LiveData<RoomForRent> mRoomForRentLiveData;


    public CheckInViewModel(RoomForRentRepo roomForRentRepo) {
        mRoomForRentRepo = roomForRentRepo;
        mRoomForRentLiveData = mRoomForRentRepo.getRoomForRent();
    }

    public LiveData<RoomForRent> getRoomForRentLiveData() {
        return mRoomForRentLiveData;
    }

    public void updateRoom(RoomForRent roomForRent) {
        mRoomForRentRepo.updateRoom(roomForRent);
    }
}
