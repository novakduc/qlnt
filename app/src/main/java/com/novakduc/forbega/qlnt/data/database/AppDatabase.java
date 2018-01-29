package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by Nguyen Quoc Thanh on 1/3/2018.
 */

@Database(entities = {Cost.class, CostManager.class, Loan.class, LoanList.class,
        Project.class, RoomForRent.class, RoomList.class, RoomService.class,
        RoomServiceList.class, UnitPrice.class}, version = 3, exportSchema = true)
@TypeConverters(CostTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "com.novakduc.forbega.database";

    //Singleton
    private static final Object LOCK = new Object();
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context, AppDatabase.class,
                            AppDatabase.DB_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return sInstance;
    }

    public abstract AppDao appDao();
}
