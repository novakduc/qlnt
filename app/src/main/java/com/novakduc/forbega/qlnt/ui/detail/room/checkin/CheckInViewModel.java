package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.ui.detail.room.GuestForRoomItemView;

import java.util.List;

public class CheckInViewModel extends ViewModel {
    private RoomForRentRepo mRoomForRentRepo;
    private LiveData<RoomForRent> mRoomForRentLiveData;
    private LiveData<List<Guest>> mGuestListLiveData;

    public CheckInViewModel(RoomForRentRepo roomForRentRepo) {
        mRoomForRentRepo = roomForRentRepo;
        mRoomForRentLiveData = mRoomForRentRepo.getRoomForRent();
        mGuestListLiveData = mRoomForRentRepo.getGuestList();
    }

    public LiveData<RoomForRent> getRoomForRentLiveData() {
        return mRoomForRentLiveData;
    }

    public LiveData<List<Guest>> getGuestListLiveData() {
        return mGuestListLiveData;
    }

    public void updateRoom(RoomForRent roomForRent) {
        mRoomForRentRepo.updateRoom(roomForRent);
    }

    public void deleteGuest(long pTempGuestId) {
        mRoomForRentRepo.deleteGuest(pTempGuestId);
    }

    public void addTvService() {
        mRoomForRentRepo.addTvService();
    }
}
