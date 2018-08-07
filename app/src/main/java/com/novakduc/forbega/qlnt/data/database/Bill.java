package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "bill")
public class Bill implements ItemWithId {
    @PrimaryKey
    private long id;
    private long projectId;
    private long roomId;
    private long roomCharge;
    private long electricalCharge;
    private long waterCharge;
    private long tvCharge;
    private long trashCollectionCharge;
    private long internetCharge;
    private long securityCharge;
    private long otherCharge;
    private int isPaid;
    private long paymentDate;
    private long amount;
    private String description;

    //For room only
    public Bill(long id, long projectId, long roomId, long roomCharge, long electricalCharge,
                long waterCharge, long tvCharge, long trashCollectionCharge, long internetCharge,
                long securityCharge, long otherCharge, int isPaid, long paymentDate, long amount,
                String description) {
        this.id = id;
        this.projectId = projectId;
        this.roomId = roomId;
        this.roomCharge = roomCharge;
        this.electricalCharge = electricalCharge;
        this.waterCharge = waterCharge;
        this.tvCharge = tvCharge;
        this.trashCollectionCharge = trashCollectionCharge;
        this.internetCharge = internetCharge;
        this.securityCharge = securityCharge;
        this.otherCharge = otherCharge;
        this.isPaid = isPaid;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.description = description;
    }

    @Ignore
    public Bill(long projectId, long roomId) {
        this.projectId = projectId;
        this.roomId = roomId;
        this.isPaid = 0;
        this.paymentDate = -1;
        this.id = Calendar.getInstance().getTimeInMillis();
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(long roomCharge) {
        this.roomCharge = roomCharge;
        updateAmount();
    }

    public long getElectricalCharge() {
        return electricalCharge;
    }

    public void setElectricalCharge(long electricalCharge) {
        this.electricalCharge = electricalCharge;
        updateAmount();
    }

    public long getWaterCharge() {
        return waterCharge;
    }

    public void setWaterCharge(long waterCharge) {
        this.waterCharge = waterCharge;
        updateAmount();
    }

    public long getTvCharge() {
        return tvCharge;
    }

    public void setTvCharge(long tvCharge) {
        this.tvCharge = tvCharge;
        updateAmount();
    }

    public long getTrashCollectionCharge() {
        return trashCollectionCharge;
    }

    public void setTrashCollectionCharge(long trashCollectionCharge) {
        this.trashCollectionCharge = trashCollectionCharge;
        updateAmount();
    }

    public long getInternetCharge() {
        return internetCharge;
    }

    public void setInternetCharge(long internetCharge) {
        this.internetCharge = internetCharge;
        updateAmount();
    }

    public long getSecurityCharge() {
        return securityCharge;
    }

    public void setSecurityCharge(long securityCharge) {
        this.securityCharge = securityCharge;
        updateAmount();
    }

    public long getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(long otherCharge) {
        this.otherCharge = otherCharge;
        updateAmount();
    }

    public boolean isPaid() {
        return isPaid != 0;
    }

    public void setPaid(boolean paid) {
        isPaid = paid ? 1 : 0;
    }

    public long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(long paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {

    }

    private void updateAmount() {
        this.amount = electricalCharge + internetCharge + otherCharge + roomCharge + securityCharge
                + trashCollectionCharge + tvCharge + waterCharge;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}
