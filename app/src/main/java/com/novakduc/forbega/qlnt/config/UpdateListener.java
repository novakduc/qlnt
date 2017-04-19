package com.novakduc.forbega.qlnt.config;

/**
 * Created by n.thanh on 4/18/2017.
 */

public interface UpdateListener {
    public abstract void discardConfirmation();

    public abstract void updateBase(String name, String address, int duration, long startDate);
}
