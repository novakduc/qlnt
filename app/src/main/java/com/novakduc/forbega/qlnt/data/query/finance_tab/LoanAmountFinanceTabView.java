package com.novakduc.forbega.qlnt.data.query.finance_tab;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by Nguyen Quoc Thanh on 2/6/2018.
 */
//This is brief version of loan for list view
public class LoanAmountFinanceTabView {
    @ColumnInfo(name = "amount")
    private long amount;

    //For room only
    public LoanAmountFinanceTabView(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }
}
