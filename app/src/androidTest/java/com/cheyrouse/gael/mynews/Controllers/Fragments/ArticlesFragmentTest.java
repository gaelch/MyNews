package com.cheyrouse.gael.mynews.Controllers.Fragments;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.cheyrouse.gael.mynews.Models.Article;
import com.cheyrouse.gael.mynews.Models.Result;
import com.cheyrouse.gael.mynews.Utils.JsoonContent;
import com.cheyrouse.gael.mynews.Utils.NewYorkTimesStreamTest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ArticlesFragmentTest extends InstrumentationTestCase {

    @ClassRule
    public static MockWebServer server;

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @Before
    public void setUp() throws Exception{
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void fetchMostPopularTest() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(JsoonContent.jsonMostPopular));

        Observable<Article> observableArticles = NewYorkTimesStreamTest.streamFetchMostPopular();
        TestObserver<Article> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Result> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());

    }

    @Test
    public void fetchTopStoriesTest() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody( JsoonContent.jsonTopStories));

        Observable<Article> observableArticles = NewYorkTimesStreamTest.streamFetchTopStories();
        TestObserver<Article> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Result> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());
    }

   /* @Test
    public void fetchSearchTest() throws Exception {
        String fileName = "search_200_ok_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTest.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Observable<Result> observableResult = NyTimesStreamsTest.streamSearch();
        TestObserver<Result> testObserver = new TestObserver<>();

        observableResult.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        Articles articles = testObserver.values().get(0).getArticles();
        List<Article> articlesL = articles.getArticles();

        assertThat("The result list is not empty", !articlesL.isEmpty());
    }*/


}