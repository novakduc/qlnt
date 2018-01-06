package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by n.thanh on 3/29/2017.
 */
@Entity(tableName = "loan_list", indices = @Index(value = {"projectId"}, unique = true))
public class LoanList<E> extends MyArrayList<E> implements Observer {

    @PrimaryKey
    private long projectId;
    private String idListGsonStringValue;
    private long totalLoanAmount;
    @Ignore
    private ArrayList idList;
    @Ignore
    public static final int DELETE = 0;
    @Ignore
    public static final int TOTAL_AMOUNT_CHANGE = 1;

    public LoanList(long projectId, String idListGsonStringValue, long totalLoanAmount) {
        super(3);
        this.projectId = projectId;
        this.idListGsonStringValue = idListGsonStringValue;
        this.totalLoanAmount = totalLoanAmount;
        idList = getIdListFromGson();
    }

    @Ignore
    public LoanList() {
        super(3);
        idList = new ArrayList(3);
    }

    @Override
    public String getIdListGsonStringValue() {
        return this.idListGsonStringValue;
    }

    @Override
    public ArrayList getIdList() {
        return this.idList;
    }

    private void updateTotalAmount() {
        long total = 0;
        for (E i :
                this) {
            total += ((Loan) i).getAmount();
        }

        this.totalLoanAmount = total;
    }

    public long getTotalLoanAmount() {

        return this.totalLoanAmount;
    }

    public double getTotalLoanAmount(CurrencyUnit unit) {
        double total = 0;
        for (E i :
                this) {
            if (i instanceof Loan) {
                total += ((Loan) i).getAmount(unit);
            }
        }
        return total;
    }

    public Loan getLoan(long loanId) {
        Loan loan;
        for (E i :
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
    public void update(Observable observable, Object o) {
        int arg = (int) o;
        if (arg == LoanList.TOTAL_AMOUNT_CHANGE) {
            updateTotalAmount();
        }
        if (arg == LoanList.DELETE) {
            E e = (E) observable;
            remove(e);
        }
    }

    @Override
    public boolean add(E e) {
        boolean b = super.add(e);
        updateTotalAmount();
        return b;
    }
}
