package com.novakduc.forbega.qlnt.ui.detail;

import android.arch.persistence.room.ColumnInfo;

public class ProjectNameQuery {
    @ColumnInfo(name = "name")
    private String name;

    //For room only
    public ProjectNameQuery(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
