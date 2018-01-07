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
public class Project implements Cloneable, ItemWithId {

    @PrimaryKey
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
    private LoanList<Loan> mLoanList;
    @Ignore
    private RoomList<RoomForRent> mRoomForRentList;
    @Ignore
    private CostManager<Cost> mCostManager;

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
    public Project() {
        projectId = Calendar.getInstance().getTimeInMillis();
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
        return mRoomForRentList.add(new RoomForRent(this.projectId, name, area, charge));
    }

    @Override
    public String toString() {
        if (name == null) {
            return String.valueOf(projectId);
        }
        return name;
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
        return Loan.round((double) (investmentAmount) / unit.getUnit(), 3);
    }

    //Add cost
    public boolean addCost(long amount, CostType type, long date, boolean repeatable) {
        return mCostManager.add(new Cost(projectId, amount, type, date, repeatable));
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

    public LoanList<Loan> getLoanList() {
        return mLoanList;
    }

    private void setLoanList(LoanList<Loan> loanList) {
        mLoanList = loanList;
    }

    @Override
    public long getId() {
        return projectId;
    }

    @Override
    public long getAmount() {
        return this.investmentAmount;
    }
}
