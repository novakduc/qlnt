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
public class LoanList<E> extends MyArrayList<E> {

    @PrimaryKey
    private long projectId;
    private String gSonIdString;
    @Ignore
    private ArrayList idListLong;

    public LoanList(long projectId, String gSonIdString) {
        super(3);
        this.projectId = projectId;
        this.gSonIdString = gSonIdString;
        idListLong = getIdListFromGson();
    }

    @Ignore
    public LoanList() {
        super(3);
        idListLong = new ArrayList(3);
    }

    @Override
    public String getGsonIdList() {
        return null;
    }

    @Override
    public ArrayList getIdListLong() {
        return null;
    }

    public long getTotalLoanAmount() {
        long total = 0;
        for (E i :
                this) {
            total += ((Loan) i).getAmount();
        }
        return total;
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
}
