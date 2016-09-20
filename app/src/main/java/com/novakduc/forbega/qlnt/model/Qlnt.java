package com.novakduc.forbega.qlnt.model;

import android.content.Context;

/**
 * Created by n.thanh on 9/20/2016.
 */
public class Qlnt {
    private static Qlnt ourInstance = new Qlnt();
    private static Context sContext;

    public static Qlnt getInstance(Context context) {
        sContext = context;
        return ourInstance;
    }

    private Qlnt() {
    }
}
