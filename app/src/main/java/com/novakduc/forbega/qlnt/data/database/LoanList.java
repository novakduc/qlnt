package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */
@Entity(tableName = "loan_list", indices = @Index(value = {"projectId"}, unique = true))
public class LoanList extends MyArrayList<Loan> implements ItemContainer<Loan> {

    @Ignore
    public static final int DELETE = 0;
    @Ignore
    public static final int TOTAL_AMOUNT_CHANGE = 1;
    @PrimaryKey
    private long projectId;
    private String idListGSonString;
    private long totalLoanAmount;
    @Ignore
    private ArrayList idList;

    //For Room only
    public LoanList(long projectId, String idListGSonString, long totalLoanAmount) {
        super(3);
        this.projectId = projectId;
        this.idListGSonString = idListGSonString;
        this.totalLoanAmount = totalLoanAmount;
        idList = gSonStringToList();
    }

    @Ignore
    public LoanList(long projectId) {
        super(3);
        this.projectId = projectId;
        idList = new ArrayList(3);
    }

    @Override
    public String getIdListGSonString() {
        return this.idListGSonString;
    }

    @Override
    public ArrayList getIdList() {
        return this.idList;
    }

    public long getTotalLoanAmount() {

        return this.totalLoanAmount;
    }

    public long getProjectId() {
        return projectId;
    }

    public Loan getLoan(long loanId) {
        Loan loan;
        for (Loan i :
                this) {
            if (i instanceof Loan) {
                loan = (Loan) i;
                if (loan.getId() == loanId) {
                    return loan;
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(Loan e) {
        boolean b = super.add(e);
        ((Loan) e).setItemContainer((ItemContainer<Loan>) this);
        this.totalLoanAmount = super.getTotalAmount();
        return b;
    }

    @Override
    public long getTotalAmount() {
        this.totalLoanAmount = super.getTotalAmount();
        return this.totalLoanAmount;
    }

    @Override
    public void update() {
        this.getTotalAmount();
    }

    @Override
    public void removeItem(Loan e) {
        remove(e);
        this.totalLoanAmount = super.getTotalAmount();
    }
}
