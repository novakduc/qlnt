package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomServiceWithIndex extends RoomService {
    private long mOldIndex;
    private long mNewIndex;

    private RoomServiceWithIndex(CostType type, UnitPrice unitPrice) {
        super(type, unitPrice);
    }

    public static RoomServiceWithIndex getInstance(CostType costType, UnitPrice unitPrice) {
        return new RoomServiceWithIndex(costType, unitPrice);
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
