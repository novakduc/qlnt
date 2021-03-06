package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by n.thanh on 9/30/2016.
 */

@Entity(tableName = "room")
public class RoomForRent implements Cloneable, ItemWithId {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long charge;
    private RoomStatus status;
    private long projectId;
    private long depositAmount;
    private long checkInDate;
    private int billDate;   //Calendar.DAY_OF_DATE

    //For Room only
    public RoomForRent(long id, String name, long charge, RoomStatus status, long projectId,
                       long depositAmount, long checkInDate, int billDate) {
        this.id = id;
        this.name = name;
        this.charge = charge;
        this.status = status;
        this.projectId = projectId;
        this.depositAmount = depositAmount;
        this.checkInDate = checkInDate;
        this.billDate = billDate;
    }

    @Ignore
    private RoomForRent(long projectId, String name, long charge) {
        this.projectId = projectId;
        this.name = name;
        this.charge = charge;
        this.status = RoomStatus.AVAILABLE;
        this.depositAmount = 0;
        this.billDate = -1;
        this.checkInDate = -1;
    }

    public static RoomForRent getInstance(long projectId, String name, long charge) {
        return new RoomForRent(projectId, name, charge);
    }

    public long getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(long pDepositAmount) {
        depositAmount = pDepositAmount;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getAmount() {
        return charge;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCharge() {
        return charge;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RoomForRent roomForRent = (RoomForRent) super.clone();
        roomForRent.setId(-1);
        return roomForRent;
    }

    public long getProjectId() {
        return projectId;
    }

    private void setProjectId(long newProjectId) {
        this.projectId = newProjectId;
    }

    public long getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(long pCheckInDate) {
        checkInDate = pCheckInDate;
    }

    public int getBillDate() {
        return billDate;
    }

    public void setBillDate(int pBillDate) {
        billDate = pBillDate;
    }

    @Override
    public String toString() {
        return "\nId:" + id + "; room name:" + name;
    }
}
