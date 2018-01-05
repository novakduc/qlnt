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
    private String gSonIdString;
    @Ignore
    private ArrayList idListLong;

    //For Room only
    public CostManager(long projectId, String gSonIdString) {
        super(3);
        this.projectId = projectId;
        this.gSonIdString = gSonIdString;
        this.idListLong = getIdListFromGson();
    }

    @Ignore
    public CostManager() {
        super(3);
        this.idListLong = new ArrayList(3);
    }

    @Ignore
    public CostManager(int i) {
        super(i);
        this.idListLong = new ArrayList(i);
    }

    @Override
    public String getGsonIdList() {
        return gSonIdString;
    }

    @Override
    public ArrayList getIdListLong() {
        return idListLong;
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
