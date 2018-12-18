package com.novakduc.forbega.qlnt.ui.detail.room;

import androidx.room.ColumnInfo;

/**
 * Created by n.thanh on 9/21/2016.
 */
//This is for storing data for viewing in Room Item
public class GuestForRoomItemView {
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @ColumnInfo(name = "roomId")
    private long roomId;

    //For Room only
    public GuestForRoomItemView(String name, String phoneNumber, long roomId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.roomId = roomId;
    }

    //Getter setter
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getRoomId() {
        return roomId;
    }
}
