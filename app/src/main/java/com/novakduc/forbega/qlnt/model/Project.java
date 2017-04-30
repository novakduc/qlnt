package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by n.thanh on 9/21/2016.
 */
public class Project extends DBObject implements Cloneable, Parcelable {

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
    private long mProjectId;
    private String mName;
    private String mAddress;
    private long mInvestment;
    private long mTotalIncome;
    private long mStartDate;
    private int mYearDuration;
    private UnitPrice mUnitPrice;
    private LoanList<Loan> mLoanList;
    private RoomList<Room> mRoomList;
    private CostManager<Cost> mCostManager;


    public Project() {
        mProjectId = Calendar.getInstance().getTimeInMillis();
        mLoanList = new LoanList<Loan>();
        mUnitPrice = new UnitPrice(mProjectId);
        mCostManager = new CostManager<Cost>();
        mRoomList = new RoomList<Room>();
    }

    protected Project(Parcel in) {
        this.mProjectId = in.readLong();
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mInvestment = in.readLong();
        this.mTotalIncome = in.readLong();
        this.mStartDate = in.readLong();
        this.mYearDuration = in.readInt();
        this.mUnitPrice = in.readParcelable(UnitPrice.class.getClassLoader());
        this.mLoanList = in.readParcelable(LoanList.class.getClassLoader());
        this.mRoomList = in.readParcelable(RoomList.class.getClassLoader());
        this.mCostManager = in.readParcelable(CostManager.class.getClassLoader());
        this.isChanged = in.readByte() != 0;
    }

    public boolean createRoom(String name, double area, long charge) {
        return mRoomList.add(new Room(this.mProjectId, name, area, charge));
    }

    @Override
    public String toString() {
        if (mName == null) {
            return String.valueOf(mProjectId);
        }
        return mName;
    }

    //Add cost
    public boolean addCost(long amount, CostType type, long date, boolean repeatable) {
        return mCostManager.add(new Cost(amount, type, date, repeatable));
    }

    //Add loan
    public Boolean addLoan(Loan loan) {
        return mLoanList.add(loan);
    }

    public long getEndDate() {
        long endDate = Calendar.getInstance().getTimeInMillis();
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

    private void setUnitPrice(UnitPrice unitPrice) {
        mUnitPrice = unitPrice;
    }

    public LoanList<Loan> getLoanList() {
        return mLoanList;
    }

    //Parcel

    private void setLoanList(LoanList<Loan> loanList) {
        mLoanList = loanList;
    }

    private void setRoomList(RoomList<Room> list) {
        mRoomList = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mProjectId);
        dest.writeString(this.mName);
        dest.writeString(this.mAddress);
        dest.writeLong(this.mInvestment);
        dest.writeLong(this.mTotalIncome);
        dest.writeLong(this.mStartDate);
        dest.writeInt(this.mYearDuration);
        dest.writeParcelable(this.mUnitPrice, flags);
        dest.writeParcelable(this.mLoanList, flags);
        dest.writeParcelable(this.mRoomList, flags);
        dest.writeParcelable(this.mCostManager, flags);
        dest.writeByte(this.isChanged ? (byte) 1 : (byte) 0);
    }
}
