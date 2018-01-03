package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 12/2/2016.
 */

public class DBObject {
    protected boolean isChanged = true;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}
