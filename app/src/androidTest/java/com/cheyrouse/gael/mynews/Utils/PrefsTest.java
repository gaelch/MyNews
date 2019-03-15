package com.cheyrouse.gael.mynews.Utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PrefsTest {
    private Prefs prefs;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        prefs = Prefs.get(context);
    }

    @After
    public void tearDown() {
        prefs = null;
    }

    @Test
    public void assertStringIsStoredAndReturned(){
        String test = "testPreferences";
        prefs.storeKeywords(test);
        Assert.assertEquals(test, getTest());
    }

    @Test
    public void assertListIsStoredAndReturned(){
        List<String> testList = new ArrayList<>();
        testList.add("hello");
        testList.add("openClassrooms");
        prefs.storeCategories(testList);
        Assert.assertEquals(testList, getList());
    }

    @Test
    public void assertBooleanIsStoredAndReturned(){
        prefs.storeBoolean(false);
        Assert.assertEquals(false, getBoolean());
    }

    private String getTest(){
        return prefs.getKeywords();
    }

    private List<String> getList(){
        return prefs.getCategories();
    }

    private Boolean getBoolean(){
        return prefs.getBoolean();
    }

}