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
    private long charge;
    private boolean available;
    private long projectId;
    @Ignore
    private RoomServiceList<RoomService> roomServices;

    //For Room only
    public RoomForRent(long id, String name, long charge, boolean available, long projectId) {
        this.id = id;
        this.name = name;
        this.charge = charge;
        this.available = available;
        this.projectId = projectId;
    }

    @Ignore
    private RoomForRent(long projectId, String name, long charge) {
        this.projectId = projectId;
        this.name = name;
        this.charge = charge;
        available = true;
        roomServices = new RoomServiceList<RoomService>(this.id);
    }

    @Ignore
    private RoomForRent(long projectId) {
        this.projectId = projectId;
    }

    public RoomForRent getInstance(long projectId) {
        return new RoomForRent(projectId);
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

    public long getCharge() {
        return charge;
    }

    //Below is for Parcelable
    //////////////////////////////

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RoomForRent roomForRent = (RoomForRent) super.clone();
        roomForRent.setId(0);
        return roomForRent;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long newProjectId) {
        this.projectId = newProjectId;
    }
}
