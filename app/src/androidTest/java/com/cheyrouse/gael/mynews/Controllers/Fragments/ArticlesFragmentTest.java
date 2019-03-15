package com.cheyrouse.gael.mynews.Controllers.Fragments;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.cheyrouse.gael.mynews.Models.Article;
import com.cheyrouse.gael.mynews.Models.Doc;
import com.cheyrouse.gael.mynews.Models.Result;
import com.cheyrouse.gael.mynews.Models.SearchArticle;
import com.cheyrouse.gael.mynews.Utils.JsonContent;
import com.cheyrouse.gael.mynews.Utils.NewYorkTimesStream;
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

import static com.cheyrouse.gael.mynews.Utils.NewYorkTimesService.API_KEY;
import static org.hamcrest.MatcherAssert.assertThat;

//Test Requests with Mockito and API request
@RunWith(AndroidJUnit4.class)
public class ArticlesFragmentTest extends InstrumentationTestCase {

    //Mockito mock the server
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
    public void TopStoriesAPIReturnArticles()
    {
       /* Observable<Article> observable = NewYorkTimesStream.streamFetchArticle("home", API_KEY);
        TestObserver<Article> testObserver = new TestObserver<>();
        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();
        Article article = testObserver.values().get(0);
        assertThat("size != 0", article.getResult().size() != 0);*/
    }

    //Test MostPopular
    @Test
    public void fetchMostPopularTest() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(JsonContent.jsonMostPopular));

        Observable<Article> observableArticles = NewYorkTimesStreamTest.streamFetchMostPopular();
        TestObserver<Article> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Result> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());

    }

    //Test TopStories
    @Test
    public void fetchTopStoriesTest() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody( JsonContent.jsonTopStories));

        Observable<Article> observableArticles = NewYorkTimesStreamTest.streamFetchTopStoriesTest();
        TestObserver<Article> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Result> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());
    }

    @Test
    public void fetchSearchTest() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(JsonContent.jsonSearch));

        Observable<SearchArticle> observableResult = NewYorkTimesStreamTest.streamSearch();
        TestObserver<SearchArticle> testObserver = new TestObserver<>();

        observableResult.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Doc> docs = testObserver.values().get(0).getResponse().getDocs();
        Log.e("test", "test");
        assertThat("The result list is not empty", !docs.isEmpty());
    }


}