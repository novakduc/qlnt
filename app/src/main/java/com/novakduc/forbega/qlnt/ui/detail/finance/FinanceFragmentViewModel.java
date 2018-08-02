package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.database.GuestForRoomItemView;
import com.novakduc.forbega.qlnt.data.database.ListViewRoomItem;

import java.util.List;

public class FinanceFragmentViewModel extends ViewModel {
    private ProjectRepo mProjectRepo;
    private LiveData<List<ListViewRoomItem>> mRoomListLiveData;
    private LiveData<List<GuestForRoomItemView>> mKeyContacts;

    public FinanceFragmentViewModel(ProjectRepo projectRepo) {
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
