package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Observable;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "loan")
public class Loan extends Observable implements Cloneable, ItemWithId, Parcelable {

    public static final Parcelable.Creator<Loan> CREATOR = new Parcelable.Creator<Loan>() {
        @Override
        public Loan createFromParcel(Parcel source) {
            return new Loan(source);
        }

        @Override
        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };
    @PrimaryKey
    private long id;
    private String name;
    private long amount;
    private long loanDate;
    private double interestRate;
    @Ignore
    private ItemContainer<Loan> mItemContainer;

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

    @Ignore
    protected Loan(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.amount = in.readLong();
        this.loanDate = in.readLong();
        this.interestRate = in.readDouble();
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

    ///Below section is for Parcelable
    ////////////////////////////////

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.amount);
        dest.writeLong(this.loanDate);
        dest.writeDouble(this.interestRate);
    }
}
