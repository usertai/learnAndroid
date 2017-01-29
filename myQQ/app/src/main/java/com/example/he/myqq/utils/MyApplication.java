package com.example.he.myqq.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by he on 2017/1/29.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
