package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by n.thanh on 3/29/2017.
 */

public class RoomList<E> extends ArrayList<E> implements Parcelable {
    // TODO: 3/29/2017

    public static final Parcelable.Creator<RoomList> CREATOR = new Parcelable.Creator<RoomList>() {
        @Override
        public RoomList createFromParcel(Parcel source) {
            return new RoomList(source);
        }

        @Override
        public RoomList[] newArray(int size) {
            return new RoomList[size];
        }
    };

    public RoomList() {
    }

    protected RoomList(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
