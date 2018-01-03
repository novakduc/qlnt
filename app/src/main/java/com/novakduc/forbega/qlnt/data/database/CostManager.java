package com.novakduc.forbega.qlnt.data.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by n.thanh on 11/28/2016.
 */

public class CostManager<E> extends ArrayList<E> implements Parcelable {
    // TODO: 11/28/2016

    public static final Parcelable.Creator<CostManager> CREATOR = new Parcelable.Creator<CostManager>() {
        @Override
        public CostManager createFromParcel(Parcel source) {
            return new CostManager(source);
        }

        @Override
        public CostManager[] newArray(int size) {
            return new CostManager[size];
        }
    };

    public CostManager() {
    }

    protected CostManager(Parcel in) {
    }

    public double getTotalCost(CurrencyUnit pUnit) {
        double total = 0;
        for (E i :
                this) {
            total += ((Cost) i).getAmount(pUnit);
        }
        return total;
    }

    public long getTotalCost() {
        long total = 0;
        Cost cost;
        for (E i :
                this) {
            total += ((Cost)i).getAmount();
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
