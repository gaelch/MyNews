package com.cheyrouse.gael.mynews.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class StringDateUtilsTest {

    //test class stringDateUtil
    @Test
    public void testDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        assertEquals(sdf.format(date), StringDateUtils.getDate(mYear, mDay, mMonth));
    }

    @Test
    public void testDateSearch(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        assertEquals(sdf.format(date), StringDateUtils.getDateForSearch(mYear, mDay, mMonth));
    }

}