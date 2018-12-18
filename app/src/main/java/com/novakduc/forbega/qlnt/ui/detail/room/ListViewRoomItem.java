package com.novakduc.forbega.qlnt.ui.detail.room;

import com.novakduc.forbega.qlnt.data.database.RoomStatus;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

/**
 * Created by n.thanh on 9/30/2016.
 */

public class ListViewRoomItem {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "status")
    private RoomStatus status;

    @Ignore
    private GuestForRoomItemView keyContact;

    //For Room only
    public ListViewRoomItem(long id, String name, RoomStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public GuestForRoomItemView getKeyContact() {
        return keyContact;
    }

    public void setKeyContact(GuestForRoomItemView keyContact) {
        this.keyContact = keyContact;
    }
}
