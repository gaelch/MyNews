package com.cheyrouse.gael.mynews.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.cheyrouse.gael.mynews.NotificationActivity.MY_PREFS;

public class AlarmHelper {

    static final String CHANNEL_ID = "chanel_id";
    static final int NOTIFICATION_ID = 0;

    //At midnight, the alarm goes off to save the mood of the day
    public void configureAlarmNotification(Context context) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        //in a current date at 1 pm, this property get an instance to calendar
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        //request sharedPreferences to check boolean switchNotif
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);

        boolean switchNotif = sharedPreferences.getBoolean("switch", false);
        //call AlarmReceiver class
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(switchNotif){
            Intent intent;
            intent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }

        //RTC-WAKEUP that will wake the device when it turns off.
        if (alarmManager != null && switchNotif) {
            Intent intent;
            intent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }

    }

}
