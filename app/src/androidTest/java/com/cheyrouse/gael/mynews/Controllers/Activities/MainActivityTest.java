package com.cheyrouse.gael.mynews.Controllers.Activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cheyrouse.gael.mynews.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testConfigurePagerAndTabs(){
        View viewPager = mActivity.findViewById(R.id.activity_main_viewpager);
        View tabs = mActivity.findViewById(R.id.activity_main_tabs);

        Assert.assertNotNull(viewPager);
        Assert.assertNotNull(tabs);
    }

  /*  @Test
    public void menuIsCreate(){
        Menu menu = mActivity.findViewById(R.menu.menu);
        mActivity.onCreateOptionsMenu(menu);
        Assert.assertNotNull(menu);
    }*/

    @Test
    public void toolbarIsOk(){
        Toolbar toolbar = mActivity.findViewById(R.id.toolbar);
        Assert.assertNotNull(toolbar);
    }

  /*  @Test
    public void GoodItemSelectedParamsSearch(){
        MenuItem itemParams = mActivity.findViewById(R.id.menu_activity_main_params);
        MenuItem itemSearch = mActivity.findViewById(R.id.menu_activity_main_search);
        Assert.assertTrue("Il n'y a rien à paramétrer ici, passez votre chemin...", mActivity.onOptionsItemSelected(itemParams));
        Assert.assertTrue("Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", mActivity.onOptionsItemSelected(itemSearch));
    }*/

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}