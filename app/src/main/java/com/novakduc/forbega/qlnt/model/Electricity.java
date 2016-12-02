package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class Electricity extends RoomService {

    private Electricity() {
        super();
        mUse = true;
    }

    @Override
    public long charge() {
        return 0;
    }
}
