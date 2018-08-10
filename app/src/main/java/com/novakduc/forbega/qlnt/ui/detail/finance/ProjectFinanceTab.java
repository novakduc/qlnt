package com.novakduc.forbega.qlnt.ui.detail.finance;

import android.arch.persistence.room.ColumnInfo;

public class ProjectFinanceTab {
    @ColumnInfo(name = "revenue")
    private long revenue;
    @ColumnInfo(name = "dept")
    private long dept;
    @ColumnInfo(name = "cost")
    private long cost;

    //For room only
    public ProjectFinanceTab(long revenue, long dept, long cost) {
        this.revenue = revenue;
        this.dept = dept;
        this.cost = cost;
    }

    public long getRevenue() {
        return revenue;
    }

    public long getDept() {
        return dept;
    }

    public long getCost() {
        return cost;
    }

    public long getIncome() {
        return getRevenue() - getCost();
    }
}
