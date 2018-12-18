package com.novakduc.forbega.qlnt.ui.detail.finance;

import androidx.room.ColumnInfo;

public class BillRecentItem {
    @ColumnInfo(name = "amount")
    private long amount;
    @ColumnInfo(name = "paymentDate")
    private long paymentDate;
    @ColumnInfo(name = "description")
    private String description;

    //For room only
    public BillRecentItem(long amount, long paymentDate, String description) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.description = description;
    }

    public static BillRecentItem getInstance() {
        return new BillRecentItem(0, 0, "temp");
    }

    public long getAmount() {
        return amount;
    }

    public long getPaymentDate() {
        return paymentDate;
    }

    public String getDescription() {
        return description;
    }
}
