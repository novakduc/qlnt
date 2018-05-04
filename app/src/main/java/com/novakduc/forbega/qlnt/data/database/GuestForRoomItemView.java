package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by n.thanh on 9/21/2016.
 */
//This is for storing data for viewing in Room Item
public class GuestForRoomItemView {
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    //For Room only
    public GuestForRoomItemView(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    //Getter setter
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
