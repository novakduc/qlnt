package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class RoomService {
    protected CostType mType;
    protected UnitPrice mUnitPrice;

    protected RoomService(UnitPrice unitPrice, CostType type) {
        this.mUnitPrice = unitPrice;
        this.mType = type;
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
}
