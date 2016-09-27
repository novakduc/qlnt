package com.novakduc.forbega.qlnt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by n.thanh on 9/22/2016.
 */

class QlntSQLiteOpenHelper extends SQLiteOpenHelper implements StringForDB {

    public QlntSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROJECT);
        db.execSQL(CREATE_TABLE_PHONG);
        db.execSQL(CREATE_TABLE_COST);
        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_UNIT_PRICE);
        db.execSQL(CREATE_TABLE_LOAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS ";
        db.execSQL(DROP_TABLE + PROJECT_TABLE);
        db.execSQL(DROP_TABLE + PHONG_TABLE);
        db.execSQL(DROP_TABLE + COST_TABLE);
        db.execSQL(DROP_TABLE + INCOME_TABLE);
        db.execSQL(DROP_TABLE + UNIT_PRICE_TABLE);
        db.execSQL(DROP_TABLE + LOAN_TABLE);

        onCreate(db);
    }
}
