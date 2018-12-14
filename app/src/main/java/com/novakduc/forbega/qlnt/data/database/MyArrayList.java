package com.novakduc.forbega.qlnt.data.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

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
    public ArrayList gSonStringToList() {
        Type collectionType = new TypeToken<ArrayList>() {
        }.getType();
        return gson.fromJson(getIdListGSonString(), collectionType);
    }

    @Override
    public String listToGSonString() {
        return gson.toJson(getIdList());
    }

    @Override
    public boolean add(E e) {
        boolean b = true;
        if (e instanceof ItemWithId) {
            ItemWithId itemWithId = (ItemWithId) e;
            b = getIdList().add(itemWithId.getId());
        }
        return b && super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean b = true;
        for (E e :
                c) {
            if (e instanceof ItemWithId) {
                ItemWithId itemWithId = (ItemWithId) e;
                b = b && getIdList().add(itemWithId.getId());
            }
        }
        return b && super.addAll(c);
    }

    public long getTotalAmount() {
        long total = 0;
        for (E i :
                this) {
            total += ((ItemWithId) i).getAmount();
        }

        return total;
    }

    public abstract String getIdListGSonString();

    public abstract ArrayList getIdList();
}
