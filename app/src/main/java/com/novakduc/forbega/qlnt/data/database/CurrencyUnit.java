package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by Nguyen Quoc Thanh on 12/21/2017.
 */

public enum CurrencyUnit {
    BASE(1),
    K_BASE(1000),
    MIL_BASE(1000000),
    BIL_BASE(1000000000);

    private int unit;
    CurrencyUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return unit;
    }
}