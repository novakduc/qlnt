package com.novakduc.forbega.qlnt.model;

import java.util.Date;

/**
 * Created by n.thanh on 9/21/2016.
 */

public class Project {

    private String mName;
    private String mAddress;
    private Long mInvestment;
    private Long mTotalIncome;
    private Date mStartDate;
    private Date mEndDate;
    private UnitPrice mUnitPrice;
    private LoanList mLoanList;

    public Boolean addLoan(String name, Long amount, Date loanDate, Double rate) {
        Loan loan = new Loan(name, amount, loanDate, rate);
        return mLoanList.add(loan);
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

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public UnitPrice getUnitPrice() {
        return mUnitPrice;
    }

}
