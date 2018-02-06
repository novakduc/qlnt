package com.novakduc.forbega.qlnt.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nguyen Quoc Thanh on 1/19/2018.
 */

public class ConverterUtilities {
    private ConverterUtilities() {
    }

    public static String calendarToString(long dateInMilis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMilis);
        Date date = calendar.getTime();
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double currencyUnitConverter(long value, CurrencyUnit unit, int decimalPlace) {
        return round(value * 1.0 / unit.getUnit(), decimalPlace);
    }

    public static String currencyUnitConverterToString(long value, CurrencyUnit unit, int decimalPlace) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(Double.valueOf(currencyUnitConverter(value, unit, decimalPlace)));
    }
}
