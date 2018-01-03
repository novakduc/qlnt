package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class UnitPrice implements Cloneable {

    private long mElectricity;
    private long mWater;
    private long mTv;
    private long mTrashCollection;
    private long mInternet;
    private long mSecurity;

    public long get(CostType type) {
        switch (type) {
            case ELECTRICITY:
                return mElectricity;
            case WATER:
                return mWater;
            case INTERNET:
                return mInternet;
            case SECURITY:
                return mSecurity;
            case TRASH_COLLECTION:
                return mTrashCollection;
            case TV_CABLE:
                return mTv;
            default:
                return -1;
        }
    }

    //Getter and setter section

    public long getElectricity() {
        return mElectricity;
    }

    public void setElectricity(long electricity) {
        mElectricity = electricity;
    }

    public long getWater() {
        return mWater;
    }

    public void setWater(long water) {
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
}
