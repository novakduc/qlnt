package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 9/20/2016.
 */
public class Qlnt {
    private static Qlnt ourInstance = new Qlnt();

    public static Qlnt getInstance() {
        return ourInstance;
    }

    private Qlnt() {
    }
}
