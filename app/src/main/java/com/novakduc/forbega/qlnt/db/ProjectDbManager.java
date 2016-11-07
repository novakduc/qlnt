package com.novakduc.forbega.qlnt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.novakduc.forbega.qlnt.model.Loan;
import com.novakduc.forbega.qlnt.model.LoanList;
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
    public long recordProject(Project project) {
        mDatabase = mHelper.getWritableDatabase();

        //Record project
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROJECT_ID_ROW, project.getProjectId());
        contentValues.put(PROJECT_NAME_ROW, project.getName());
        contentValues.put(PROJECT_ADDRESS_ROW, project.getAddress());
        contentValues.put(PROJECT_END_DATE_ROW, project.getEndDate().getTimeInMillis());
        contentValues.put(PROJECT_INVESTMENT_ROW, project.getInvestment());
        contentValues.put(PROJECT_START_DATE_ROW, project.getStartDate().getTimeInMillis());
        contentValues.put(PROJECT_TOTAL_INCOME_ROW, project.getTotalIncome());

        mDatabase.insert(PROJECT_TABLE, null, contentValues);

        //record unit price
        contentValues = new ContentValues();
        contentValues.put(UP_PROJECT_ID_ROW, project.getProjectId());
        contentValues.put(UP_ELECTRICITY_ROW, project.getUnitPrice().getElectricity());
        contentValues.put(UP_WATER_ROW, project.getUnitPrice().getWater());
        contentValues.put(UP_INTERNET_ROW, project.getUnitPrice().getInternet());
        contentValues.put(UP_TV_ROW, project.getUnitPrice().getTv());
        contentValues.put(UP_TRASH_COLLECTION_ROW, project.getUnitPrice().getTrashCollection());
        contentValues.put(UP_SECURITY_ROW, project.getUnitPrice().getSecurity());

        mDatabase.insert(UNIT_PRICE_TABLE, null, contentValues);

        //Record loans
        LoanList<Loan> loanList = project.getLoanList();

        for (Loan tmpLoan : loanList) {
            contentValues = new ContentValues();
            contentValues.put(LOAN_PROJECT_ID, project.getProjectId());
            contentValues.put(LOAN_NAME, tmpLoan.getName());
            contentValues.put(LOAN_AMOUNT, tmpLoan.getAmount());
            contentValues.put(LOAN_DATE, tmpLoan.getLoanDate().getTimeInMillis());
            contentValues.put(LOAN_INTEREST_RATE, tmpLoan.getInterestRate());

            mDatabase.insert(LOAN_TABLE, null, contentValues);
        }

        return project.getProjectId();
    }
}
