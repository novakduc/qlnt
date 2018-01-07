package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.ArrayList;

/**
 * Created by Novak on 1/7/2018.
 */
@Entity(tableName = "room_service_list", indices = @Index(value = {"roomId"}, unique = true))
public class RoomServiceList<E> extends MyArrayList<E> {
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

    @Override
    public String getIdListGSonString() {
        return idListGSonString;
    }

    @Override
    public ArrayList getIdList() {
        return idList;
    }
}
