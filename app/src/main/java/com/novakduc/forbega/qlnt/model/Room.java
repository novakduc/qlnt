package com.novakduc.forbega.qlnt.model;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/30/2016.
 */

public class Room implements Cloneable {
    // TODO: 9/30/2016
    private String mName;
    private double mArea;
    private long mCharge;
    private ArrayList<RoomService> mRoomServices;
    private Project mProject; // TODO: 12/2/2016 check check ...

    public Room(Project project, String name, double area, long charge) {
        mName = name;
        this.mArea = area;
        this.mCharge = charge;
        mProject = project;
        mRoomServices = new ArrayList<RoomService>(5);
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

    public Project getProject() {
        return mProject;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO: 9/30/2016
        return super.clone();
    }
}
