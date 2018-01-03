package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */

public class LoanList<E> extends ArrayList<E> {
    // TODO: 3/29/2017

    public LoanList() {
        super(3);
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
