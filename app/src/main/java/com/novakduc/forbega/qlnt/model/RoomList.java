package com.novakduc.forbega.qlnt.model;

import java.util.ArrayList;

/**
 * Created by n.thanh on 9/30/2016.
 */

public class RoomList<R> extends ArrayList<R> implements Cloneable {
    private Long mProjectId;
    // TODO: 9/30/2016

    public RoomList(Long projectId) {
        mProjectId = projectId;
    }

    @Override

    public Object clone() {
        // TODO: 9/30/2016
        return super.clone();
    }
}
