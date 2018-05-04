package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 9/30/2016.
 */

@Entity(tableName = "room")
public class RoomForRent implements Cloneable, ItemWithId {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long charge;
    private boolean available;
    private long projectId;

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
    }

    public RoomForRent getInstance(long projectId, String name, long charge) {
        return new RoomForRent(projectId, name, charge);
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

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RoomForRent roomForRent = (RoomForRent) super.clone();
        roomForRent.setId(-1);
        return roomForRent;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long newProjectId) {
        this.projectId = newProjectId;
    }
}
