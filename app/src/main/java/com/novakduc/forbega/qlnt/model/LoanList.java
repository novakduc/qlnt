package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */

public class LoanList<E> extends ArrayList<E> implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
