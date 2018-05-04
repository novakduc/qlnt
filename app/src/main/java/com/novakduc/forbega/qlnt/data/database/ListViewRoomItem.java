package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.ColumnInfo;

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
}
