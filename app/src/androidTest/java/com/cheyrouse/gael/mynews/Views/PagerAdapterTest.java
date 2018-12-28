package com.cheyrouse.gael.mynews.Views;

import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.FragmentManager;

import com.cheyrouse.gael.mynews.Controllers.Activities.MainActivity;
import com.cheyrouse.gael.mynews.Controllers.Fragments.ArticlesFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class PagerAdapterTest {

    FragmentManager mgr;
    List<ArticlesFragment> listFragment;
    PagerAdapter pagerAdapter;
    MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);



    public PagerAdapterTest() {
    }

    @Before
    public void setUp() throws Throwable {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void runTestPager() throws Throwable {
        getItem(2);
    }

    @Test
    public void shouldGetCountReturn3() {
        assertEquals(3, pagerAdapter.getCount());
    }

    @Test
    public void getGoodTitleToGoodFragment() {
        assertEquals("TOP STORIES", pagerAdapter.getPageTitle(0));
        assertEquals("MOST POPULAR", pagerAdapter.getPageTitle(1));
        assertEquals("BUSINESS", pagerAdapter.getPageTitle(2));
    }


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