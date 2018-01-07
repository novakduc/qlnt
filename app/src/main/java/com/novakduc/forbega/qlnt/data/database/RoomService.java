package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

/**
 * Created by n.thanh on 12/2/2016.
 */
@Entity(tableName = "room_service")
public class RoomService implements RoomServiceInterface {
    @PrimaryKey
    private long id;
    private CostType type;
    private long mOldIndex;
    private long mNewIndex;
    @Ignore
    protected UnitPrice unitPrice;

    //For Room only
    public RoomService(long id, CostType type, boolean withIndex, long oldIndex, long newIndex) {
        this.id = id;
        this.type = type;
        mOldIndex = oldIndex;
        mNewIndex = newIndex;
    }

    @Ignore
    protected RoomService(CostType type, UnitPrice unitPrice) {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.type = type;

        this.unitPrice = unitPrice;
    }

    @Ignore
    protected RoomService(CostType type) {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.type = type;
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
            factor = this.mNewIndex - this.mOldIndex > 0 ?
                        this.mNewIndex - this.mOldIndex : 1;
        }
        return unitPrice.get(type) * factor;
    }

    @Override
    public long getOldIndex() {
        return mOldIndex;
    }

    @Override
    public long getNewIndex() {
        return mNewIndex;
    }

    @Override
    public void setNewIndex(long newIndex) {
        mOldIndex = mNewIndex;  //update old index
        mNewIndex = newIndex;
    }

    @Override
    public boolean isWithIndex() {
        switch (this.type) {
            case WATER:
                return true;
            case INTERNET:
                return true;
            case TV_CABLE:
                return true;
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
    public long getAmount() {
        return charge();
    }
}
