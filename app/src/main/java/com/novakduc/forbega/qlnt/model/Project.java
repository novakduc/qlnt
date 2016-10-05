package com.novakduc.forbega.qlnt.model;

import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */

public class Project implements Cloneable {

    private Long mProjectId;
    private String mName;
    private String mAddress;
    private Long mInvestment;
    private Long mTotalIncome;
    private Calendar mStartDate;
    private Calendar mEndDate;
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
            return mProjectId.toString();
        }
        return mName;
    }

    public Boolean addLoan(String name, Long amount, Calendar loanDate, Double rate) {
        Loan loan = new Loan(name, amount, loanDate, rate);
        return mLoanList.add(loan);
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

    public void setTrashCollectionUnitPrice(Long trashCollectionUnitPrice) {
        mUnitPrice.setTrashCollection(trashCollectionUnitPrice);
    }

    public void setSecurityUnitPrice(Long securityUnitPrice) {
        mUnitPrice.setSecurity(securityUnitPrice);
    }

    public void setInternetUnitPrice(Long internetUnitPrice) {
        mUnitPrice.setInternet(internetUnitPrice);
    }

    // Getter and setter section
    public Long getProjectId() {
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

    public Long getInvestment() {
        return mInvestment;
    }

    public void setInvestment(Long investment) {
        mInvestment = investment;
    }

    public Long getTotalIncome() {
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

    public Calendar getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Calendar endDate) {
        mEndDate = endDate;
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
