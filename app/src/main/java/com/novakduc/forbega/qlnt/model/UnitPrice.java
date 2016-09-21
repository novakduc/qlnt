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
}
