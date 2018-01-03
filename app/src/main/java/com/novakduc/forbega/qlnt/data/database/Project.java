package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by n.thanh on 9/21/2016.
 */
@Entity(tableName = "project")
public class Project implements Cloneable {

    @PrimaryKey
    private long mProjectId;
    private String mName;
    private String mAddress;
    private long mInvestment;
    private long mStartDate;
    private int mYearDuration;
    @Ignore
    private long mTotalIncome;
    @Ignore
    private UnitPrice mUnitPrice;
    @Ignore
    private LoanList<Loan> mLoanList;
    @Ignore
    private RoomList<RoomForRent> mRoomForRentList;
    @Ignore
    private CostManager<Cost> mCostManager;

    public Project(long mProjectId, String mName, String mAddress, long mInvestment,
                   long mStartDate, int mYearDuration) {
        this.mProjectId = mProjectId;
        this.mName = mName;
        this.mAddress = mAddress;
        this.mInvestment = mInvestment;
        this.mStartDate = mStartDate;
        this.mYearDuration = mYearDuration;
    }

    @Ignore
    public Project() {
        mProjectId = Calendar.getInstance().getTimeInMillis();
        mLoanList = new LoanList<Loan>();
        mCostManager = new CostManager<Cost>();
        mRoomForRentList = new RoomList<RoomForRent>();
    }

    public static String calendarToString(long dateInMilis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMilis);
        Date date = calendar.getTime();
            DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }

    public boolean createRoom(String name, double area, long charge) {
        return mRoomForRentList.add(new RoomForRent(this.mProjectId, name, area, charge));
    }

    @Override
    public String toString() {
        if (mName == null) {
            return String.valueOf(mProjectId);
        }
        return mName;
    }


    public CostManager<Cost> getCostManager() {
        return mCostManager;
    }

    public void setCostManager(CostManager<Cost> pCostManager) {
        mCostManager = pCostManager;
    }

    public RoomList<RoomForRent> getRoomForRentList() {
        return mRoomForRentList;
    }

    private void setRoomForRentList(RoomList<RoomForRent> list) {
        mRoomForRentList = list;
    }

    public double getTotalIncome(CurrencyUnit unit) {
        return Loan.round(((double) (getTotalIncome()) / unit.getUnit()), 3);
    }

    public double getInvestment(CurrencyUnit unit) {
        return Loan.round((double) (mInvestment) / unit.getUnit(), 3);
    }

    //Add cost
    public boolean addCost(long amount, CostType type, long date, boolean repeatable) {
        return mCostManager.add(new Cost(mProjectId, amount, type, date, repeatable));
    }

    //Add loan
    public Boolean addLoan(Loan loan) {
        return mLoanList.add(loan);
    }

    public long getEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(mStartDate);
        endDate.add(Calendar.YEAR, mYearDuration);
        return endDate.getTimeInMillis();
    }

    public int getStartYear() {
        Calendar tmp = Calendar.getInstance();
        tmp.setTimeInMillis(mStartDate);
        return tmp.get(Calendar.YEAR);
    }

    public int getEndYear() {
        Calendar tmp = Calendar.getInstance();
        tmp.setTimeInMillis(getEndDate());
        return tmp.get(Calendar.YEAR);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Project project = (Project) super.clone();
        project.setProjectId(Calendar.getInstance().getTimeInMillis());
        project.setUnitPrice((UnitPrice) mUnitPrice.clone());
        //Clone loan list
        LoanList<Loan> loanList = new LoanList<Loan>();
        for (Loan l :
                mLoanList) {
            loanList.add((Loan) l.clone());
        }
        project.setLoanList(loanList);
        //Clone room list
        RoomList<RoomForRent> roomForRents = new RoomList<RoomForRent>();
        for (RoomForRent roomForRent :
                mRoomForRentList) {
            roomForRents.add((RoomForRent) roomForRent.clone());
        }
        project.setRoomForRentList(roomForRents);
        return project;
    }

    // Getter and setter section
    public long getProjectId() {
        return mProjectId;
    }

    private void setProjectId(Long projectId) {
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

    public long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(long startDate) {
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

    public void setUnitPrice(UnitPrice unitPrice) {
        mUnitPrice = unitPrice;
    }

    //Parcel

    public LoanList<Loan> getLoanList() {
        return mLoanList;
    }

    private void setLoanList(LoanList<Loan> loanList) {
        mLoanList = loanList;
    }

}
