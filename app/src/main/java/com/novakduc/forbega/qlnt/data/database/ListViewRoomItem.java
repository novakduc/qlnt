package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by n.thanh on 9/30/2016.
 */

public class ListViewRoomItem {
    private long id;
    private String name;
    private RoomStatus status;
    private long projectId;

    //For Room only
    public ListViewRoomItem(long id, String name, long charge, RoomStatus status, long projectId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.projectId = projectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ListViewRoomItem roomForRent = (ListViewRoomItem) super.clone();
        roomForRent.setId(-1);
        return roomForRent;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long newProjectId) {
        this.projectId = newProjectId;
    }
}
