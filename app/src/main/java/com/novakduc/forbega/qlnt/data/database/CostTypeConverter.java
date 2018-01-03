package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by Nguyen Quoc Thanh on 1/3/2018.
 */

public class CostTypeConverter {

    @TypeConverter
    public static CostType toCostType(int order) {
        switch (order) {
            case 0:
                return CostType.ELECTRICITY;
            case 1:
                return CostType.WATER;
            case 2:
                return CostType.TV_CABLE;
            case 3:
                return CostType.INTERNET;
            case 4:
                return CostType.TRASH_COLLECTION;
            case 5:
                return CostType.SECURITY;
            case 6:
                return CostType.MAINTENANCE;
            default:
                return CostType.OTHERS;
        }
    }

    @TypeConverter
    public static int toInt(CostType type) {
        return type == null ? 0 : type.getOrder();
    }
}
