package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by Nguyen Quoc Thanh on 1/3/2018.
 */

@Database(entities = {Cost.class}, version = 1)
@TypeConverters(CostTypeConverter.class)
public abstract class QlntDataBase extends RoomDatabase {
    private static final String DB_NAME = "qlnt_database";

    //Singleton
    private static final Object LOCK = new Object();
    private static volatile QlntDataBase sInstance;

    public static QlntDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, QlntDataBase.class,
                            QlntDataBase.DB_NAME).build();
                }
            }
        }
        return sInstance;
    }

    public abstract QlntDao qlntDao();
}
