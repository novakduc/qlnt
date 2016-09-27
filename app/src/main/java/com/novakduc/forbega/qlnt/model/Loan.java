package com.novakduc.forbega.qlnt.model;

import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class Loan {
    //private Long mProjectId;
    private String mName;
    private Long mAmount;
    private Calendar mLoanDate;
    private Double mInterestRate;

    public Loan(String name, Long amount, Calendar loanDate, Double rate) {
        mName = name;
        mAmount = amount;
        mLoanDate = loanDate;
        mInterestRate = rate;
    }

    //Getter setter
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(Long amount) {
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

    public void setInterestRate(Double interestRate) {
        mInterestRate = interestRate;
    }

    //Tra no
    public void pay(Long payAmount) {
        // TODO: 9/21/2016  
    }
}
