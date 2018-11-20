package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Observable;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "guest")
public class Guest extends Observable implements Cloneable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String phoneNumber;
    private String guestId;
    private int keyContact;
    private long roomId;
    private long checkOutDate;

    //For Room only
    public Guest(long id, String name, String phoneNumber, String guestId,
                 int keyContact, long roomId, long checkOutDate) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.guestId = guestId;
        this.keyContact = keyContact;
        this.roomId = roomId;
        this.checkOutDate = checkOutDate;
    }

    @Ignore
    public Guest(String pName, long pRoomId) {
        name = pName;
        roomId = pRoomId;
        this.checkOutDate = -1;
    }

    public static Guest getInstance(String name, long roomId) {
        return new Guest(name, roomId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Guest guest = (Guest) super.clone();
        guest.setId(-1);
        return guest;
    }

    public long getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(long pCheckOutDate) {
        checkOutDate = pCheckOutDate;
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

    public boolean isKeyContact() {
        return keyContact != 0;
    }

    public int getKeyContact() {
        return keyContact;
    }

    public void setKeyContact(boolean keyContact) {
        this.keyContact = keyContact ? 1 : 0;
    }

    @Override
    public String toString() {
        return "\nId:" + id + "; name:" + name;
    }
}
