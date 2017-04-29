package com.iflytek.xiri;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Feedback {
    public final static int EXECUTION = 1; // 执行
    public final static int SILENCE = 2; // 静默
    public final static int DIALOG = 3; // 聊天对话
    public final static int ERROR = 4;// 错误信息
    private int mTalkKey;
    private String mPackageName;
    private Context mContext;
    // 判断顶层activity是否为自动化测试工具
    public static boolean isXiriTool = false;

    public Feedback(Context context) {
        mContext = context;
    }

    public void begin(Intent intent) {
        Log.d("APP", "begin ");
        mTalkKey = intent.getIntExtra("_token", -1234567);
        mPackageName = intent.getStringExtra("pkgname") == null ? "com.iflytek.xiri"
                : intent.getStringExtra("pkgname");
    }

    public void feedback(String text, int type) {
        Log.d("Feedback", "in");
        if ("com.iflytek.xiri".equals(mPackageName)) {
            Intent intent = new Intent("com.iflytek.xiri2.app.CALL");
            intent.putExtra("_token", mTalkKey);
            intent.putExtra("text", text);
            intent.putExtra("type", type);
            intent.putExtra("_action", "FEEDBACK");
            intent.setPackage(mPackageName);
            mContext.startService(intent);
            Log.d("Feedback", "intent1:" + intent.toURI());
        } else {
            Intent intent = new Intent("tv.yuyin.app.CALL");
            intent.putExtra("_token", mTalkKey);
            intent.putExtra("text", text);
            intent.putExtra("type", type);
            intent.putExtra("_action", "FEEDBACK");
            intent.setPackage(mPackageName);
            mContext.startService(intent);
            Log.d("Feedback", "intent2:" + intent.toURI());
        }
        Log.d("Feedback", "out");
        // 自动化测试反馈
        if (isXiriTool) {
            Intent intent2 = new Intent("com.iflytek.xiri2.xiritool.CALL");
            intent2.putExtra("text", text);
            intent2.putExtra("type", type);
            mContext.startService(intent2);
        }
    }
}
