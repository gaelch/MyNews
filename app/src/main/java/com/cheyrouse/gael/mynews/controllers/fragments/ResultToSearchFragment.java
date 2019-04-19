package com.cheyrouse.gael.mynews.controllers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.models.Doc;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.cheyrouse.gael.mynews.views.RecyclerViewSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;
import static com.cheyrouse.gael.mynews.controllers.activities.SearchActivity.ARTICLES_SEARCH;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultToSearchFragment extends Fragment implements View.OnClickListener, RecyclerViewSearchAdapter.onArticleSearchAdapterListener {

    @BindView(R.id.fragment_search_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_search_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_empty) TextView textView;


    private List<Doc> responses;
    private SearchArticle searchArticle;
    private RecyclerViewSearchAdapter adapter;
    private ResultToSearchFragmentListener mListener;

    public ResultToSearchFragment() {
        // Required empty public constructor
    }

    //Attach the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ResultToSearchFragmentListener){
            //Listener to pass userLogin to th activityMain
            mListener = (ResultToSearchFragmentListener)context;
        }
        else{
            Log.d(TAG, "onAttach: parrent Activity must implement MainFragementListener");
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result_to_search, container, false);
        ButterKnife.bind(this, view);
        getTheBundle();

        configureRecyclerView();
        configureSwipeRefreshLayout();
        updateUI();

        return view;
    }

    //Get the bundle contains user params
    private void getTheBundle() {
        if(getArguments() != null){
            searchArticle = getArguments().getParcelable(ARTICLES_SEARCH);
        }
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    //Configure recyclerView and tabs
    private void configureRecyclerView(){
        this.responses = new ArrayList<Doc>();
        // Create adapter passing in the sample user data
        this.adapter = new RecyclerViewSearchAdapter(this.responses, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.recyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //Configure SSwipeRefreshLayout
    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUI();
            }
        });
    }

    //Update adapter
    private void updateUI() {
        if(responses != null){
            responses.clear();
        }
        if(searchArticle.getResponse().getDocs() != null) {
            responses.addAll(searchArticle.getResponse().getDocs());

            if(responses.size() == 0){
                textView.setVisibility(View.VISIBLE);
                textView.setText(R.string.list_empty);
            }
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.e("Test", "searchArticle.getResponse().getDocs() is null");
        }
    }

    @Override
    public void onClick(View v) { }

    //Interface to launch callback
    @Override
    public void onArticleClicked(Doc resultTopStories) {
        mListener.callbackSearchArticle(resultTopStories);
    }


    //Callback
    public interface ResultToSearchFragmentListener{
        void callbackSearchArticle(Doc searchArticle);
    }
}
