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
    private boolean isPaid;
    private long paymentDate;

    //For room only
    public Bill(long id, long projectId, long roomId, long roomCharge, long electricalCharge,
                long waterCharge, long tvCharge, long trashCollectionCharge, long internetCharge,
                long securityCharge, long otherCharge, boolean isPaid, long paymentDate) {
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
    }

    @Ignore
    public Bill(long projectId, long roomId) {
        this.projectId = projectId;
        this.roomId = roomId;
        this.isPaid = false;
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
    }

    public long getElectricalCharge() {
        return electricalCharge;
    }

    public void setElectricalCharge(long electricalCharge) {
        this.electricalCharge = electricalCharge;
    }

    public long getWaterCharge() {
        return waterCharge;
    }

    public void setWaterCharge(long waterCharge) {
        this.waterCharge = waterCharge;
    }

    public long getTvCharge() {
        return tvCharge;
    }

    public void setTvCharge(long tvCharge) {
        this.tvCharge = tvCharge;
    }

    public long getTrashCollectionCharge() {
        return trashCollectionCharge;
    }

    public void setTrashCollectionCharge(long trashCollectionCharge) {
        this.trashCollectionCharge = trashCollectionCharge;
    }

    public long getInternetCharge() {
        return internetCharge;
    }

    public void setInternetCharge(long internetCharge) {
        this.internetCharge = internetCharge;
    }

    public long getSecurityCharge() {
        return securityCharge;
    }

    public void setSecurityCharge(long securityCharge) {
        this.securityCharge = securityCharge;
    }

    public long getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(long otherCharge) {
        this.otherCharge = otherCharge;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(long paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public long getAmount() {
        return electricalCharge + internetCharge + otherCharge + roomCharge + securityCharge
                + trashCollectionCharge + tvCharge + waterCharge;
    }
}
