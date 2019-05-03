package com.cheyrouse.gael.mynews.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.models.SearchArticle;

import static com.cheyrouse.gael.mynews.utils.AlarmReceiver.CHANEL_ID;
import static com.cheyrouse.gael.mynews.utils.AlarmReceiver.NOTIFICATION_ID;

public class ShowNotification {

    //To display notification
    public static void showNotification(SearchArticle articles, Context mContext) {
        String textContent = NotificationUtils.getTextContent(articles);
        // 2 - Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Notification");
        inboxStyle.addLine(textContent);

        // 3 - Create a Channel (Android 8)
        String channelId = CHANEL_ID;

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        .setSmallIcon(R.mipmap.ic_my_news)
                        .setContentTitle("My News")
                        .setContentText(textContent)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setStyle(inboxStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message provenant de MyNews";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);


            // 7 - Show notification
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }
    }
}
