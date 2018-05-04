package com.novakduc.forbega.qlnt.ui.detail.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import java.util.List;

public class RoomListFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<List<ListViewRoomItem>> mRoomListLiveData;
    private LiveData<GuestNameOnly> mContactPoint;

    public RoomListFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
    }

    public void deleteRoom(long tempRoomId) {

    }

    public void copyRoom(long roomId) {

    }
}
