package com.cheyrouse.gael.mynews.utils;

import android.content.Context;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.controllers.activities.MainActivity;

import java.util.List;


public class CheckUtils {

    // to verify if checkbox are checked
    public static boolean getCheckBoxBoolean(List<String> categories, String s) {
        boolean ret = false;
        if (categories != null && categories.contains(s)) {
            ret = true;
        }
        return ret;
    }

    // check is fields are not empty to launch http request
    public static boolean toExecuteHttpRequest(String keywords, List<String> categories, Context context){
        boolean val = false;
        if (keywords == null) {
            Toast.makeText(context, "You must enter a keyword", Toast.LENGTH_LONG).show();
        } else if (keywords.isEmpty()) {
            Toast.makeText(context, "You must enter a keyword", Toast.LENGTH_LONG).show();
        } else {
            if (categories.size() == 0) {
                Toast.makeText(context, "You must choose a category", Toast.LENGTH_LONG).show();
            } else {
                val = true;
            }
        }
        return val;
    }

    // check is fields are not empty to save params in sharedPreferences
    public static boolean checkToSaveNotifications(String s, List<String> categories, Context context ){
        boolean val = false;
        if(s.isEmpty()) {
            Toast.makeText(context, "You must enter a keyword", Toast.LENGTH_LONG).show();
        }
        if (categories.size() == 0) {
            Toast.makeText(context, "You must choose a category", Toast.LENGTH_LONG).show();
        }
        if (!s.isEmpty() && categories.size() != 0){
            val = true;
        }
        return val;
    }

    // get prefs to know if switch is checked
    public static boolean getSwitchPrefs(Context context){
        boolean switchNotif = false;
        Prefs prefs = Prefs.get(context);
        if(prefs != null) {
            switchNotif = prefs.getBoolean();
        }
        return switchNotif;
    }

    public static boolean checkIfSwitchIsChecked(boolean val){
        boolean switchNotif = false;
        if(val){
            switchNotif = true;
        }
        return switchNotif;
    }
}
