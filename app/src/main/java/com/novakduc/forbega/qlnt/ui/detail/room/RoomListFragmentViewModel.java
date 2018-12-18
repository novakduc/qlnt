package com.novakduc.forbega.qlnt.ui.detail.room;

import com.novakduc.forbega.qlnt.data.ProjectRepo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RoomListFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<List<ListViewRoomItem>> mRoomListLiveData;
    private LiveData<List<GuestForRoomItemView>> mKeyContacts;

    public RoomListFragmentViewModel(ProjectRepo projectRepo) {
        mProjectRepo = projectRepo;
        mRoomListLiveData = mProjectRepo.getListViewRoomItems();
        mKeyContacts = mProjectRepo.getAllKeyContact();
    }

    public LiveData<List<ListViewRoomItem>> getRoomListLiveData() {
        return mRoomListLiveData;
    }

    public LiveData<List<GuestForRoomItemView>> getKeyContacts() {
        return mKeyContacts;
    }

    public void deleteRoom(long roomId) {
        mProjectRepo.deleteRoom(roomId);
    }

    public void copyRoom(long roomId) {

    }
}
