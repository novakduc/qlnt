package com.novakduc.forbega.qlnt.data.query.finance_tab;

import android.arch.persistence.room.ColumnInfo;

import com.novakduc.forbega.qlnt.data.database.CostType;

/**
 * Created by Nguyen Quoc Thanh on 2/6/2018.
 */
//This is brief version of loan for list view
public class CostRecentItem {
    @ColumnInfo(name = "amount")
    private long amount;
    @ColumnInfo(name = "date")
    private long date;
    @ColumnInfo(name = "type")
    private CostType type;

    //For room only
    public CostRecentItem(long date, long amount, CostType type) {
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public CostType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
