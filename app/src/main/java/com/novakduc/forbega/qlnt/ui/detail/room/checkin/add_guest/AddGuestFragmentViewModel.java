package com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.Guest;

/**
 * Created by Novak on 1/28/2018.
 */

public class AddGuestFragmentViewModel extends ViewModel {
    private RoomForRentRepo mRoomRepo;
    private LiveData<Guest> mGuestLiveData;
    private boolean isNew;
    private static final String TAG = AddGuestFragment.class.getSimpleName();

    public AddGuestFragmentViewModel(RoomForRentRepo pRoomRepo, boolean isNew) {
        mRoomRepo = pRoomRepo;
        this.isNew = isNew;
    }

    public LiveData<Guest> getGuestLiveData() {
        mGuestLiveData = mRoomRepo.createTempGuest();
        return mGuestLiveData;
    }

    public LiveData<Guest> getGuestLiveData(long guestId) {
        mGuestLiveData = mRoomRepo.getGuest(guestId);
        return mGuestLiveData;
    }

    public void updateGuest(Guest guest) {
        mRoomRepo.updateGuest(guest);
        isNew = false;
    }

    @Override
    protected void onCleared() {
            mRoomRepo.cleanGuestData();
        Log.d(TAG, "Cleared invalid guest");
        super.onCleared();
    }
}
