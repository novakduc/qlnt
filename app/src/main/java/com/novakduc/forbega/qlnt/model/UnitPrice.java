package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class UnitPrice extends DBObject implements Cloneable, Parcelable {
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
    private long mProjectId;
    private long mElectricity;
    private long mWater;
    private long mTv;
    private long mTrashCollection;
    private long mInternet;
    private long mSecurity;

    //Getter and setter section
    public UnitPrice(long projectId) {
        mProjectId = projectId;
    }

    protected UnitPrice(Parcel in) {
        this.mProjectId = in.readLong();
        this.mElectricity = in.readLong();
        this.mWater = in.readLong();
        this.mTv = in.readLong();
        this.mTrashCollection = in.readLong();
        this.mInternet = in.readLong();
        this.mSecurity = in.readLong();
        this.isChanged = in.readByte() != 0;
    }

    public long getProjectId() {
        return mProjectId;
    }

    public long getElectricity() {
        return mElectricity;
    }

    public void setElectricity(long electricity) {
        mElectricity = electricity;
    }

    public long getWater() {
        return mWater;
    }

    public void setWater(Long water) {
        mWater = water;
    }

    public long getTv() {
        return mTv;
    }

    public void setTv(long tv) {
        mTv = tv;
    }

    public long getTrashCollection() {
        return mTrashCollection;
    }

    public void setTrashCollection(long trashCollection) {
        mTrashCollection = trashCollection;
    }

    public long getInternet() {
        return mInternet;
    }

    public void setInternet(long internet) {
        mInternet = internet;
    }

    public long getSecurity() {
        return mSecurity;
    }

    public void setSecurity(long security) {
        mSecurity = security;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mProjectId);
        dest.writeLong(this.mElectricity);
        dest.writeLong(this.mWater);
        dest.writeLong(this.mTv);
        dest.writeLong(this.mTrashCollection);
        dest.writeLong(this.mInternet);
        dest.writeLong(this.mSecurity);
        dest.writeByte(this.isChanged ? (byte) 1 : (byte) 0);
    }
}
