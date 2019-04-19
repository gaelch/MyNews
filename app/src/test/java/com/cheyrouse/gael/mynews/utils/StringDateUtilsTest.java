package com.cheyrouse.gael.mynews.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringDateUtilsTest {

    //test class stringDateUtil
    @Test
    public void testDate(){
        String month = "11";
        String year1 = "2012";
        String day1 = "12";
        String dateTest = day1 + month + year1;
        int day = 12;
        int year = 2012;
        assertEquals(dateTest, StringDateUtils.getDate(day, year, month));

    }

}