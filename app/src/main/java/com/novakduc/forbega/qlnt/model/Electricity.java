package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class Electricity extends RoomService {
    private volatile static Electricity mInstance;
    private long mOldIndex, mNewIndex;

    private Electricity() {
    }

    private Electricity(Room room) {
        super();
        this.mRoom = room;
        this.mUse = true;
    }

    public static Electricity getInstance(Room room) {
        if (mInstance == null) {
            synchronized (Electricity.class) {
                if (mInstance == null) {
                    mInstance = new Electricity(room);
                }
            }
        }
        return mInstance;
    }

    public long getOldIndex() {
        return mOldIndex;
    }

    public long getNewIndex() {
        return mNewIndex;
    }

    public void setNewIndex(long newIndex) {
        mNewIndex = newIndex;
    }

    @Override
    public long charge() {
        return 0;
    }
}
