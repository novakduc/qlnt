package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "loan")
public class Loan implements Cloneable, ItemWithId {

    private Long projectId;
    @PrimaryKey
    private long id;
    private String name;
    private long amount;
    private long loanDate;
    private double interestRate;

    //For Room only
    public Loan(Long projectId, long id, String name, long amount, long loanDate, double interestRate) {
        this.projectId = projectId;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.loanDate = loanDate;
        this.interestRate = interestRate;
    }

    @Ignore
    public Loan(long projectId, String name, long amount, long loanDate, double interestRate) {
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

    public Long getProjectId() {
        return projectId;
    }

    private void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public double getAmount(CurrencyUnit unit) {
        double convertedAmount = round((double) (amount) / unit.getUnit(), 3);
        return convertedAmount;
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
        // TODO: 9/21/2016
    }
}
