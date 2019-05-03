package com.cheyrouse.gael.mynews.utils;

import android.content.Context;
import android.widget.Toast;
import java.util.List;


public class CheckUtils {


    public static boolean getCheckBoxBoolean(List<String> categories, String s) {
        boolean ret = false;
        if (categories != null && categories.contains(s)) {
            ret = true;
        }
        return ret;
    }

    public static boolean toExecuteHttpRequest(String keywords, List<String> categories){
        Context context = MyNews.appContext;
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

    public static boolean checkToSaveNotifications(String s, List<String> categories){
        Context context = MyNews.appContext;
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
}
