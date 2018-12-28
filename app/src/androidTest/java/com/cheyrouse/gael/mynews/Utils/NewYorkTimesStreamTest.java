package com.cheyrouse.gael.mynews.Utils;

import com.cheyrouse.gael.mynews.Models.Article;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

   /* public static Observable<SearchArticle> streamSearch(){
        NyTimesServiceTest nyTimesServiceTest = NyTimesServiceTest.retrofit.create(NyTimesServiceTest.class);
        return nyTimesServiceTest.getSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }*/

}