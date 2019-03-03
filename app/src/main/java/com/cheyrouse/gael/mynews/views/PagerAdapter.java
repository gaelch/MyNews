package com.cheyrouse.gael.mynews.views;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.cheyrouse.gael.mynews.Controllers.Fragments.ArticlesFragment;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private final List<ArticlesFragment> mListFragment;


    // Default Constructor
    public PagerAdapter(FragmentManager mgr, List<ArticlesFragment> listFragment) {
        super(mgr);
        mListFragment = listFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }


    // Depend the position we can switch the fragment placeholder
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: //Page number 1
                return mListFragment.get(position);
            case 1: //Page number 2
                return mListFragment.get(position);
            case 2: //Page number 3
                return mListFragment.get(position);
            default:
                return null;
        }
    }

    //Set the title on Tabs
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: //Page number 1
                return "TOP STORIES";
            case 1: //Page number 2
                return "MOST POPULAR";
            case 2: //Page number 3
                return "BUSINESS";
            default:
                return null;
        }
    }
}
