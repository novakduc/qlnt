package com.novakduc.forbega.qlnt.ui.list;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

/**
 * Created by Nguyen Quoc Thanh on 2/6/2018.
 */
///This is brief info of project for list view, no address information
public class ListViewProjectItem {
    @ColumnInfo(name = "projectId")
    private long projectId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "investmentAmount")
    private long investmentAmount;
    @ColumnInfo(name = "startDate")
    private long startDate;
    @ColumnInfo(name = "yearDuration")
    private int yearDuration;

    @Ignore
    private long income, debt;
    @Ignore
    private int noOfRoom, noOfAvailableRoom;
    @Ignore
    private long totalCostAmount;

    //For room only
    public ListViewProjectItem(long projectId, String name, long investmentAmount, long startDate,
                               int yearDuration) {
        this.projectId = projectId;
        this.name = name;
        this.investmentAmount = investmentAmount;
        this.startDate = startDate;
        this.yearDuration = yearDuration;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public long getInvestmentAmount() {
        return investmentAmount;
    }

    public long getStartDate() {
        return startDate;
    }

    public int getYearDuration() {
        return yearDuration;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getDebt() {
        return debt;
    }

    public void setDebt(long debt) {
        this.debt = debt;
    }

    public long getRevenue() {
        return income - totalCostAmount;
    }

    public int getNoOfProducingRoom() {
        return noOfRoom - noOfAvailableRoom;
    }

    public int getNoOfRoom() {
        return noOfRoom;
    }

    public void setNoOfRoom(int noOfRoom) {
        this.noOfRoom = noOfRoom;
    }

    public double getRate() {
        if (noOfRoom == 0) return 0;
        return getNoOfProducingRoom() * 1.0 / noOfRoom;
    }

    public void setNoOfAvailableRoom(int noOfAvailableRoom) {
        this.noOfAvailableRoom = noOfAvailableRoom;
    }

    public void setTotalCostAmount(long totalCostAmount) {
        this.totalCostAmount = totalCostAmount;
    }

    public int getIncomeProgress() {
        return (int) (investmentAmount == 0 ? 1000 : income * 1000 / investmentAmount);
    }

    public int getDebtProgress() {
        return (int) (investmentAmount == 0 ? 0 : debt * 1000 / investmentAmount);
    }

    public int getRevenueProgress() {
        return (int) (investmentAmount == 0 ? 1000 : getRevenue() * 1000 / investmentAmount);
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

    public long getId() {
        return projectId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ListViewProjectItem) {
            ListViewProjectItem listViewProjectItem = (ListViewProjectItem) obj;
            return this.name == listViewProjectItem.name
                    && this.income == listViewProjectItem.income
                    && this.debt == listViewProjectItem.debt
                    && this.investmentAmount == listViewProjectItem.investmentAmount
                    && this.noOfAvailableRoom == listViewProjectItem.noOfAvailableRoom
                    && this.noOfRoom == listViewProjectItem.noOfRoom
                    && this.startDate == listViewProjectItem.startDate
                    && this.yearDuration == listViewProjectItem.yearDuration
                    && this.totalCostAmount == listViewProjectItem.totalCostAmount;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        String s = projectId + "; "
                + name + "; "
                + "\n";
        return s;
    }
}
