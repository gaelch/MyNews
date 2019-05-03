package com.cheyrouse.gael.mynews.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String TEXT_DATE = "dd-MM-yyyy";
    public static final String API_DATE = "yyyyMMdd";

    public static String getDate(int year, int dayOfMonth, int monthOfYear, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, monthOfYear, dayOfMonth);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(date);
    }
    public static int getYear() {
        final Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getMonth() {
        final Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH);
    }

    public static int getDay() {
        final Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }
}
