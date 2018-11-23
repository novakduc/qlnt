package com.novakduc.forbega.qlnt.ui.detail.room.edit_room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomService;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModel;

import java.util.List;

public class EditRoomViewModel extends ViewModel {
    private RoomForRentRepo mRoomForRentRepo;
    private LiveData<RoomForRent> mRoomForRentLiveData;
    private LiveData<List<Guest>> mGuestListLiveData;
    private LiveData<List<RoomService>> mServicesLiveData;

    public EditRoomViewModel(RoomForRentRepo pRoomForRentRepo) {
        mRoomForRentRepo = pRoomForRentRepo;
        mRoomForRentLiveData = mRoomForRentRepo.getRoomForRent();
        mGuestListLiveData = mRoomForRentRepo.getGuestList();
        mServicesLiveData = mRoomForRentRepo.getServices();
    }

    public LiveData<RoomForRent> getRoomForRentLiveData() {
        return mRoomForRentLiveData;
    }

    public LiveData<List<Guest>> getGuestListLiveData() {
        return mGuestListLiveData;
    }

    public void deleteGuest(long pTempGuestId) {
        mRoomForRentRepo.deleteGuest(pTempGuestId);
    }

    public LiveData<List<RoomService>> getServicesLiveData() {
        return mServicesLiveData;
    }

    public void updateService(RoomService pService) {
        mRoomForRentRepo.updateService(pService);
    }

    public void updateRoom(RoomForRent pRoomForRent) {
        mRoomForRentRepo.updateRoom(pRoomForRent);
    }

    public void confirmKeyContact() {
        mRoomForRentRepo.confirmAssignKeyContact();
    }
}
