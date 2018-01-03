package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 11/25/2016.
 */

@Entity(tableName = "cost")
public class Cost {

    private long mAmount;
    @PrimaryKey
    private long mDate;
    private CostType mType;
    private boolean mRepeatable;

    public Cost(long amount, CostType type, long date, boolean repeatable) {
        this.mAmount = amount;
        this.mType = type;
        this.mDate = date;
        this.mRepeatable = repeatable;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public CostType getType() {
        return mType;
    }

    public void setType(CostType type) {
        mType = type;
    }

    public boolean isRepeatable() {
        return mRepeatable;
    }

    public void setRepeatable(boolean repeatable) {
        mRepeatable = repeatable;
    }

    public long getAmount() {
        return mAmount;
    }

    public void setAmount(long amount) {
        mAmount = amount;
    }

    public double getAmount(CurrencyUnit pUnit) {
        return Loan.round((double) mAmount / pUnit.getUnit(), 3);
    }
}
