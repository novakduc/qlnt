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
    private long revenue;
    private long dept;
    private long cost;
    private long startDate;
    private int yearDuration;

    //For Room only

    public Project(long projectId, String name, String address, long investmentAmount, long revenue,
                   long dept, long cost, long startDate, int yearDuration) {
        this.projectId = projectId;
        this.name = name;
        this.address = address;
        this.investmentAmount = investmentAmount;
        this.revenue = revenue;
        this.dept = dept;
        this.cost = cost;
        this.startDate = startDate;
        this.yearDuration = yearDuration;
    }

    // TODO: 02/08/2018 process income, revenue, dept and cost

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

    public long getEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(startDate);
        endDate.add(Calendar.YEAR, yearDuration);
        return endDate.getTimeInMillis();
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Project project = (Project) super.clone();
        project.setProjectId(-1);

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

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public long getDept() {
        return dept;
    }

    public void setDept(long dept) {
        this.dept = dept;
    }

    public long gerIncome() {
        return getRevenue() - getCost();
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    //Parcel

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
}
