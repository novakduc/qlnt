package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 12/2/2016.
 */
@Entity(tableName = "room_service")
public class RoomService implements RoomServiceInterface {
    @Ignore
    protected UnitPrice unitPrice;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private CostType type;
    private long oldIndex;
    private long newIndex;
    private long roomId;

    //For Room only
    public RoomService(long id, CostType type, long oldIndex, long newIndex, long roomId) {
        this.id = id;
        this.type = type;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.roomId = roomId;
    }

    @Ignore
    public RoomService(CostType type, long roomId) {
        this.type = type;
        this.roomId = roomId;
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
        if (isWithIndex()) {
            factor = this.newIndex - this.oldIndex > 0 ?
                        this.newIndex - this.oldIndex : 1;
        }
        return unitPrice.get(type) * factor;
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
}
