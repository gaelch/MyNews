package com.cheyrouse.gael.mynews.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Locale;

public class AlarmHelper {

    //Configuration of Alarm Helper too show notification
    public void configureAlarmNotification(Context context) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;
        //getCalendarPresets
        Calendar calendar = DateUtils.getCalendarPresets();
        //request sharedPreferences to check boolean switchNotif
        Prefs prefs = new Prefs(context);
        boolean switchNotif = prefs.getBoolean();

        //call AlarmReceiver class
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(switchNotif){
            Intent intent;
            intent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
