package com.novakduc.forbega.qlnt.model;

import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class Loan implements Cloneable {
    //private Long mProjectId;
    private String mName;
    private long mAmount;
    private Calendar mLoanDate;
    private double mInterestRate;

    public Loan(String name, long amount, Calendar loanDate, double rate) {
        mName = name;
        mAmount = amount;
        mLoanDate = loanDate;
        mInterestRate = rate;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Loan loan = (Loan) super.clone();
        loan.setLoanDate((Calendar) this.mLoanDate.clone());
        return loan;
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

    public Calendar getLoanDate() {
        return mLoanDate;
    }

    public void setLoanDate(Calendar loanDate) {
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
