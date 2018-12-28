package com.cheyrouse.gael.mynews.Utils;

import com.cheyrouse.gael.mynews.Models.Article;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStream {

    public static Observable<Article> streamFetchArticle(String section){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.get().create(NewYorkTimesService.class);
        return newYorkTimesService.getBySection(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<Article> streamFetchArticleMostPopular(String section){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.get().create(NewYorkTimesService.class);
        return newYorkTimesService.getBySectionMp(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

  /*  public static Observable<SearchArticle> streamFetchArticleSearch(String search, String category, String beginDate, String endDate){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.get().create(NewYorkTimesService.class);
        return newYorkTimesService.getSearch(search,category,beginDate,endDate,"newest")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }*/
}
