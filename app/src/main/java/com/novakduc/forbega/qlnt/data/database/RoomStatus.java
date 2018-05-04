package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 11/25/2016.
 */

public enum RoomStatus {
    NORMAL(0),
    WAITING_FOR_BILL(1),
    WAITING_FOR_PAYMENT(2),
    AVAILABLE(-1);

    private final int order;

    RoomStatus(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
