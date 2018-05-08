package com.novakduc.forbega.qlnt.ui.detail.room.add_room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;

public class AddRoomViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<RoomForRent> mRoomForRentLiveData;

    public AddRoomViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mRoomForRentLiveData = mProjectRepo.createTempRoom();
    }

    public LiveData<RoomForRent> getRoomForRentLiveData() {
        return mRoomForRentLiveData;
    }
}
