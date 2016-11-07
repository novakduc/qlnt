package com.novakduc.forbega.qlnt.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */

public class Project implements Cloneable, Serializable {

    private long mProjectId;
    private String mName;
    private String mAddress;
    private long mInvestment;
    private long mTotalIncome;
    private Calendar mStartDate;
    private int mYearDuration;
    private UnitPrice mUnitPrice;
    private LoanList<Loan> mLoanList;
    private RoomList<Room> mRoomList;

    public Project() {
        mProjectId = Calendar.getInstance().getTimeInMillis();
        mLoanList = new LoanList<Loan>();
        mUnitPrice = new UnitPrice(mProjectId);
    }

    @Override
    public String toString() {
        if (mName == null) {
            return String.valueOf(mProjectId);
        }
        return mName;
    }

    public Boolean addLoan(String name, long amount, Calendar loanDate, double rate) {
        Loan loan = new Loan(name, amount, loanDate, rate);
        return mLoanList.add(loan);
    }

    public Calendar getEndDate() {
        Calendar endDate = Calendar.getInstance();
        // TODO: 11/3/2016 Calculate end date based on start date and duration
        return endDate;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Project project = (Project) super.clone();
        project.setUnitPrice((UnitPrice) mUnitPrice.clone());
        //Clone loan list
        LoanList<Loan> loanList = new LoanList<Loan>();
        for (Loan loan :
                mLoanList) {
            loanList.add((Loan) loan.clone());
        }
        project.setLoanList((LoanList<Loan>) mLoanList.clone());
        //Clone room list
        RoomList<Room> rooms = new RoomList<Room>();
        for (Room room :
                mRoomList) {
            rooms.add((Room) room.clone());
        }
        project.setRoomList((RoomList<Room>) mRoomList.clone());
        return project;
    }

    //Unit price setting section
    public void setElectricityUnitPrice(Long electricityUnitPrice) {
        mUnitPrice.setElectricity(electricityUnitPrice);
    }

    public void setWaterUnitPrice(Long waterUnitPrice) {
        mUnitPrice.setWater(waterUnitPrice);
    }

    public void setTvUnitPrice(Long tvUnitPrice) {
        mUnitPrice.setTv(tvUnitPrice);
    }

    public void setTrashCollectionUnitPrice(long trashCollectionUnitPrice) {
        mUnitPrice.setTrashCollection(trashCollectionUnitPrice);
    }

    public void setSecurityUnitPrice(long securityUnitPrice) {
        mUnitPrice.setSecurity(securityUnitPrice);
    }

    public void setInternetUnitPrice(long internetUnitPrice) {
        mUnitPrice.setInternet(internetUnitPrice);
    }

    // Getter and setter section
    public long getProjectId() {
        return mProjectId;
    }

    public void setProjectId(Long projectId) {
        mProjectId = projectId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public long getInvestment() {
        return mInvestment;
    }

    public void setInvestment(Long investment) {
        mInvestment = investment;
    }

    public long getTotalIncome() {
        return mTotalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        mTotalIncome = totalIncome;
    }

    public Calendar getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Calendar startDate) {
        mStartDate = startDate;
    }

    public int getDuration() {
        return mYearDuration;
    }

    public void setDuration(int yearDuration) {
        mYearDuration = yearDuration;
    }

    public UnitPrice getUnitPrice() {
        return mUnitPrice;
    }

    private void setUnitPrice(UnitPrice unitPrice) {
        mUnitPrice = unitPrice;
    }

    public LoanList<Loan> getLoanList() {
        return mLoanList;
    }

    private void setLoanList(LoanList<Loan> loanList) {
        mLoanList = loanList;
    }

    private void setRoomList(RoomList<Room> list) {
        mRoomList = list;
    }
}
