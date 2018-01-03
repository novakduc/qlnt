package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/30/2016.
 */

public class RoomForRent implements Cloneable {
    // TODO: 9/30/2016
    private String mName;
    private double mArea;
    private long mCharge;
    private boolean mAvailable;
    private ArrayList<RoomService> mRoomServices;
    private long mProjectId; // TODO: 12/2/2016 check check ...

    public RoomForRent(long projectId, String name, double area, long charge) {
        mName = name;
        this.mArea = area;
        this.mCharge = charge;
        mAvailable = true;
        mProjectId = projectId;
        mRoomServices = new ArrayList<RoomService>(5);
    }

    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean mAvailable) {
        this.mAvailable = mAvailable;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getArea() {
        return mArea;
    }

    public void setArea(double area) {
        mArea = area;
    }

    public long getCharge() {
        return mCharge;
    }

    public void setCharge(long charge) {
        mCharge = charge;
    }

    public long getProjectId() {
        return mProjectId;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO: 9/30/2016 room clone
        return super.clone();
    }
}
