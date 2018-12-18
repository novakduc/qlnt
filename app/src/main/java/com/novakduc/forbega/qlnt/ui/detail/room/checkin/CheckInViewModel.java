package com.novakduc.forbega.qlnt.ui.detail.room.checkin;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomService;
import com.novakduc.forbega.qlnt.utilities.Constants;
import com.novakduc.forbega.qlnt.workers.BillRemindWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class CheckInViewModel extends ViewModel {
    private RoomForRentRepo mRoomForRentRepo;
    private LiveData<RoomForRent> mRoomForRentLiveData;
    private LiveData<List<Guest>> mGuestListLiveData;
    private LiveData<List<RoomService>> mServicesLiveData;
    private String mRoomName;

    public CheckInViewModel(RoomForRentRepo roomForRentRepo) {
        mRoomForRentRepo = roomForRentRepo;
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

    public LiveData<List<RoomService>> getServicesLiveData() {
        return mServicesLiveData;
    }

    public void updateRoom(RoomForRent roomForRent) {
        mRoomForRentRepo.updateRoom(roomForRent);
    }

    public void deleteGuest(long pTempGuestId) {
        mRoomForRentRepo.deleteGuest(pTempGuestId);
    }

    public void updateTvService(RoomService tvService) {
        mRoomForRentRepo.updateService(tvService);
    }

    public void updateInternetService(RoomService internetService) {
        mRoomForRentRepo.updateService(internetService);
    }

    public void updateWaterService(RoomService waterService) {
        mRoomForRentRepo.updateService(waterService);
    }

    public void updateElectricalService(RoomService electricalService) {
        mRoomForRentRepo.updateService(electricalService);
    }

    public void turnOnBillReminder() {
        PeriodicWorkRequest.Builder builder =
                new PeriodicWorkRequest.Builder(BillRemindWorker.class, 24, TimeUnit.HOURS);
        builder.setInputData(createInputRoomData());
        PeriodicWorkRequest workRequest = builder.build();

        WorkManager.getInstance().enqueue(workRequest);
    }

    private Data createInputRoomData() {
        Data.Builder builder = new Data.Builder();
        if (mRoomName != null) {
            builder.putString(Constants.ROOM_NAME_KEY, mRoomName);
        }

        return builder.build();
    }

    public void confirmKeyContact() {
        mRoomForRentRepo.confirmAssignKeyContact();
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String pRoomName) {
        mRoomName = pRoomName;
    }
}
