package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */

public class RoomList<E> extends ArrayList<E> {
    // TODO: 3/29/2017

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
}
