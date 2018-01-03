package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomServiceWithIndex extends RoomService {
    private long mOldIndex, mNewIndex;

    private RoomServiceWithIndex(UnitPrice unitPrice, CostType type) {
        super(unitPrice, type);
    }

    public static RoomServiceWithIndex getInstance(UnitPrice room, CostType type) {
        return new RoomServiceWithIndex(room, type);
    }

    public long getOldIndex() {
        return mOldIndex;
    }

    public long getNewIndex() {
        return mNewIndex;
    }

    public void setNewIndex(long newIndex) {
        mOldIndex = mNewIndex;  //update old index
        mNewIndex = newIndex;
    }
}
