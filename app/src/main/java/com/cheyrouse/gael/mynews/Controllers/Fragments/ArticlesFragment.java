package com.cheyrouse.gael.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheyrouse.gael.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment {

    private static final String FRAGMENT_POSITION= "position";


    public ArticlesFragment() {
        // Required empty public constructor
    }

    public static ArticlesFragment newInstance(int position) {
        // Create new fragment
        ArticlesFragment frag = new ArticlesFragment();
        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_POSITION, position);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

}
