package com.cheyrouse.gael.mynews.utils;

import android.app.Application;
import android.content.Context;

public class MyNews extends Application {

    private static MyNews mAppInstance=null;
    public static Context appContext;
    public static MyNews getInstance() {
        return mAppInstance;
    }
    public static MyNews get() {
        return get(appContext);
    }
    public static MyNews get(Context context) {
        return (MyNews) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance=this;
        appContext=getApplicationContext();

    }

}
