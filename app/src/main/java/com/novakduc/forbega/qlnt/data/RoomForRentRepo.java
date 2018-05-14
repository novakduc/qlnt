package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;

/**
 * Created by Novak on 1/14/2018.
 */

public class RoomForRentRepo {
    private static final String LOG_TAG = RoomForRentRepo.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static RoomForRentRepo sInstance;
    private AppDao mAppDao;
    private AppExecutors mExecutors;
    private long mRoomId;

    private RoomForRentRepo(AppDao appDao, AppExecutors executors, long roomId) {
        this.mAppDao = appDao;
        this.mExecutors = executors;
        this.mRoomId = roomId;
    }

    public static synchronized RoomForRentRepo getInstance(
            AppDao appDao, AppExecutors executors, long roomId) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new RoomForRentRepo(appDao, executors, roomId);
                    Log.d(LOG_TAG, "Create new product repo " + roomId);
                } else {
                    sInstance.setRoomId(roomId);
                    Log.d(LOG_TAG, "Lock repo to project id" + sInstance.getRoomId());
                }
            }
        } else {
            sInstance.setRoomId(roomId);
            Log.d(LOG_TAG, "Lock repo to project id" + sInstance.getRoomId());
        }
        return sInstance;
    }

    public long getRoomId() {
        return mRoomId;
    }

    public void setRoomId(long roomId) {
        mRoomId = roomId;
    }

    public LiveData<RoomForRent> getRoomForRent() {
        return mAppDao.getLiveDataRoomById(mRoomId);
    }


    public void updateRoom(final RoomForRent roomForRent) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.updateRoomForRent(roomForRent);
            }
        });
    }
}
