package com.novakduc.forbega.qlnt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomService extends DBObject implements Parcelable {
    protected CostType mType;
    protected UnitPrice mUnitPrice;

    protected RoomService(UnitPrice unitPrice, CostType type) {
        this.mUnitPrice = unitPrice;
        this.mType = type;
    }

    protected RoomService(Parcel in) {
        int tmpMType = in.readInt();
        this.mType = tmpMType == -1 ? null : CostType.values()[tmpMType];
        this.mUnitPrice = in.readParcelable(UnitPrice.class.getClassLoader());
        this.isChanged = in.readByte() != 0;
    }

    public CostType getType() {
        return mType;
    }

    public void setType(CostType type) {
        mType = type;
    }

    public void setUnitPrice(UnitPrice unitPrice) {
        mUnitPrice = unitPrice;
    }

    public long charge() {
        return mUnitPrice.get(mType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType == null ? -1 : this.mType.ordinal());
        dest.writeParcelable(this.mUnitPrice, flags);
        dest.writeByte(this.isChanged ? (byte) 1 : (byte) 0);
    }

}
