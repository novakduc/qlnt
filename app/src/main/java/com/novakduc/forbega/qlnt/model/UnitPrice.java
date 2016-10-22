package com.novakduc.forbega.qlnt.model;

import java.io.Serializable;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class UnitPrice implements Cloneable, Serializable {
    private Long mProjectId;
    private Long mElectricity;
    private Long mWater;
    private Long mTv;
    private Long mTrashCollection;
    private Long mInternet;
    private Long mSecurity;

    //Getter and setter section
    public UnitPrice(Long projectId) {
        mProjectId = projectId;
    }

    public Long getProjectId() {
        return mProjectId;
    }

    public Long getElectricity() {
        return mElectricity;
    }

    public void setElectricity(Long electricity) {
        mElectricity = electricity;
    }

    public Long getWater() {
        return mWater;
    }

    public void setWater(Long water) {
        mWater = water;
    }

    public Long getTv() {
        return mTv;
    }

    public void setTv(Long tv) {
        mTv = tv;
    }

    public Long getTrashCollection() {
        return mTrashCollection;
    }

    public void setTrashCollection(Long trashCollection) {
        mTrashCollection = trashCollection;
    }

    public Long getInternet() {
        return mInternet;
    }

    public void setInternet(Long internet) {
        mInternet = internet;
    }

    public Long getSecurity() {
        return mSecurity;
    }

    public void setSecurity(Long security) {
        mSecurity = security;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
