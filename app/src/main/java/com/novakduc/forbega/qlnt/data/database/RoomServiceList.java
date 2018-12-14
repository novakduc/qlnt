package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by Novak on 1/7/2018.
 */
@Entity(tableName = "room_service_list", indices = @Index(value = {"roomId"}, unique = true))
public class RoomServiceList<E> extends MyArrayList<E> {
    @PrimaryKey
    private long roomId;
    private String idListGSonString;
    @Ignore
    private ArrayList idList;

    //For Room only
    public RoomServiceList(long roomId, String idListGSonString) {
        this.roomId = roomId;
        this.idListGSonString = idListGSonString;

        this.idList = gSonStringToList();
    }

    @Ignore
    public RoomServiceList(long roomId) {
        super(10);
        this.roomId = roomId;
    }

    public long getRoomId() {
        return roomId;
    }

    @Override
    public String getIdListGSonString() {
        return idListGSonString;
    }

    @Override
    public ArrayList getIdList() {
        return idList;
    }
}
