package com.novakduc.forbega.qlnt.data.database;

import java.util.ArrayList;

/**
 * Created by n.thanh on 11/28/2016.
 */

public class CostManager<E> extends ArrayList<E> {

    public double getTotalCost(CurrencyUnit pUnit) {
        double total = 0;
        for (E i :
                this) {
            total += ((Cost) i).getAmount(pUnit);
        }
        return total;
    }

    public long getTotalCost() {
        long total = 0;
        Cost cost;
        for (E i :
                this) {
            total += ((Cost)i).getAmount();
        }
        return total;
    }
}
