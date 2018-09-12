package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Observable;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "loan")
public class Loan extends Observable implements Cloneable, ItemWithId {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long amount;
    private long loanDate;
    private double interestRate;
    private long projectId;
    @Ignore
    private ItemContainer<Loan> mItemContainer;

    //For Room only
    public Loan(long id, String name, long amount, long loanDate, double interestRate, long projectId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.loanDate = loanDate;
        this.interestRate = interestRate;
        this.projectId = projectId;
    }

    @Ignore
    private Loan(String name, long projectId) {
        this.name = name;
        this.projectId = projectId;
        this.loanDate = System.currentTimeMillis();
    }

    public static Loan getInstance(String bankName, long projectId) {
        return new Loan(bankName, projectId);
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long newProjectId) {
        this.projectId = newProjectId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Loan loan = (Loan) super.clone();
        loan.setId(0);
        return loan;
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

    public void setItemContainer(ItemContainer<Loan> itemContainer) {
        mItemContainer = itemContainer;
    }

    //Tra no
    public void pay(long payAmount) {
        this.amount = payAmount < this.amount ? this.amount - payAmount : 0;
        this.mItemContainer.update();
    }

    public void payAll() {
        this.amount = 0;
        this.mItemContainer.removeItem(this);
    }
}
