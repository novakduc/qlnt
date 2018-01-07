package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */
@Entity(tableName = "room_list", indices = @Index(value = {"roomId"}, unique = true))
public class RoomList<E> extends MyArrayList<E> {
    private long roomId;
    private String idListGSonString;
    @Ignore
    private ArrayList idList;

    //For Room only
    public RoomList(long roomId, String idListGSonString) {
        this.roomId = roomId;
        this.idListGSonString = idListGSonString;

        this.idList = gSonStringToList();
    }

    public RoomList(long roomId) {
        super(10);
        this.roomId = roomId;
    }

    public int getNoOfProducingRoom() {
        return this.size() - getNoOfAvailableRoom();
    }

    private int getNoOfAvailableRoom() {
        int availableCount = 0;
        RoomForRent roomForRent;
        for (E i :
                this) {
            roomForRent = (RoomForRent) i;
            if (roomForRent.isAvailable()) availableCount++;
        }
        return availableCount;
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
