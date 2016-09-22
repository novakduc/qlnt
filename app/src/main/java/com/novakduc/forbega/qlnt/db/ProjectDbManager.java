package com.novakduc.forbega.qlnt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.novakduc.forbega.qlnt.model.Project;

/**
 * Created by n.thanh on 10/27/2015.
 */
public class ProjectDbManager implements StringForDB {
    private SQLiteDatabase mDatabase;
    private QlntSQLiteOpenHelper mHelper;
    private Context mContext;

    public ProjectDbManager(Context context) {
        mContext = context;
        mHelper = new QlntSQLiteOpenHelper(context);
        //mDatabase = helper.getReadableDatabase();
    }

    protected QlntSQLiteOpenHelper getHelper() {
        return mHelper;
    }

    //Record project information
    public Long recordProject(Project project) {
        mDatabase = mHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PROJECT_ID_ROW, project.getProjectId());
        contentValues.put(PROJECT_NAME_ROW, project.getName());
        contentValues.put(PROJECT_ADDRESS_ROW, project.getAddress());
        contentValues.put(PROJECT_END_DATE_ROW, project.getEndDate().getTimeInMillis());
        contentValues.put(PROJECT_INVESTMENT_ROW, project.getInvestment());
        contentValues.put(PROJECT_START_DATE_ROW, project.getStartDate().getTimeInMillis());
        contentValues.put(PROJECT_TOTAL_INCOME_ROW, project.getTotalIncome());
        return mDatabase.insert(PROJECT_TABLE, null, contentValues);
    }

}
