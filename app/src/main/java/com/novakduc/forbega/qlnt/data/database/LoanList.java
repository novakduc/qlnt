package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;
import java.util.Collection;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    private ArrayList<Long> idList;

    //For Room only
    public LoanList(long projectId, String idListGSonString, long totalLoanAmount) {
        super(3);
        this.projectId = projectId;
        this.idListGSonString = idListGSonString;
        this.totalLoanAmount = totalLoanAmount;
        idList = gSonStringToList();
    }

    @Ignore
    private LoanList(long projectId) {
        super(3);
        this.projectId = projectId;
        idList = new ArrayList(3);
    }

    public static LoanList getInstance(long projectId) {
        return new LoanList(projectId);
    }

    @Override
    public String getIdListGSonString() {
        return this.idListGSonString;
    }

    @Override
    public ArrayList<Long> getIdList() {
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
                loan = i;
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
        e.setItemContainer(this);
        this.totalLoanAmount = super.getTotalAmount();
        return b;
    }

    @Override
    public boolean addAll(Collection<? extends Loan> c) {
        boolean b = super.addAll(c);
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

    @Override
    public String toString() {
        String s = "[";
        for (Loan l :
                this) {
            s = s + l.getId() + "/" + l.getProjectId() + ", ";
        }
        ;
        s = s + "]";
        return s;
    }
}
