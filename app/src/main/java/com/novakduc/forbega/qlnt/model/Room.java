package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 9/30/2016.
 */

public class Room implements Cloneable {
    // TODO: 9/30/2016
    private String mName;
    private double area;
    private long charge;
    private Project mProject; // TODO: 12/2/2016 check check ...

    public Room(String name, double area, long charge) {
        mName = name;
        this.area = area;
        this.charge = charge;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO: 9/30/2016
        return super.clone();
    }
}
