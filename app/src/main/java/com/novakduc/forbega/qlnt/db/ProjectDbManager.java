package com.novakduc.forbega.qlnt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.novakduc.forbega.qlnt.data.database.Loan;
import com.novakduc.forbega.qlnt.data.database.LoanList;
import com.novakduc.forbega.qlnt.data.database.Project;

/**
 * Created by n.thanh on 10/27/2015.
 */
public class ProjectDbManager implements StringForDB {
    private final String TAG = "Project Database Manger";
    private SQLiteDatabase mDatabase;
    private QlntSQLiteOpenHelper mHelper;
    private Context mContext;
    private FirebaseFirestore mFirestoreDB;

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
        contentValues.put(PROJECT_END_DATE_ROW, project.getEndDate());
        contentValues.put(PROJECT_INVESTMENT_ROW, project.getInvestment());
        contentValues.put(PROJECT_START_DATE_ROW, project.getStartDate());
        contentValues.put(PROJECT_TOTAL_INCOME_ROW, project.getTotalIncome());

        mDatabase.insert(PROJECT_TABLE, null, contentValues);
        // TODO: 12/28/2017 Check on uploading project to cloudFire
        /*
        mFirestoreDB.collection("projects")
                .add(contentValues)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference pDocumentReference) {
                        Log.d(TAG, "Project is written to cloud fire");
                    }
                });
         */

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
            contentValues.put(LOAN_ID, tmpLoan.getId());
            contentValues.put(LOAN_NAME, tmpLoan.getName());
            contentValues.put(LOAN_AMOUNT, tmpLoan.getAmount());
            contentValues.put(LOAN_DATE, tmpLoan.getLoanDate());
            contentValues.put(LOAN_INTEREST_RATE, tmpLoan.getInterestRate());

            mDatabase.insert(LOAN_TABLE, null, contentValues);
        }

        mHelper.close();
        return project.getProjectId();
    }
}
