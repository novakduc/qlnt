package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class UnitPrice {
    private static UnitPrice ourInstance = new UnitPrice();
    private static Long sActiveProjectId;
    private Long mElectricity;
    private Long mWater;
    private Long mTv;
    private Long mTrashCollection;
    private Long mInternet;
    private Long mSecurity;
    private Long mId;

    public static UnitPrice getInstance(Long projectId) {
        sActiveProjectId = projectId;
        return ourInstance;
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

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

}
