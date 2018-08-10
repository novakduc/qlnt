package com.novakduc.forbega.qlnt.ui.list;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by Nguyen Quoc Thanh on 2/6/2018.
 */
//This is brief version of loan for list view
public class LoanAmount {
    @ColumnInfo(name = "amount")
    private long amount;
    @ColumnInfo(name = "projectId")
    private long projectId;

    //For room only
    public LoanAmount(long amount, long projectId) {
        this.amount = amount;
        this.projectId = projectId;
    }

    public long getAmount() {
        return amount;
    }

    public long getProjectId() {
        return projectId;
    }
}
