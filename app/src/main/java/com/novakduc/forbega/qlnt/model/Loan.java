package com.novakduc.forbega.qlnt.model;

import java.util.Date;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class Loan {
    private String mName;
    private Long mAmount;
    private Date mLoanDate;
    private Double mInterestRate;

    public Loan(String name, Long amount, Date loanDate, Double rate) {
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

    public Date getLoanDate() {
        return mLoanDate;
    }

    public void setLoanDate(Date loanDate) {
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
