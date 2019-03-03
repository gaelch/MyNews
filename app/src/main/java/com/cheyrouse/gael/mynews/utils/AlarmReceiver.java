package com.cheyrouse.gael.mynews.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;
import static com.cheyrouse.gael.mynews.Controllers.activities.NotificationActivity.MY_PREFS;
import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;

public class AlarmReceiver extends BroadcastReceiver {

    static final String CHANEL_ID = "chanel_id";
    static final int NOTIFICATION_ID = 0;
    private Context mContext;

    //Receive notification and execute request to search user articles
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        executeRequestWithSearchParams();
    }

    //Request to search user articles
    public void executeRequestWithSearchParams(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String keywords = sharedPreferences.getString("keywords", "");
        String jsonFavorites = sharedPreferences.getString("categories", null);
        Gson gson = new Gson();
        String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
        List<String> categories = Arrays.asList(favoriteItems);
        categories = new ArrayList<String>(categories);
        Log.e("test", String.valueOf(categories));
        Log.e("test", keywords);
        Disposable disposable = NewYorkTimesStream.streamFetchArticleSearchNotification(API_KEY, keywords, categories).subscribeWith(new DisposableObserver<SearchArticle>() {
            @Override
            public void onNext(SearchArticle articles) {
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


    //To display notification
    private void showNotification(SearchArticle articles) {
        // 2 - Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Notification");
        inboxStyle.addLine("MyNews founds " + articles.getResponse().getDocs().size() + " articles today");

        // 3 - Create a Channel (Android 8)
        String channelId = CHANEL_ID;

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        .setSmallIcon(R.mipmap.ic_my_news)
                        .setContentTitle("My News")
                        .setContentText("MyNews founds " + articles.getResponse().getDocs().size() + " articles today")
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setStyle(inboxStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message provenant de Firebase";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);


            // 7 - Show notification
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }
    }
}
