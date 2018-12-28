package com.cheyrouse.gael.mynews.Controllers.Activities;


import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.Controllers.Fragments.ArticlesFragment;
import com.cheyrouse.gael.mynews.Models.Result;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.Views.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cheyrouse.gael.mynews.Models.Result.TOPSTORIES_EXTRA;

public class MainActivity extends AppCompatActivity implements ArticlesFragment.ArticlesFragmentListener, NavigationView.OnNavigationItemSelectedListener
{

    @BindView(R.id.activity_main_viewpager)
    public ViewPager pager;
    @BindView(R.id.activity_main_tabs)
    TabLayout tabs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<ArticlesFragment> listFragments;
    private ArticlesFragment articlesFragment;
    private String sectionNav;
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

        this.configureAndShowArticlesFragment();
    }

    private void configureToolbar() {
        // Set the Toolbar
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                return true;
            case R.id.menu_activity_main_params_Notification:
                // startNotificationActivity();
                return true;
            case R.id.menu_activity_main_params_help:
                return true;
            case R.id.menu_activity_main_params_about:
                return true;
            case R.id.menu_activity_main_search:
                Toast.makeText(this, " fragment recherche ", Toast.LENGTH_SHORT).show();
             /*   Intent SearchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(SearchActivityIntent);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void configureViewPagerAndTabs() {
        //Set Adapter PagerAdapter and glue it together
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), listFragments);
        pager.setAdapter(mPagerAdapter);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        item.getTitle();
        switch (id) {
            case R.id.activity_main_drawer_technology:
                Log.e("test", "technology is clicked");
                sectionNav = "technology";
                updateNavArticle(sectionNav);
                break;
            case R.id.activity_main_drawer_politics:
                sectionNav = "politics";
                updateNavArticle(sectionNav);
                break;
            case R.id.activity_main_drawer_travel:
                sectionNav = "travel";
                updateNavArticle(sectionNav);
                break;
            case R.id.activity_main_drawer_automobiles:
                sectionNav = "automobiles";
                updateNavArticle(sectionNav);
                break;
            case R.id.activity_main_drawer_arts:
                sectionNav = "arts";
                updateNavArticle(sectionNav);
                break;
            case R.id.activity_main_drawer_sports:
                sectionNav = "sports";
                updateNavArticle(sectionNav);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void updateNavArticle(String sectionNav) {
        pager.setCurrentItem(2);
        ((ArticlesFragment) mPagerAdapter.getItem(2)).updateContent(sectionNav);
    }


    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowArticlesFragment() {
        articlesFragment = (ArticlesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (articlesFragment == null) {
            articlesFragment = new ArticlesFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, articlesFragment)
                    .commit();
        }
    }


    private void startDetailActivity(Result article) {
        Intent detailActivityIntent = new Intent(MainActivity.this, ArticleDetailActivity.class);
        detailActivityIntent.putExtra(TOPSTORIES_EXTRA, article.getUrl());
        startActivity(detailActivityIntent);
    }


    @Override
    public void callbackArticle(Result article) {
        startDetailActivity(article);
    }

}
