package com.novakduc.forbega.qlnt.data.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */

public class LoanList<E> extends ArrayList<E> implements Parcelable, Cloneable {
    // TODO: 3/29/2017

    public static final Parcelable.Creator<LoanList> CREATOR = new Parcelable.Creator<LoanList>() {
        @Override
        public LoanList createFromParcel(Parcel source) {
            return new LoanList(source);
        }

        @Override
        public LoanList[] newArray(int size) {
            return new LoanList[size];
        }
    };

    public LoanList() {
        super(3);
    }

    protected LoanList(Parcel in) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
