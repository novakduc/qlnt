package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomServiceWithIndex extends RoomService {
    private long mOldIndex, mNewIndex;

    private RoomServiceWithIndex(Room room, CostType type) {
        super(room, true, type);
    }

    public static RoomServiceWithIndex getInstance(Room room, CostType type) {
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

    @Override
    public long charge() {
        if (mUse) {
            long unitPrice = mRoom.getProject().getUnitPrice().get(mType);
            return (mNewIndex - mOldIndex) * unitPrice;
        }
        return 0;
    }
}
