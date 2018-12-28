package com.cheyrouse.gael.mynews.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheyrouse.gael.mynews.Models.Article;
import com.cheyrouse.gael.mynews.Models.Result;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.Utils.NewYorkTimesStream;
import com.cheyrouse.gael.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.support.constraint.Constraints.TAG;

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
    private List<Result> ArticlesResults;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ArticlesFragmentListener){
            //Listener to pass userLogin to th activityMain
            mListener = (ArticlesFragmentListener)context;
        }
        else{
            Log.d(TAG, "onAttach: parrent Activity must implement MainFragementListener");
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);

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

    public void updateContent(String section){
        progressBar.setVisibility(View.VISIBLE);
        sectionNav = section;
        executeHttpRequestBusiness(section);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void configureRecyclerView(){
        this.ArticlesResults = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new RecyclerViewAdapter(this.ArticlesResults, Glide.with(this), this);
        // Attach the adapter to the recyclerView to populate items
        this.recyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

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

    private void executeHttpRequestTopStories( ){

        disposable = NewYorkTimesStream.streamFetchArticle("home").subscribeWith(new DisposableObserver<Article>() {
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


    private  void executeHttpRequestMostPopular (){

        disposable = NewYorkTimesStream.streamFetchArticleMostPopular("movies").subscribeWith(new DisposableObserver<Article>() {

            @Override
            public void onNext(Article articles) {
                updateUI(articles);
            }

            @Override
            public void onError(Throwable e) {
                textView.setVisibility(View.VISIBLE);
                Log.e("Test", "MostPopular is on error");
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                Log.e("Test", "MostPopular is charged");
            }
        });
    }

    private void executeHttpRequestBusiness(String sectionNav){

        disposable = NewYorkTimesStream.streamFetchArticle(sectionNav).subscribeWith(new DisposableObserver<Article>() {
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

    private void updateUI(Article articles){
        if(ArticlesResults != null){
            ArticlesResults.clear();
        }
        if(articles.getResult() != null)
        {
            ArticlesResults.addAll(articles.getResult());
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

    // 1 - Configure item click on RecyclerView
    @Override
    public void onArticleClicked(Result resultTopStories) {
        Toast.makeText(getContext(), "You clicked on newspaper : "+ resultTopStories.getTitle(), Toast.LENGTH_SHORT).show();
        Log.e("TAG", "Position : ");
        mListener.callbackArticle(resultTopStories);
    }

    public interface ArticlesFragmentListener{
        void callbackArticle(Result resultTopStories);
    }

}
