package com.novakduc.forbega.qlnt.data.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by n.thanh on 11/25/2016.
 */

@Entity(tableName = "cost")
public class Cost implements ItemWithId {

    @PrimaryKey
    private long id;
    private long date;
    private long amount;
    private CostType type;
    private long projectId;
    private String description;

    //For room only
    public Cost(long id, long date, long amount, CostType type, long projectId, String description) {
        this.amount = amount;
        this.id = id;
        this.date = date;
        this.type = type;
        this.projectId = projectId;
        this.description = description;
    }

    @Ignore
    public Cost(long date, long amount, CostType type, long projectId) {
        this.id = System.currentTimeMillis();
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.projectId = projectId;
        this.description = "none";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }
}
