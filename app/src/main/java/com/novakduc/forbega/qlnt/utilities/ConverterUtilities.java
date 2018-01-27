package com.novakduc.forbega.qlnt.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nguyen Quoc Thanh on 1/19/2018.
 */

public class ConverterUtilities {
    public static String calendarToString(long dateInMilis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMilis);
        Date date = calendar.getTime();
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }
}
