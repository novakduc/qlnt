package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

/**
 * Created by n.thanh on 12/2/2016.
 */
@Entity
public class RoomService {
    protected CostType type;
    @Ignore
    protected UnitPrice unitPrice;

    @Ignore
    protected RoomService(CostType type, UnitPrice unitPrice) {
        this.type = type;
        this.unitPrice = unitPrice;
    }

    protected RoomService(CostType type) {
        this.type = type;
    }

    public CostType getType() {
        return type;
    }

    private void setType(CostType type) {
        this.type = type;
    }

    public long charge() {
        return unitPrice.get(type);
    }
}
