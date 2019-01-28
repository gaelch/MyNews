package com.cheyrouse.gael.mynews.Utils;

import com.cheyrouse.gael.mynews.Models.Article;
import com.cheyrouse.gael.mynews.Models.SearchArticle;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NewYorkTimesService {

    static final String API_KEY = "c30Fj2G1xFQkdMypW0aC6uEfGZhJh6Rn";


    @GET("svc/topstories/v2/{section}.json?")
    Observable<Article> getBySection(@Path("section") String section, @Query("api-key") String API_KEY);

    @GET("svc/mostpopular/v2/mostviewed/{section}/1.json?")
    Observable<Article> getBySectionMp(@Path("section") String section, @Query("api-key") String API_KEY);

    @GET("svc/search/v2/articlesearch.json?")
    Observable<SearchArticle> getSearch(@Query("api-key") String API_KEY,
            @Query("q") String search,
            @Query("fq")List<String> category,
            @Query("begin_date")String beginDate,
            @Query("end_date")String endDate,
            @Query("sort") String sort

    );

    @GET("svc/search/v2/articlesearch.json?")
    Observable<List<SearchArticle>> getSearchNotification(@Query("api-key") String API_KEY,
                                        @Query("q") String search,
                                        @Query("fq")List<String> category,
                                        @Query("sort") String sort

    );

    ThreadLocal<Retrofit> retrofit = new ThreadLocal<Retrofit>() {
        @Override
        protected Retrofit initialValue() {
            return new Retrofit.Builder()
                    .baseUrl("https://api.nytimes.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    };

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
}