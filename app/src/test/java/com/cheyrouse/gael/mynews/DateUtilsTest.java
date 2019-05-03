package com.cheyrouse.gael.mynews;

import com.cheyrouse.gael.mynews.utils.DateUtils;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.cheyrouse.gael.mynews.utils.DateUtils.API_DATE;
import static com.cheyrouse.gael.mynews.utils.DateUtils.TEXT_DATE;
import static org.junit.Assert.*;

public class DateUtilsTest {

    //test class stringDateUtil
    @Test
    public void testDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(TEXT_DATE, Locale.getDefault());
        Assert.assertEquals(sdf.format(date), DateUtils.getDate(mYear, mDay, mMonth, TEXT_DATE));
        SimpleDateFormat sdformm = new SimpleDateFormat(API_DATE, Locale.getDefault());
        assertEquals(sdformm.format(date), DateUtils.getDate(mYear, mDay, mMonth, API_DATE));
    }
    @Test
    public void testDateString(){
        assertEquals("19700101", DateUtils.getDate(1970, 1, 0, API_DATE));
        assertEquals("01-01-1970", DateUtils.getDate(1970, 1, 0, TEXT_DATE));

    }

    @Test
    public void testCalendar(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        assertEquals(mDay, DateUtils.getDay());
        assertEquals(mMonth, DateUtils.getMonth());
        assertEquals(mYear, DateUtils.getYear());
    }
}