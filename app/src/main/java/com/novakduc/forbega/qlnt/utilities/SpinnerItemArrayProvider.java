package com.novakduc.forbega.qlnt.utilities;

import java.util.ArrayList;

public class SpinnerItemArrayProvider {
    public static ArrayList<CharSequence> calendarDateArrayAdapter(int numberOfDate) {
        int size = 31;
        if (numberOfDate <= 31) {
            size = numberOfDate;
        }

        ArrayList<CharSequence> date = new ArrayList<CharSequence>(size);
        for (int i = 0; i < size; ) {
            date.add(String.valueOf(++i));
        }

        return date;
    }
}
