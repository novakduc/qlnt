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

    @Override
    public String toString() {
        switch (getOrder()) {
            case 0:
                return "electricity";
            case 1:
                return "water";
            case 2:
                return "tv";
            case 3:
                return "internet";
            case 4:
                return "trash collection";
            case 5:
                return "security";
            case 6:
                return "maintenance";
            default:
                return "other";
        }
    }
}
