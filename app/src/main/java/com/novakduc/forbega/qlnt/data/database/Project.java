package com.novakduc.forbega.qlnt.data.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static String calendarToString(long dateInMilis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMilis);
        Date date = calendar.getTime();
            DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
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


    public CostManager<Cost> getCostManager() {
        return mCostManager;
    }

    public void setCostManager(CostManager<Cost> pCostManager) {
        mCostManager = pCostManager;
    }

    public RoomList<Room> getRoomList() {
        return mRoomList;
    }

    private void setRoomList(RoomList<Room> list) {
        mRoomList = list;
    }

    public double getTotalIncome(CurrencyUnit unit) {
        return Loan.round(((double) (getTotalIncome()) / unit.getUnit()), 3);
    }

    public double getInvestment(CurrencyUnit unit) {
        return Loan.round((double) (mInvestment) / unit.getUnit(), 3);
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
        RoomList<Room> rooms = new RoomList<Room>();
        for (Room room :
                mRoomList) {
            rooms.add((Room) room.clone());
        }
        project.setRoomList(rooms);
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
