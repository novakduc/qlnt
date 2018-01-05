package com.novakduc.forbega.qlnt.data.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Nguyen Quoc Thanh on 1/5/2018.
 */

public abstract class MyArrayList<E> extends ArrayList<E> implements ArrayListGsonExchange {
    private static Gson gson;

    public MyArrayList() {
        super();
        this.gson = new Gson();
    }

    public MyArrayList(int initialCapacity) {
        super(initialCapacity);
        this.gson = new Gson();
    }


    @Override
    public ArrayList getIdListFromGson() {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList>() {
        }.getType();
        return gson.fromJson(getGsonIdList(), collectionType);
    }

    @Override
    public String idListToString() {
        Gson gson = new Gson();
        return gson.toJson(getIdListLong());
    }

    public abstract String getGsonIdList();

    public abstract ArrayList getIdListLong();
}
