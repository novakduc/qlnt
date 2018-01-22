package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "unit_price")
public class UnitPrice implements Cloneable, Parcelable {

    public static final Parcelable.Creator<UnitPrice> CREATOR = new Parcelable.Creator<UnitPrice>() {
        @Override
        public UnitPrice createFromParcel(Parcel source) {
            return new UnitPrice(source);
        }

        @Override
        public UnitPrice[] newArray(int size) {
            return new UnitPrice[size];
        }
    };
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
    public UnitPrice(long projectId) {
        this.id = projectId;
    }

    @Ignore
    private UnitPrice() {
        //Prevent init empty object
    }

    @Ignore
    protected UnitPrice(Parcel in) {
        this.id = in.readLong();
        this.electricity = in.readLong();
        this.water = in.readLong();
        this.tv = in.readLong();
        this.trashCollection = in.readLong();
        this.internet = in.readLong();
        this.security = in.readLong();
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
    protected Object clone() throws CloneNotSupportedException {
        UnitPrice unitPrice = (UnitPrice) super.clone();
        unitPrice.id = Calendar.getInstance().getTimeInMillis();
        return unitPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.electricity);
        dest.writeLong(this.water);
        dest.writeLong(this.tv);
        dest.writeLong(this.trashCollection);
        dest.writeLong(this.internet);
        dest.writeLong(this.security);
    }
}
