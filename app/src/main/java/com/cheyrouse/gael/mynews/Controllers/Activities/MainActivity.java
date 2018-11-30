package com.cheyrouse.gael.mynews.Controllers.Activities;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cheyrouse.gael.mynews.Controllers.Fragments.ArticlesFragment;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.Views.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_viewpager) ViewPager pager;
    @BindView(R.id.activity_main_tabs) TabLayout tabs;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private List<ArticlesFragment> listFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listFragments = new ArrayList<>();

        listFragments.add(ArticlesFragment.newInstance(0));
        listFragments.add(ArticlesFragment.newInstance(1));
        listFragments.add(ArticlesFragment.newInstance(2));
        this.configureToolbar();
        this.configureViewPagerAndTabs();
    }


    private void configureToolbar() {
        // Set the Toolbar
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs() {
        //Set Adapter PagerAdapter and glue it together
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), listFragments));
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
