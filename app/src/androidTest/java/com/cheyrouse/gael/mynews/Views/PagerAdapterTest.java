package com.cheyrouse.gael.mynews.Views;

import android.support.v4.app.FragmentManager;

import com.cheyrouse.gael.mynews.Controllers.Fragments.ArticlesFragment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PagerAdapterTest {

    FragmentManager mgr;
    List<ArticlesFragment> listFragment;
    PagerAdapter pagerAdapter;

    public PagerAdapterTest() {
    }

    @Before
    public void setUp() throws Exception {
        pagerAdapter = new PagerAdapter(mgr, listFragment);

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

    @Test
    public void getItem() {
       assert pagerAdapter.getItemPosition(0) == 0;
        assert pagerAdapter.getItemPosition(1) == 1;
        assert pagerAdapter.getItemPosition(2) == 2;
    }
}