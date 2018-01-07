package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Observable;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "loan")
public class Loan extends Observable implements Cloneable, ItemWithId {

    @PrimaryKey
    private long id;
    private String name;
    private long amount;
    private long loanDate;
    private double interestRate;

    //For Room only
    public Loan(long id, String name, long amount, long loanDate, double interestRate) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.loanDate = loanDate;
        this.interestRate = interestRate;
    }

    @Ignore
    public Loan(String name, long amount, long loanDate, double interestRate) {
        id = Calendar.getInstance().getTimeInMillis();
        this.name = name;
        this.amount = amount;
        this.loanDate = loanDate;
        this.interestRate = interestRate;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //Getter setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(long loanDate) {
        this.loanDate = loanDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    //Tra no
    public void pay(long payAmount) {
        this.amount = payAmount < this.amount ? this.amount - payAmount
                : 0;
        updateToObserver();
    }

    public void payAll() {
        this.amount = 0;
        updateToObserver();
    }

    private void updateToObserver() {
        setChanged();
        if (amount <= 0) {
            notifyObservers(LoanList.DELETE);
        } else {
            notifyObservers(LoanList.TOTAL_AMOUNT_CHANGE);
        }
    }
}
