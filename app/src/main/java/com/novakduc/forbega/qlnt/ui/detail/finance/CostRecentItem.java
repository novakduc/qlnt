package com.novakduc.forbega.qlnt.ui.detail.finance;

import com.novakduc.forbega.qlnt.data.database.CostType;

import androidx.room.ColumnInfo;

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
    @ColumnInfo(name = "description")
    private String description;

    //For room only
    public CostRecentItem(long date, long amount, CostType type, String description) {
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.description = description;
    }

    public static CostRecentItem getInstance() {
        return new CostRecentItem(0, 0, CostType.OTHERS, "none");
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

    public String getDescription() {
        return description;
    }
}
