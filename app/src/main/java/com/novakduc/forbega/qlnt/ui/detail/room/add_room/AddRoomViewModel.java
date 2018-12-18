package com.novakduc.forbega.qlnt.ui.detail.room.add_room;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddRoomViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<RoomForRent> mRoomForRentLiveData;
    private boolean isConfirmed;
    private long mRoomId;

    public AddRoomViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mRoomForRentLiveData = mProjectRepo.createTempRoom();
        isConfirmed = false;
        mRoomId = -1;
    }

    public LiveData<RoomForRent> getRoomForRentLiveData() {
        return mRoomForRentLiveData;
    }

    public void addRoom(RoomForRent roomForRent) {
        mProjectRepo.updateRoomForRent(roomForRent);
        isConfirmed = true;
    }

    @Override
    protected void onCleared() {
        if (!isConfirmed) {
            if (mRoomId != -1)
                mProjectRepo.deleteRoom(mRoomId);
        }
        super.onCleared();
    }

    public void setRoomId(long roomId) {
        mRoomId = roomId;
    }
}
