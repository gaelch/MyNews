package com.cheyrouse.gael.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.cheyrouse.gael.mynews.utils.Prefs;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class PrefsTest {
    private Prefs prefs;

    @Before
    public void setUp()  {
        Context context = InstrumentationRegistry.getTargetContext();
        prefs = Prefs.get(context);
    }

    @After
    public void tearDown() {
        prefs = null;
    }


    @Test
    public void assertListIsStoredAndReturned(){
        List<String> testList = new ArrayList<>();
        testList.add("hello");
        testList.add("openClassrooms");
        prefs.storeListTest(testList);
        Assert.assertEquals(testList, getList());
    }


    private List<String> getList(){
        return (List<String>) prefs.getTestList();
    }


}