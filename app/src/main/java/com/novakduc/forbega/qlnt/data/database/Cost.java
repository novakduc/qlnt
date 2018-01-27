package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 11/25/2016.
 */

@Entity(tableName = "cost")
public class Cost implements ItemWithId{

    private long amount;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long date;
    private CostType type;
    private boolean repeatable;

    //For room only
    public Cost(long id, long amount, CostType type, long date, boolean repeatable) {
        this.amount = amount;
        this.id = id;
        this.type = type;
        this.date = date;
        this.repeatable = repeatable;
    }

    @Ignore
    public Cost(long amount, long date, CostType type, boolean repeatable) {
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.repeatable = repeatable;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public CostType getType() {
        return type;
    }

    public void setType(CostType type) {
        this.type = type;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public long getId() {
        return id;
    }

}
