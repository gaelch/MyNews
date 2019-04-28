package com.cheyrouse.gael.mynews.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StringDateUtils {

    public static String getDate(int year, int dayOfMonth, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, monthOfYear, dayOfMonth);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(date);
    }
    public static String getDateForSearch(int year, int dayOfMonth, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, dayOfMonth);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return sdf.format(date);
    }
}
