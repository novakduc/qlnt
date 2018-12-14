package com.novakduc.forbega.qlnt.data.database;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by n.thanh on 12/2/2016.
 */
@Entity(tableName = "room_service")
public class RoomService implements RoomServiceInterface {
    @Ignore
    private static final String LOG_TAG = RoomService.class.getSimpleName().toString();
    @Ignore
    protected UnitPrice unitPrice;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private CostType type;
    private long oldIndex;
    private long newIndex;
    private long roomId;
    private boolean isApplied;

    //For Room only
    public RoomService(long id, CostType type, long oldIndex, long newIndex, long roomId, boolean isApplied) {
        this.id = id;
        this.type = type;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.roomId = roomId;
        this.isApplied = isApplied;
    }

    @Ignore
    private RoomService(CostType type, long roomId) {
        this.type = type;
        this.roomId = roomId;
        this.isApplied = true;
    }

    public static RoomService getInstance(CostType pCostType, long pRoomId) {
        return new RoomService(pCostType, pRoomId);
    }

    public long getRoomId() {
        return roomId;
    }

    @Override
    public CostType getType() {
        return type;
    }

    private void setType(CostType type) {
        this.type = type;
    }

    @Override
    public long charge() {
        long factor = 1;
        if (unitPrice != null) {
            if (isWithIndex()) {
                factor = this.newIndex - this.oldIndex > 0 ?
                        this.newIndex - this.oldIndex : 1;
            }
            return unitPrice.get(type) * factor;
        } else {
            Log.d(LOG_TAG, "Unexpected result. Unitprice is null");
            return -1;
        }
    }

    @Override
    public long getOldIndex() {
        return oldIndex;
    }

    @Override
    public long getNewIndex() {
        return newIndex;
    }

    @Override
    public void setNewIndex(long newIndex) {
        oldIndex = this.newIndex;  //update old index
        this.newIndex = newIndex;
    }

    @Override
    public boolean isWithIndex() {
        switch (this.type) {
            case WATER:
                return true;
            case INTERNET:
                return false;
            case TV_CABLE:
                return false;
            case ELECTRICITY:
                return true;
            case SECURITY:
                return false;
            case MAINTENANCE:
                return false;
            case TRASH_COLLECTION:
                return false;
            case OTHERS:
                return false;
        }
        return false;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean pApplied) {
        isApplied = pApplied;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getAmount() {
        return charge();
    }

    @Override
    public String toString() {
        return "\nId:" + id + "; type:" + type.toString() + "; room Id:" + roomId + "; index:" + newIndex + "; applied:" + isApplied;
    }
}
