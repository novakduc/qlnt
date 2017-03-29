package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomServiceWithIndex extends RoomService {
    public static final Creator<RoomServiceWithIndex> CREATOR = new Creator<RoomServiceWithIndex>() {
        @Override
        public RoomServiceWithIndex createFromParcel(Parcel source) {
            return new RoomServiceWithIndex(source);
        }

        @Override
        public RoomServiceWithIndex[] newArray(int size) {
            return new RoomServiceWithIndex[size];
        }
    };
    private long mOldIndex, mNewIndex;

    private RoomServiceWithIndex(UnitPrice unitPrice, CostType type) {
        super(unitPrice, type);
    }

    protected RoomServiceWithIndex(Parcel in) {
        super(in);
        this.mOldIndex = in.readLong();
        this.mNewIndex = in.readLong();
        int tmpMType = in.readInt();
        this.mType = tmpMType == -1 ? null : CostType.values()[tmpMType];
        this.mUnitPrice = in.readParcelable(UnitPrice.class.getClassLoader());
        this.isChanged = in.readByte() != 0;
    }

    public static RoomServiceWithIndex getInstance(UnitPrice room, CostType type) {
        return new RoomServiceWithIndex(room, type);
    }

    public long getOldIndex() {
        return mOldIndex;
    }

    public long getNewIndex() {
        return mNewIndex;
    }

    public void setNewIndex(long newIndex) {
        mOldIndex = mNewIndex;  //update old index
        mNewIndex = newIndex;
    }

    @Override
    public long charge() {
        long unitPrice = mUnitPrice.get(mType);
            return (mNewIndex - mOldIndex) * unitPrice;
    }

    //Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.mOldIndex);
        dest.writeLong(this.mNewIndex);
        dest.writeInt(this.mType == null ? -1 : this.mType.ordinal());
        dest.writeParcelable(this.mUnitPrice, flags);
        dest.writeByte(this.isChanged ? (byte) 1 : (byte) 0);
    }
}
