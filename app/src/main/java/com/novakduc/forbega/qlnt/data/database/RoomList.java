package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */
@Entity(tableName = "room_list", indices = @Index(value = {"projectId"}, unique = true))
public class RoomList extends MyArrayList<RoomForRent> {
    @PrimaryKey
    private long projectId;
    private String idListGSonString;
    @Ignore
    private ArrayList idList;

    //For Room only
    public RoomList(long projectId, String idListGSonString) {
        this.projectId = projectId;
        this.idListGSonString = idListGSonString;

        this.idList = gSonStringToList();
    }

    @Ignore
    public RoomList(long projectId) {
        super(10);
        this.projectId = projectId;
    }

    public long getProjectId() {
        return projectId;
    }

    public int getNoOfProducingRoom() {
        return this.size() - getNoOfAvailableRoom();
    }

    private int getNoOfAvailableRoom() {
        int availableCount = 0;
        RoomForRent roomForRent;
        for (RoomForRent i :
                this) {
            roomForRent = i;
            if (roomForRent.getStatus() == RoomStatus.AVAILABLE) availableCount++;
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

    ////Below is for Parcelable
    ////////////////////////////////////

}
