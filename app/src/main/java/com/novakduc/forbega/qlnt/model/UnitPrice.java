package com.novakduc.forbega.qlnt.model;

import java.io.Serializable;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class UnitPrice implements Cloneable, Serializable {
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
}
