package com.cheyrouse.gael.mynews;

import android.support.test.rule.ActivityTestRule;

import com.cheyrouse.gael.mynews.controllers.activities.MainActivity;
import com.cheyrouse.gael.mynews.views.PagerAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

//Test the ViewPager
public class PagerAdapterTest {

    private MainActivity mActivity;
    private PagerAdapter pagerAdapter;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    public PagerAdapterTest() {
    }

    @Before
    public void setUp() throws Throwable {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    //Get item at position
    @Test
    public void runTestPager() throws Throwable {
        getItem(2);
    }

    //Number of pages
    @Test
    public void shouldGetCountReturn3() {
        assertEquals(3, Objects.requireNonNull(mActivity.pager.getAdapter()).getCount());
    }

    //Test if title is good
    @Test
    public void getGoodTitleToGoodFragment() {
        assertEquals("TOP STORIES", Objects.requireNonNull(mActivity.pager.getAdapter()).getPageTitle(0));
        assertEquals("MOST POPULAR", Objects.requireNonNull(mActivity.pager.getAdapter()).getPageTitle(1));
        assertEquals("BUSINESS", Objects.requireNonNull(mActivity.pager.getAdapter()).getPageTitle(2));
    }

    //Return the good item
    private void getItem(final int i) throws Throwable {
        mainActivityActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.pager.setCurrentItem(i);
            }
        });

        Thread.sleep(200);
        assertEquals(Objects.requireNonNull(mActivity.pager.getAdapter()).getPageTitle(i),
                mActivity.pager.getAdapter().getPageTitle(mActivity.pager.getCurrentItem()));
    }
}