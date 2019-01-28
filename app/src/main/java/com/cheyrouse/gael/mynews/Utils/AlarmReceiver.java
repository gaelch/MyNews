package com.cheyrouse.gael.mynews.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.cheyrouse.gael.mynews.Models.SearchArticle;
import com.cheyrouse.gael.mynews.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.cheyrouse.gael.mynews.NotificationActivity.MY_PREFS;
import static com.cheyrouse.gael.mynews.Utils.NewYorkTimesService.API_KEY;

public class AlarmReceiver extends BroadcastReceiver {

    private String keywords;
    private List<String> categories;
    private Context context;
    private SharedPreferences sharedPreferences;
    static final String CHANEL_ID = "chanel_id";
    static final int NOTIFICATION_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        executeRequestWithSearchParams();
    }

    public void executeRequestWithSearchParams(){
        sharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        keywords = sharedPreferences.getString(MY_PREFS, "");
        String jsonFavorites = sharedPreferences.getString(MY_PREFS, null);
        Gson gson = new Gson();
        String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
        categories = Arrays.asList(favoriteItems);
        categories = new ArrayList<String>(categories);
        Log.e("test", String.valueOf(categories));
        Log.e("test", keywords);
        Disposable disposable = NewYorkTimesStream.streamFetchArticleSearchNotification(API_KEY, keywords, categories).subscribeWith(new DisposableObserver<List<SearchArticle>>() {
            @Override
            public void onNext(List<SearchArticle> articles) {
                showNotification(articles);
            }

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

    private void showNotification(List<SearchArticle> articles) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Builder mBuilder = new Builder(context, CHANEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_foreground_my_news)
                .setContentTitle("Notification")
                .setContentText("MyNews founds " + articles.size() + "articles today")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
