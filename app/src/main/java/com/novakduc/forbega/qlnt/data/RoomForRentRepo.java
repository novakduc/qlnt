package com.novakduc.forbega.qlnt.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.database.AppDao;
import com.novakduc.forbega.qlnt.data.database.CostType;
import com.novakduc.forbega.qlnt.data.database.Guest;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.RoomForRent;
import com.novakduc.forbega.qlnt.data.database.RoomService;

import java.util.List;

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
                    Log.d(LOG_TAG, "Create new room repo " + roomId);
                } else {
                    sInstance.setRoomId(roomId);
                    Log.d(LOG_TAG, "Lock repo to room id" + sInstance.getRoomId());
                }
            }
        } else {
            sInstance.setRoomId(roomId);
            Log.d(LOG_TAG, "Lock repo to room id" + sInstance.getRoomId());
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

    public LiveData<Guest> createTempGuest() {
        final MutableLiveData<Guest> guestMutableLiveData = new MutableLiveData<Guest>();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Guest guest = Guest.getInstance(null, mRoomId);
                guest.setId(mAppDao.insert(guest));
                guestMutableLiveData.postValue(guest);
            }
        });
        return guestMutableLiveData;
    }

    public LiveData<Guest> getGuest(long pGuestId) {
        return mAppDao.getLiveDataGuestById(pGuestId);
    }

    public void updateGuest(final Guest pGuest) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.updateGuest(pGuest);
            }
        });
    }

    public void cleanGuestData() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Guest> guests = mAppDao.getGuestsByRoomId(mRoomId);
                if (guests != null) {
                    for (Guest g :
                            guests) {
                        if (g.getName() == null) {
                            mAppDao.removeGuest(g);
                        }
                    }
                }
            }
        });
    }

    public LiveData<List<Guest>> getGuestList() {
        return mAppDao.getLiveDataGuestListByRoomId(mRoomId);
    }

    public void deleteGuest(final long pGuestId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Guest guest = mAppDao.getGuestById(pGuestId);
                if (guest != null) {
                    mAppDao.removeGuest(guest);
                }
            }
        });
    }

    public void addTvService() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.insert(new RoomService(CostType.TV_CABLE, mRoomId));
            }
        });
    }

    public void addInternetService() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDao.insert(new RoomService(CostType.INTERNET, mRoomId));
            }
        });
    }

    public void addWaterService(final long initIndex) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                RoomService waterService = new RoomService(CostType.WATER, getRoomId());
                waterService.setNewIndex(initIndex);
                mAppDao.insert(waterService);
            }
        });
    }

    public void addElectricityService(final long pInitIndex) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                RoomService electricityService = new RoomService(CostType.ELECTRICITY, getRoomId());
                electricityService.setNewIndex(pInitIndex);
                mAppDao.insert(electricityService);
            }
        });
    }

    public void deleteServices() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<RoomService> roomServices = mAppDao.getAllServicesByRoomId(mRoomId);
                if (roomServices != null) {
                    for (RoomService roomService :
                            roomServices) {
                        mAppDao.removeRoomService(roomService);
                    }
                }
            }
        });
    }

    public void confirmAssignKeyContact() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Guest> guests = mAppDao.getGuestsByRoom(mRoomId);
                if (guests != null) {
                    boolean isKeyContact = false;
                    for (Guest guest :
                            guests) {
                        isKeyContact = guest.isKeyContact();
                        if (isKeyContact) {
                            return;
                        }
                    }

                    Guest guest = guests.get(0);
                    guest.setKeyContact(true);
                    mAppDao.updateGuest(guest);
                }
            }
        });
    }

    public LiveData<List<RoomService>> getServices() {
        return mAppDao.getLiveDataServices(mRoomId);
    }
}
