package com.novakduc.forbega.qlnt.db;

/**
 * Created by n.thanh on 9/22/2016.
 */

public interface StringForDB {
    String DB_NAME = "qlnt";
    int DB_VERSION = 1;
    String PROJECT_TABLE = "PROJECT_table";
    String PROJECT_ID_ROW = "_id";
    String PROJECT_NAME_ROW = "PROJECT_name";
    String PROJECT_ADDRESS_ROW = "PROJECT_address";
    String PROJECT_INVESTMENT_ROW = "PROJECT_INVESTMENT";
    String PROJECT_TOTAL_INCOME_ROW = "PROJECT_TOTAL_INCOME";
    String PROJECT_START_DATE_ROW = "PROJECT_START_DATE";
    String PROJECT_END_DATE_ROW = "PROJECT_END_DATE";
    String CREATE_TABLE_PROJECT =
            "CREATE TABLE " + PROJECT_TABLE + "("
                    + PROJECT_ID_ROW + " INTEGER PRIMARY KEY,"
                    + PROJECT_NAME_ROW + " TEXT,"
                    + PROJECT_ADDRESS_ROW + " TEXT,"
                    + PROJECT_INVESTMENT_ROW + " INTEGER,"
                    + PROJECT_TOTAL_INCOME_ROW + " INTEGER,"
                    + PROJECT_START_DATE_ROW + " INTEGER,"
                    + PROJECT_START_DATE_ROW + " INTEGER" + ");";
    String PHONG_TABLE = "PHONG_TABLE";
    String PHONG_ID_ROW = "_ID";
    String PHONG_NAME_ROW = "PHONG_NAME";
    String PHONG_PRICE_ROW = "PHONG_PRICE";
    String PHONG_PROJECT_ID_ROW = "PHONG_PROJECT_ID_ROW";
    String PHONG_STATUS = "PHONG_STATUS";
    String PHONG_CHECKIN_DATE = "PHONG_CHECKIN_DATE";
    String PHONG_CHECKOUT_DATE = "PHONG_CHECKOUT_DATE";
    String PHONG_BALANCE = "PHONG_BALANCE";
    String PHONG_BILLED_DATE = "PHONG_BILLED_DATE";
    String CREATE_TABLE_PHONG =
            "CREATE TABLE " + PHONG_TABLE + "("
                    + PHONG_ID_ROW + " INTEGER PRIMARY KEY,"
                    + PHONG_NAME_ROW + " TEXT,"
                    + PHONG_PRICE_ROW + " INTEGER,"
                    + PHONG_PROJECT_ID_ROW + " INTEGER,"
                    + PHONG_STATUS + " INTEGER,"
                    + PHONG_CHECKIN_DATE + " INTEGER,"
                    + PHONG_CHECKOUT_DATE + " INTEGER,"
                    + PHONG_BALANCE + " INTEGER,"
                    + PHONG_BILLED_DATE + " INTEGER" + ");";
    String COST_TABLE = "COST_TABLE";
    String COST_ID = "_ID";
    String COST_PROJECT_ID = "PROJECT_ID";
    String COST_LOAI = "LOAI";
    String COST_AMOUNT = "COST_AMOUNT";
    String COST_REPEATABE = "COST_REPEATABLE";
    String COST_DATE = "COST_DATE";
    String CREATE_TABLE_COST =
            "CREATE TABLE " + COST_TABLE + "("
                    + COST_ID + " INTEGER PRIMARY KEY,"
                    + COST_PROJECT_ID + " INTEGER,"
                    + COST_LOAI + " TEXT,"
                    + COST_AMOUNT + " INTEGER,"
                    + COST_REPEATABE + " INTEGER,"
                    + COST_LOAI + " INTEGER" + ");";
    String INCOME_TABLE = "INCOME_TABLE";
    String INCOME_PROJECT_ID = "INCOME_PROJECT_ID";
    String INCOME_PHONG_ID = "INCOME_PHONG_ID";
    String INCOME_TYPE = "INCOME_TYPE";
    String INCOME_AMOUNT = "INCOME_TYPE";
    String INCOME_DATE = "INCOME_DATE";
    String CREATE_TABLE_INCOME =
            "CREATE TABLE " + INCOME_TABLE + "("
                    + INCOME_PROJECT_ID + " INTEGER,"
                    + INCOME_PHONG_ID + " INTEGER,"
                    + INCOME_TYPE + " INTEGER,"
                    + INCOME_AMOUNT + " INTEGER,"
                    + INCOME_DATE + " INTEGER" + ");";
}
