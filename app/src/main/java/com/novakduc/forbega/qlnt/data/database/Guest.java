package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Observable;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "guess")
public class Guest extends Observable implements Cloneable {
    @PrimaryKey
    private long id;
    private String name;
    private String phoneNumber;
    private String guestId;
    private long roomId;

    //For Room only
    public Guest(long id, String name, String phoneNumber, String guestId, long roomId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.guestId = guestId;
        this.roomId = roomId;
    }

    @Ignore
    private Guest(String name, String guestId, String phoneNumber, long roomId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.guestId = guestId;
        this.roomId = roomId;
    }

    public static Guest getInstance(String name, String guestId, String phoneNumber, long roomId) {
        return new Guest(name, guestId, phoneNumber, roomId);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Guest guest = (Guest) super.clone();
        guest.setId(-1);
        return guest;
    }

    //Getter setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
