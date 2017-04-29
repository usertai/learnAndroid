package com.iflytek.xiri;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class AnswerBroadcaseRecevier extends BroadcastReceiver {
    
    private static final String ASKACTION = "tv.yuyin.global.ASK";
    private static final String TAG = "AnswerBroadcaseRecevier";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "intent:" + intent.toURI());
        if (ASKACTION.equals(intent.getAction())) {
            Intent i = new Intent();
            i.setPackage(context.getPackageName());
            i.setAction(AppService.SELFACTION);
            i.putExtra("text", intent.getExtras().getString("text"));
            i.putExtra("_token", intent.getIntExtra("_token", -1));
            context.startService(i);
            Log.d(TAG, "intent2:" + Uri.decode(i.toURI()));
        }
        
    }

}
