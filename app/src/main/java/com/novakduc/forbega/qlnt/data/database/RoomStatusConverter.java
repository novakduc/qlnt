package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by Nguyen Quoc Thanh on 1/3/2018.
 */

public class RoomStatusConverter {

    @TypeConverter
    public static RoomStatus toRoomStatus(int order) {
        switch (order) {
            case 0:
                return RoomStatus.NORMAL;
            case 1:
                return RoomStatus.WAITING_FOR_BILL;
            case 2:
                return RoomStatus.WAITING_FOR_PAYMENT;
            case -1:
                return RoomStatus.AVAILABLE;
            default:
                return RoomStatus.AVAILABLE;
        }
    }

    @TypeConverter
    public static int toInt(RoomStatus status) {
        return status == null ? 0 : status.getOrder();
    }
}
