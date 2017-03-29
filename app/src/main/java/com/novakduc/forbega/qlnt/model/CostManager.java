package com.novakduc.forbega.qlnt.model;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
