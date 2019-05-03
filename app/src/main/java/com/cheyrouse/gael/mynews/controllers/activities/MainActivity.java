package com.cheyrouse.gael.mynews.controllers.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cheyrouse.gael.mynews.controllers.fragments.ArticlesFragment;
import com.cheyrouse.gael.mynews.models.Result;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.utils.AlarmHelper;
import com.cheyrouse.gael.mynews.utils.IntentUtils;
import com.cheyrouse.gael.mynews.views.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

//home screen
public class MainActivity extends AppCompatActivity implements ArticlesFragment.ArticlesFragmentListener,NavigationView.OnNavigationItemSelectedListener
{

    @BindView(R.id.activity_main_viewpager) public ViewPager pager;
    @BindView(R.id.activity_main_tabs) TabLayout tabs;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private List<ArticlesFragment> listFragments;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureToolbar();
        listFragments = new ArrayList<>();

        listFragments.add(ArticlesFragment.newInstance(0));
        listFragments.add(ArticlesFragment.newInstance(1));
        listFragments.add(ArticlesFragment.newInstance(2));

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.configureViewPagerAndTabs();

        (new AlarmHelper()).configureAlarmNotification(this);
    }

    // To set he toolbar
    private void configureToolbar() {
        // Set the Toolbar
        setSupportActionBar(toolbar);
    }

    //inflate the menu and add it to the Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // To switch on different menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                return true;
            case R.id.menu_activity_main_params_Notification:
                IntentUtils.launchIntentSearch(this, false);
                return true;
            case R.id.menu_activity_main_params_help:
                IntentUtils.launchIntent(this, false);
                return true;
            case R.id.menu_activity_main_params_about:
                IntentUtils.launchIntent(this, true);
                return true;
            case R.id.menu_activity_main_search:
                IntentUtils.launchIntentSearch(this, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // configuration ViewPager and Tabs
    private void configureViewPagerAndTabs() {
        //Set Adapter PagerAdapter and glue it together
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), listFragments);
        pager.setAdapter(mPagerAdapter);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }


    //To manage drawerLayout on back button
    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Configuration NavigationView of Drawer Menu
    private void configureNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Configure Drawer layout
    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Switch to menu Drawer items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        item.getTitle();
        switch (id) {
            case R.id.activity_main_drawer_technology:
                updateNavArticle("technology");
                break;
            case R.id.activity_main_drawer_sports:
                updateNavArticle("sports");
                break;
            case R.id.activity_main_drawer_politics:
                updateNavArticle("politics");
                break;
            case R.id.activity_main_drawer_travel:
                updateNavArticle("travel");
                break;
            case R.id.activity_main_drawer_automobiles:
                updateNavArticle("automobiles");
                break;
            case R.id.activity_main_drawer_arts:
                updateNavArticle("arts");
                break;
            case R.id.activity_main_drawer_Business:
                updateNavArticle("business");
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // To update ArticleFragment at position 2 with selected section
    private void updateNavArticle(String sectionNav) {
        pager.setCurrentItem(2);
        Objects.requireNonNull(tabs.getTabAt(2)).setText(sectionNav);
        ((ArticlesFragment) mPagerAdapter.getItem(2)).updateContent(sectionNav);
    }


    // -------------------
    // CONFIGURATION
    // -------------------

    //Callback of ArticleFragment
    @Override
    public void callbackArticle(Result article) {
        IntentUtils.startDetailActivity(this, article.getUrl());
    }
}
