package com.cheyrouse.gael.mynews.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;

public class AlarmReceiver extends BroadcastReceiver {

    static final String CHANEL_ID = "chanel_id";
    static final int NOTIFICATION_ID = 0;
    private Context mContext;

    //Receive notification and execute request to search user articles
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        executeRequestWithSearchParams();
    }

    //Request to search user articles
    public void executeRequestWithSearchParams(){
        Prefs prefs = Prefs.get(mContext);
        String keywords = prefs.getKeywords();
        List<String> categories = prefs.getCategories();
        Disposable disposable = NewYorkTimesStream.streamFetchArticleSearchNotification(API_KEY, keywords, categories).subscribeWith(new DisposableObserver<SearchArticle>() {
            @Override
            public void onNext(SearchArticle articles) { ShowNotification.showNotification(articles, mContext); }
            @Override
            public void onError(Throwable e) {
                Log.e("test", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("Test", "Search is charged");
            }
        });
    }
}
