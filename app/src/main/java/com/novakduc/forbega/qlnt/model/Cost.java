package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by n.thanh on 11/25/2016.
 */

public class Cost implements Parcelable {
    public static final Parcelable.Creator<Cost> CREATOR = new Parcelable.Creator<Cost>() {
        @Override
        public Cost createFromParcel(Parcel source) {
            return new Cost(source);
        }

        @Override
        public Cost[] newArray(int size) {
            return new Cost[size];
        }
    };
    private long mAmount;
    private long mDate;
    private CostType mType;
    private boolean mRepeatable;

    public Cost(long amount, CostType type, long date, boolean repeatable) {
        this.mAmount = amount;
        this.mType = type;
        this.mDate = date;
        this.mRepeatable = repeatable;
    }

    protected Cost(Parcel in) {
        this.mAmount = in.readLong();
        this.mDate = in.readLong();
        int tmpMType = in.readInt();
        this.mType = tmpMType == -1 ? null : CostType.values()[tmpMType];
        this.mRepeatable = in.readByte() != 0;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public CostType getType() {
        return mType;
    }

    public void setType(CostType type) {
        mType = type;
    }

    public boolean isRepeatable() {
        return mRepeatable;
    }

    public void setRepeatable(boolean repeatable) {
        mRepeatable = repeatable;
    }

    public long getAmount() {
        return mAmount;
    }

    public void setAmount(long amount) {
        mAmount = amount;
    }

    //Parcel...
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mAmount);
        dest.writeLong(this.mDate);
        dest.writeInt(this.mType == null ? -1 : this.mType.ordinal());
        dest.writeByte(this.mRepeatable ? (byte) 1 : (byte) 0);
    }
}
