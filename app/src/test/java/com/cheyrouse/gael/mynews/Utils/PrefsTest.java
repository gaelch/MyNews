package com.cheyrouse.gael.mynews.Utils;


import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import java.util.ArrayList;
import java.util.List;

//@RunWith(RobolectricTestRunner.class)
public class PrefsTest  {
   /* private Prefs prefs;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Before
    public void setUp() throws Exception{
        prefs = Prefs.PrefsInit(mMockSharedPreferences);
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
        Boolean testFalse = false;
        prefs.storeBoolean(false);
        Assert.assertEquals(false, getBoolean());
    }*/


}
