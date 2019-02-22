package com.cheyrouse.gael.mynews.utils;

import com.cheyrouse.gael.mynews.models.Article;
import com.cheyrouse.gael.mynews.models.SearchArticle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//Stream run test with mockito
public class NewYorkTimesStreamTest {

    public static Observable<Article> streamFetchTopStories(){
        NewYorkTimesServiceTest newYorkTimesServiceTest = NewYorkTimesServiceTest.retrofit.create(NewYorkTimesServiceTest.class);
        return newYorkTimesServiceTest.getTopStoriesArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<Article> streamFetchMostPopular(){
        NewYorkTimesServiceTest newYorkTimesServiceTest = NewYorkTimesServiceTest.retrofit.create(NewYorkTimesServiceTest.class);
        return newYorkTimesServiceTest.getMostPopularArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<SearchArticle>  streamSearch(){
        NewYorkTimesServiceTest newYorkTimesServiceTest = NewYorkTimesServiceTest.retrofit.create(NewYorkTimesServiceTest.class);
        return newYorkTimesServiceTest.getSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

}