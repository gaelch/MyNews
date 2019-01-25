package com.cheyrouse.gael.mynews.Utils;

import com.cheyrouse.gael.mynews.Models.Article;
import com.cheyrouse.gael.mynews.Models.SearchArticle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStream {

    public static Observable<Article> streamFetchArticle(String section, String api_key){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.get().create(NewYorkTimesService.class);
        return newYorkTimesService.getBySection(section, api_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<Article> streamFetchArticleMostPopular(String section, String api_key){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.get().create(NewYorkTimesService.class);
        return newYorkTimesService.getBySectionMp(section, api_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SearchArticle> streamFetchArticleSearch(String api_key, String search, String category, String beginDate, String endDate){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.get().create(NewYorkTimesService.class);
        return newYorkTimesService.getSearch(api_key, search, category, beginDate, endDate,"newest")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
