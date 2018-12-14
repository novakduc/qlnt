package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by n.thanh on 11/28/2016.
 */
@Entity(tableName = "cost_manager", indices = @Index(value = {"projectId"}, unique = true))
public class CostManager extends MyArrayList<Cost> {

    @PrimaryKey
    private long projectId;
    private String idListGSonString;
    private long totalAmount;
    @Ignore
    private ArrayList idList;

    //For Room only
    public CostManager(long projectId, String idListGSonString, long totalAmount) {
        this.projectId = projectId;
        this.idListGSonString = idListGSonString;
        this.idList = gSonStringToList();
        this.totalAmount = totalAmount;
    }

    @Ignore
    public CostManager(long projectId) {
        super(3);
        this.projectId = projectId;
        this.idList = new ArrayList(3);
    }

    public long getProjectId() {
        return projectId;
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
    public boolean add(Cost e) {
        boolean b = super.add(e);
        totalAmount = super.getTotalAmount();
        return b;
    }


}
