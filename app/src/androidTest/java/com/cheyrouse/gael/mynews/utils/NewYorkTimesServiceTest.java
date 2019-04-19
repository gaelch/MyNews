package com.cheyrouse.gael.mynews.utils;

import com.cheyrouse.gael.mynews.controllers.fragments.ArticlesFragmentTest;
import com.cheyrouse.gael.mynews.models.Article;
import com.cheyrouse.gael.mynews.models.SearchArticle;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

//Request to mock server
public interface  NewYorkTimesServiceTest {

    @GET("/topStories_200_response.json")
    Observable<Article> getTopStoriesArticles();


    @GET("/mostPopular_200_response.json")
     Observable<Article> getMostPopularArticles();

    @GET("/searchArticle_200_response.json")
    Observable<SearchArticle> getSearch();

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

      Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ArticlesFragmentTest.server.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build();


}