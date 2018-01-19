package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by Nguyen Quoc Thanh on 1/19/2018.
 */

public interface ItemContainer<T> {
    void update();

    void removeItem(T item);
}
