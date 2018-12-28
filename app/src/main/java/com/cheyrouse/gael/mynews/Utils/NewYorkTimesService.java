package com.cheyrouse.gael.mynews.Utils;

import com.cheyrouse.gael.mynews.Models.Article;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NewYorkTimesService {


    @GET("svc/topstories/v2/{section}.json?api_key=0adc2f31b1d340e9afa8a9804f49000a")
    Observable<Article> getBySection(@Path("section") String section);

    @GET("svc/mostpopular/v2/mostviewed/{section}/1.json?api_key=0adc2f31b1d340e9afa8a9804f49000a")
    Observable<Article> getBySectionMp(@Path("section") String section);

   /* @GET("svc/search/v2/articlesearch.json?api_key=0adc2f31b1d340e9afa8a9804f49000a")
    Observable<SearchArticle> getSearch(
            @Query("q") String section,
            @Query("fq")String category,
            @Query("begin_date")String beginDate,
            @Query("end_date")String endDate,
            @Query("sort") String sort
    );*/

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