package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by n.thanh on 9/30/2016.
 */

@Entity(tableName = "room")
public class RoomForRent implements Cloneable, ItemWithId, Parcelable {
    public static final Parcelable.Creator<RoomForRent> CREATOR = new Parcelable.Creator<RoomForRent>() {
        @Override
        public RoomForRent createFromParcel(Parcel source) {
            return new RoomForRent(source);
        }

        @Override
        public RoomForRent[] newArray(int size) {
            return new RoomForRent[size];
        }
    };
    // TODO: 9/30/2016
    @PrimaryKey
    private long id;
    private String name;
    private double area;
    private long charge;
    private boolean available;
    @Ignore
    private ArrayList<RoomService> roomServices;

    //For Room only
    public RoomForRent(long id, String name, double area, long charge, boolean available) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.charge = charge;
        this.available = available;
    }

    @Ignore
    public RoomForRent(String name, double area, long charge) {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.name = name;
        this.area = area;
        this.charge = charge;
        available = true;
        roomServices = new ArrayList<RoomService>(5);
    }

    @Ignore //Ignore for room
    protected RoomForRent(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.area = in.readDouble();
        this.charge = in.readLong();
        this.available = in.readByte() != 0;
        this.roomServices = new ArrayList<RoomService>();
        in.readList(this.roomServices, RoomService.class.getClassLoader());
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getAmount() {
        return charge;
    }

    public ArrayList<RoomService> getRoomServices() {
        return roomServices;
    }

    public void setRoomServices(ArrayList<RoomService> roomServices) {
        this.roomServices = roomServices;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean mAvailable) {
        this.available = mAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public long getCharge() {
        return charge;
    }

    //Below is for Parcelable
    //////////////////////////////

    public void setCharge(long charge) {
        this.charge = charge;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO: 9/30/2016 room clone
        return super.clone();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.area);
        dest.writeLong(this.charge);
        dest.writeByte(this.available ? (byte) 1 : (byte) 0);
        dest.writeList(this.roomServices);
    }
}
