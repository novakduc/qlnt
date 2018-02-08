package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "unit_price")
public class UnitPrice implements Cloneable {
    @PrimaryKey
    private long id;
    private long electricity;
    private long water;
    private long tv;
    private long trashCollection;
    private long internet;
    private long security;

    //For room only
    public UnitPrice(long id, long electricity, long water, long tv, long trashCollection,
                     long internet, long security) {
        this.id = id;
        this.electricity = electricity;
        this.water = water;
        this.tv = tv;
        this.trashCollection = trashCollection;
        this.internet = internet;
        this.security = security;
    }

    @Ignore
    private UnitPrice(long projectId) {
        this.id = projectId;
    }

    @Ignore
    private UnitPrice() {
        //Prevent init empty object
    }

    public static UnitPrice getInstance(long projectId) {
        return new UnitPrice(projectId);
    }

    //Getter and setter section

    public long getId() {
        return id;
    }

    public long get(CostType type) {
        switch (type) {
            case ELECTRICITY:
                return electricity;
            case WATER:
                return water;
            case INTERNET:
                return internet;
            case SECURITY:
                return security;
            case TRASH_COLLECTION:
                return trashCollection;
            case TV_CABLE:
                return tv;
            default:
                return -1;
        }
    }

    public long getElectricity() {
        return electricity;
    }

    public void setElectricity(long electricity) {
        this.electricity = electricity;
    }

    public long getWater() {
        return water;
    }

    public void setWater(long water) {
        this.water = water;
    }

    public long getTv() {
        return tv;
    }

    public void setTv(long tv) {
        this.tv = tv;
    }

    public long getTrashCollection() {
        return trashCollection;
    }

    public void setTrashCollection(long trashCollection) {
        this.trashCollection = trashCollection;
    }

    public long getInternet() {
        return internet;
    }

    public void setInternet(long internet) {
        this.internet = internet;
    }

    public long getSecurity() {
        return security;
    }

    //Below is for Parcelable
    //////////////////////////////////////

    public void setSecurity(long security) {
        this.security = security;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        UnitPrice unitPrice = (UnitPrice) super.clone();
        return unitPrice;
    }

    public void setProjectId(long newProjectId) {
        id = newProjectId;
    }
}
