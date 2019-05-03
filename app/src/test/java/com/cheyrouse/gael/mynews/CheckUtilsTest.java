package com.cheyrouse.gael.mynews;

import com.cheyrouse.gael.mynews.utils.CheckUtils;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class CheckUtilsTest {

    //test if CheckBox return good value
    @Test
    public void testCheckBoxBoolean(){
        String s = "arts";
        String st = "business";
        List<String> test = new ArrayList<>();
        test.add(s);
        test.add(st);
        assertTrue(CheckUtils.getCheckBoxBoolean(test, s));
        assertTrue(CheckUtils.getCheckBoxBoolean(test, st));
    }

    // test if values to request api search are empty
    @Test
    public void testIfSearchValuesAreEmpty(){
        List<String> cat = new ArrayList<>();
        cat.add("News");
        assertTrue(CheckUtils.toExecuteHttpRequest("MyNews", cat));
    }

    // Test to see if values are not empty to save preset for notifications
    @Test
    public void testToSaveNotifications(){
        List<String> cat = new ArrayList<>();
        cat.add("News");
        assertTrue(CheckUtils.checkToSaveNotifications("MyNews", cat));
    }
}
