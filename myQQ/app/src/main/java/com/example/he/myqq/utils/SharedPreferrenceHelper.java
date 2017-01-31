package com.example.he.myqq.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.he.myqq.R;

/**
 * Created by he on 2017/1/30.
 */

public class SharedPreferrenceHelper {


    public static void setTheme(Context context, int resId) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("theme", resId);
        editor.commit();
    }


    //获取保存的主题
    public static int getTheme(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int theme = sp.getInt("theme", R.style.AppTheme);
        return theme;
    }
}
