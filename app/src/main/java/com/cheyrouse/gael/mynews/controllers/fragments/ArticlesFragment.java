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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.models.Article;
import com.cheyrouse.gael.mynews.models.Result;
import com.cheyrouse.gael.mynews.utils.NewYorkTimesStream;
import com.cheyrouse.gael.mynews.views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.support.constraint.Constraints.TAG;
import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment implements RecyclerViewAdapter.onArticleAdapterListener{

    @BindView(R.id.fragment_main_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_bad_request) TextView textView;
    @BindView(R.id.fragment_progress_bar) ProgressBar progressBar;

    private static final String FRAGMENT_POSITION= "position";
    private ArticlesFragmentListener mListener;
    private List<Result> articlesResults;
    private RecyclerViewAdapter adapter;
    private int position;
    private Disposable disposable;
    private String sectionNav = "business";


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

    //Attach the callback tto activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ArticlesFragmentListener){
            //Listener to pass userLogin to th activityMain
            mListener = (ArticlesFragmentListener)context;
        }
        else{
            Log.d(TAG, "onAttach: parent Activity must implement MainFragmentListener");
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        articlesResults = new ArrayList<>();

        if (getArguments() != null) {
            position = getArguments().getInt(FRAGMENT_POSITION);
        }
        switch (position) {
            case 0:
                executeHttpRequestTopStories();
                break;
            case 1:
                executeHttpRequestMostPopular();
                break;
            case 2:
                executeHttpRequestBusiness(sectionNav);
                break;
        }

        configureRecyclerView();
        configureSwipeRefreshLayout();

        return view;
    }

    //Update sectionNav to request articles
    public void updateContent(String section){
        sectionNav = section;
        executeHttpRequestBusiness(sectionNav);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    //configure recyclerView and Tabs
    private void configureRecyclerView(){
        this.articlesResults = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new RecyclerViewAdapter(this.articlesResults, Glide.with(this), this);
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
                switch (position) {
                    case 0:
                          executeHttpRequestTopStories();
                        break;
                    case 1:
                          executeHttpRequestMostPopular();
                        break;
                    case 2:
                           executeHttpRequestBusiness(sectionNav);
                        break;
                }
            }
        });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    //Request to TopStories Api articles
    private void executeHttpRequestTopStories( ){
        disposable = NewYorkTimesStream.streamFetchArticle( "home", API_KEY).subscribeWith(new DisposableObserver<Article>() {
            @Override
            public void onNext(Article articles) {
                updateUI(articles);
            }

            @Override
            public void onError(Throwable e) {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                Log.e("Test", "TopStories is charged");
            }
        });
    }

    //Request to MostPopular Api articles
    private  void executeHttpRequestMostPopular (){
        disposable = NewYorkTimesStream.streamFetchArticleMostPopular("world", API_KEY).subscribeWith(new DisposableObserver<Article>() {

            @Override
            public void onNext(Article articles) {
                updateUI(articles);
            }
            @Override
            public void onError(Throwable e) {
                textView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.e("Test", "MostPopular is on error");
                Log.e("Test", e.getMessage());
            }
            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                Log.e("Test", "MostPopular is charged");
            }
        });
    }

    //Request to user section selected
    private void executeHttpRequestBusiness(String sectionNav){
        disposable = NewYorkTimesStream.streamFetchArticle(sectionNav, API_KEY).subscribeWith(new DisposableObserver<Article>() {
            @Override
            public void onNext(Article articles) {
                updateUI(articles);
            }

            @Override
            public void onError(Throwable e) {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                Log.e("Test", "TopStories, section Business is charged");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    //Update the adapter to recyclerView
    private void updateUI(Article articles){
        if(articlesResults != null){
            articlesResults.clear();
        }
        if(articles.getResult() != null)
        {
            articlesResults.addAll(articles.getResult());
            textView.setVisibility(View.GONE);
            if(articlesResults.size() == 0){
                {
                    articlesResults.clear();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(R.string.list_empty);}
            }
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.e("Test", "articles.getResult() is null");
        }
    }

    // -----------------
    // ACTION
    // -----------------

    //Configure item click on RecyclerView
    @Override
    public void onArticleClicked(Result resultTopStories) {
        Log.e("TAG", "Position : ");
        mListener.callbackArticle(resultTopStories);
    }

    //Callback to ArticleFragment
    public interface ArticlesFragmentListener{
        void callbackArticle(Result resultTopStories);
    }
}
