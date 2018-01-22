package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 9/30/2016.
 */

@Entity(tableName = "room")
public class RoomForRent implements Cloneable, ItemWithId {
    // TODO: 9/30/2016
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private double area;
    private long charge;
    private boolean available;
    @Ignore
    private RoomServiceList<RoomService> roomServices;

    //For Room only
    public RoomForRent(long id, String name, double area, long charge, boolean available) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.charge = charge;
        this.available = available;
    }

    @Ignore
    public RoomForRent(String name, double area, long charge) {
        this.name = name;
        this.area = area;
        this.charge = charge;
        available = true;
        roomServices = new RoomServiceList<RoomService>(this.id);
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getAmount() {
        return charge;
    }

    public RoomServiceList<RoomService> getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(RoomServiceList<RoomService> roomServices) {
        this.roomServices = roomServices;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean mAvailable) {
        this.available = mAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public long getCharge() {
        return charge;
    }

    //Below is for Parcelable
    //////////////////////////////

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO: 9/30/2016 room clone
        return super.clone();
    }
}
