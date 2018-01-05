package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 11/25/2016.
 */

@Entity(tableName = "cost")
public class Cost {

    private long amount;
    @PrimaryKey
    private long date;
    private CostType type;
    private boolean repeatable;
    private long projectId;

    public Cost(long projectId, long amount, CostType type, long date, boolean repeatable) {
        this.projectId = projectId;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.repeatable = repeatable;
    }

    public long getProjectId() {
        return projectId;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getAmount(CurrencyUnit pUnit) {
        return Loan.round((double) amount / pUnit.getUnit(), 3);
    }

    public long getId() {
        return date;
    }
}
