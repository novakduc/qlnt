package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "project")
public class Project implements Cloneable, ItemWithId {

    @PrimaryKey(autoGenerate = true)
    private long projectId;
    private String name;
    private String address;
    private long investmentAmount;
    private long startDate;
    private int yearDuration;
    @Ignore
    private long totalIncome;
    @Ignore
    private UnitPrice mUnitPrice;
    @Ignore
    private LoanList mLoanList;
    @Ignore
    private RoomList mRoomForRentList;
    @Ignore
    private CostManager mCostManager;

    //For Room only
    public Project(long projectId, String name, String address, long investmentAmount,
                   long startDate, int yearDuration) {
        this.projectId = projectId;
        this.name = name;
        this.address = address;
        this.investmentAmount = investmentAmount;
        this.startDate = startDate;
        this.yearDuration = yearDuration;
    }

    @Ignore
    private Project(String name, String address, int yearDuration) {
        this.name = name;
        this.address = address;
        this.yearDuration = yearDuration;
    }

    public static Project getInstance(String name, String address, int yearDuration) {
        return new Project(name, address, yearDuration);
    }

    @Override
    public String toString() {
        if (name == null) {
            return String.valueOf(projectId);
        }
        return name;
    }


    public CostManager getCostManager() {
        return mCostManager;
    }

    public void setCostManager(CostManager pCostManager) {
        mCostManager = pCostManager;
    }

    public RoomList getRoomForRentList() {
        return mRoomForRentList;
    }

    private void setRoomForRentList(RoomList list) {
        mRoomForRentList = list;
    }

    //Add cost
    public boolean addCost(long amount, long date, CostType type, boolean repeatable) {
        return mCostManager.add(new Cost(amount, date, type, repeatable));
    }

    //Add loan
    public Boolean addLoan(Loan loan) {
        return mLoanList.add(loan);
    }

    public long getEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(startDate);
        endDate.add(Calendar.YEAR, yearDuration);
        return endDate.getTimeInMillis();
    }

    public int getStartYear() {
        Calendar tmp = Calendar.getInstance();
        tmp.setTimeInMillis(startDate);
        return tmp.get(Calendar.YEAR);
    }

    public int getEndYear() {
        Calendar tmp = Calendar.getInstance();
        tmp.setTimeInMillis(getEndDate());
        return tmp.get(Calendar.YEAR);
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setInvestmentAmount(long investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public int getYearDuration() {
        return yearDuration;
    }

    public void setYearDuration(int yearDuration) {
        this.yearDuration = yearDuration;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Project project = (Project) super.clone();
        project.setProjectId(0);
//        if (mUnitPrice != null) {
//            project.setUnitPrice((UnitPrice) mUnitPrice.clone());
//        }
//        //Clone loan list
//        if (mLoanList != null) {
//            LoanList loanList = LoanList.getInstance(project.getId());
//            for (Loan l :
//                    mLoanList) {
//                loanList.add((Loan) l.clone());
//            }
//            project.setLoanList(loanList);
//        }
//        //Clone room list
//        if (mRoomForRentList != null) {
//            RoomList roomForRents = new RoomList(project.getId());
//            for (RoomForRent roomForRent :
//                    mRoomForRentList) {
//                roomForRents.add((RoomForRent) roomForRent.clone());
//            }
//            project.setRoomForRentList(roomForRents);
//        }

        return project;
    }

    // Getter and setter section
    public long getProjectId() {
        return projectId;
    }

    private void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(Long investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public long getTotalIncome() {
        return totalIncome;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return yearDuration;
    }

    public void setDuration(int yearDuration) {
        this.yearDuration = yearDuration;
    }

    public UnitPrice getUnitPrice() {
        return mUnitPrice;
    }

    public void setUnitPrice(UnitPrice unitPrice) {
        mUnitPrice = unitPrice;
    }

    //Parcel

    public LoanList getLoanList() {
        return mLoanList;
    }

    public void setLoanList(LoanList loanList) {
        mLoanList = loanList;
    }

    @Override
    public long getId() {
        return projectId;
    }

    @Override
    public void setId(long id) {
        this.projectId = id;
    }

    @Override
    public long getAmount() {
        return this.investmentAmount;
    }

    ////Below is for Parcelable
    /////////////////////////////

}
