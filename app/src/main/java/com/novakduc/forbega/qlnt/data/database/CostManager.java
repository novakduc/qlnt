package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by n.thanh on 11/28/2016.
 */
@Entity(tableName = "cost_manager", indices = @Index(value = {"projectId"}, unique = true))
public class CostManager<E> extends MyArrayList<E> {
    @PrimaryKey
    private long projectId;
    private String idListGsonStringValue;
    @Ignore
    private ArrayList idList;

    //For Room only
    public CostManager(long projectId, String idListGsonStringValue) {
        super(3);
        this.projectId = projectId;
        this.idListGsonStringValue = idListGsonStringValue;
        this.idList = getIdListFromGson();
    }

    @Ignore
    public CostManager() {
        super(3);
        this.idList = new ArrayList(3);
    }

    @Ignore
    public CostManager(int i) {
        super(i);
        this.idList = new ArrayList(i);
    }

    @Override
    public String getIdListGsonStringValue() {
        return idListGsonStringValue;
    }

    @Override
    public ArrayList getIdList() {
        return idList;
    }

    public double getTotalCost(CurrencyUnit pUnit) {
        double total = 0;
        for (E i :
                this) {
            total += ((Cost) i).getAmount(pUnit);
        }
        return total;
    }

    public long getTotalCost() {
        long total = 0;
        Cost cost;
        for (E i :
                this) {
            total += ((Cost)i).getAmount();
        }

        return total;
    }
}
