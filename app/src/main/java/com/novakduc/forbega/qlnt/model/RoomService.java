package com.novakduc.forbega.qlnt.model;

/**
 * Created by n.thanh on 12/2/2016.
 */

public abstract class RoomService extends DBObject {
    protected boolean mUse;

    public abstract long charge();
}
