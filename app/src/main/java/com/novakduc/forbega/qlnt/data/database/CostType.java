package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 11/25/2016.
 */

public enum CostType {
    ELECTRICITY(0),
    WATER(1),
    TV_CABLE(2),
    INTERNET(3),
    TRASH_COLLECTION(4),
    SECURITY(5),
    MAINTENANCE(6),
    OTHERS(7);

    private final int order;

    CostType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
