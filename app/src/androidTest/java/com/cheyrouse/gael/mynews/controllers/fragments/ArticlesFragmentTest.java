package com.cheyrouse.gael.mynews.controllers.fragments;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.util.Log;
import com.cheyrouse.gael.mynews.models.Article;
import com.cheyrouse.gael.mynews.models.Doc;
import com.cheyrouse.gael.mynews.models.Result;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.cheyrouse.gael.mynews.utils.JsonContent;
import com.cheyrouse.gael.mynews.utils.NewYorkTimesStream;
import com.cheyrouse.gael.mynews.utils.NewYorkTimesStreamTest;
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

import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;
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
    public void TopStoriesAPIReturnArticles() throws InterruptedException {

        Observable<Article> observable = NewYorkTimesStream.streamFetchArticle("home", API_KEY);
        TestObserver<Article> testObserver = new TestObserver<>();

        observable.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        List<Result> articles = testObserver.values().get(0).getResult();
        assertThat("size != 0", articles.size() != 0);
    }

    //Test MostPopular
    @Test
    public void fetchMostPopularTest() throws InterruptedException {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(JsonContent.jsonMostPopular));

        Observable<Article> observableArticles = NewYorkTimesStreamTest.streamFetchMostPopular();
        TestObserver<Article> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        Thread.sleep(5000);
        List<Result> articles = testObserver.values().get(0).getResult();

        assertThat("The result list is not empty", !articles.isEmpty());

    }

    //Test TopStories
    @Test
    public void fetchTopStoriesTest()  {
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
    public void fetchSearchTest()  {
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