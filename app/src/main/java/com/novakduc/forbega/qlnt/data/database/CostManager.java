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
    private String idListGSonString;
    private long totalAmount;
    @Ignore
    private ArrayList idList;

    //For Room only
    public CostManager(long projectId, String idListGSonString, long totalAmount) {
        super(3);
        this.projectId = projectId;
        this.idListGSonString = idListGSonString;
        this.idList = gSonStringToList();
        this.totalAmount = totalAmount;
    }

    @Ignore
    public CostManager() {
        super(3);
        this.idList = new ArrayList(3);
    }

    @Override
    public String getIdListGSonString() {
        return idListGSonString;
    }

    @Override
    public ArrayList getIdList() {
        return idList;
    }

    @Override
    public long getTotalAmount() {
        totalAmount = super.getTotalAmount();
        return totalAmount;
    }

    @Override
    public boolean add(E e) {
        boolean b = super.add(e);
        totalAmount = super.getTotalAmount();
        return b;
    }
}
