package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomService extends DBObject {
    protected Room mRoom;
    protected CostType mType;

    protected RoomService(Room room, CostType type) {
        this.mRoom = room;
        this.mType = type;
    }

    public CostType getType() {
        return mType;
    }

    public void setType(CostType type) {
        mType = type;
    }

    public Room getRoom() {
        return mRoom;
    }

    public long charge() {
        return mRoom.getProject().getUnitPrice().get(mType);
    }
}
