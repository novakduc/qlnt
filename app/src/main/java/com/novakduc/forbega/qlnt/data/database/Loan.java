package com.novakduc.forbega.qlnt.data.database;

import android.os.Parcel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class Loan implements Cloneable {

    //private Long mProjectId;
    private long mId;
    private String mName;
    private long mAmount;
    private long mLoanDate;
    private double mInterestRate;

    public Loan(String name, long amount, long loanDate, double rate) {
        mId = Calendar.getInstance().getTimeInMillis();
        mName = name;
        mAmount = amount;
        mLoanDate = loanDate;
        mInterestRate = rate;
    }

    protected Loan(Parcel in) {
        this.mId = in.readLong();
        this.mName = in.readString();
        this.mAmount = in.readLong();
        this.mLoanDate = in.readLong();
        this.mInterestRate = in.readDouble();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public long getId() {
        return mId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //Getter setter
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getAmount() {
        return mAmount;
    }

    public void setAmount(long amount) {
        mAmount = amount;
    }

    public double getAmount(CurrencyUnit unit) {
        double convertedAmount = round((double) (mAmount) / unit.getUnit(), 3);
        return convertedAmount;
    }

    public long getLoanDate() {
        return mLoanDate;
    }

    public void setLoanDate(long loanDate) {
        mLoanDate = loanDate;
    }

    public Double getInterestRate() {
        return mInterestRate;
    }

    public void setInterestRate(double interestRate) {
        mInterestRate = interestRate;
    }

    //Tra no
    public void pay(long payAmount) {
        // TODO: 9/21/2016
    }
}
