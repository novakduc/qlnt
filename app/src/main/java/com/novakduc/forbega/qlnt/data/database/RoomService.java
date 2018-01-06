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
    protected long id;
    protected CostType type;
    private boolean withIndex;
    private long mOldIndex;
    private long mNewIndex;
    @Ignore
    protected UnitPrice unitPrice;

    //For Room only
    public RoomService(long id, CostType type, boolean withIndex, long oldIndex, long newIndex) {
        this.id = id;
        this.type = type;
        this.withIndex = withIndex;
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

    public CostType getType() {
        return type;
    }

    private void setType(CostType type) {
        this.type = type;
    }

    public long charge() {
        return unitPrice.get(type);
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
    public long getId() {
        return 0;
    }
}
