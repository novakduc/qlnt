package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

/**
 * Created by n.thanh on 11/25/2016.
 */

@Entity(tableName = "cost")
public class Cost implements ItemWithId{

    @PrimaryKey
    private long id;
    private long date;
    private long amount;
    private CostType type;
    private long projectId;

    //For room only
    public Cost(long id, long date, long amount, CostType type, long projectId) {
        this.amount = amount;
        this.id = id;
        this.date = date;
        this.type = type;
        this.projectId = projectId;
    }

    @Ignore
    public Cost(long date, long amount, CostType type, long projectId) {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.projectId = projectId;
    }

    public CostType getType() {
        return type;
    }

    public void setType(CostType type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }
}
