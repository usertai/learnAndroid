package com.iflytek.xiri.scene;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

/**
 * 只发给Activity
 * 
 * @author ScorpioNeal2013
 */
public class Scene {

    private Context mContext;
    IntentFilter mIntentFilter;
    private ISceneListener mISceneListenner;
    public static final String ADDSCENECOMMAND_ACTION = "com.iflytek.xiri2.topActivity.QUERY";
    public static final String FUZZY_SCENE_SERVICE_ACTION = "com.iflytek.xiri2.scenes.EXECUTE";
    public static final String ASK_SCENE_FILTER_ACTION = "com.iflytek.xiri2.scenes.ask.FILTER";
    public static final String COMMIT_ACTION = "com.iflytek.xiri2.topActivity.COMMIT";
    private static final String ACTION_FOCUSACTIVITYRESPONSE = "tv.yuyin.focusActivity.RESPONSE";
    public static final String NEW_COMMIT_ACTION = "tv.yuyin.topActivity.COMMIT";
    private int token;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ADDSCENECOMMAND_ACTION.equals(intent.getAction())) {
                Log.d("XiriScene",
                        "ADDSCENECOMMAND_ACTION onReceive "
                                + Uri.decode(intent.toURI()));
                String pkgname = intent.getStringExtra("pkgname") == null ? "com.iflytek.xiri"
                        : intent.getStringExtra("pkgname");
                if ("com.iflytek.xiri".equals(pkgname)) {
                    intent.setAction(COMMIT_ACTION);
                    intent.putExtra("_scene", mISceneListenner.onQuery()); // 场景的数据
                    intent.putExtra("_package", mContext.getPackageName());// 场景在的包
                    intent.putExtra("_objhash", token); // 场景的hashcode
                    intent.setPackage(pkgname);
                    mContext.startService(intent);
                } else {
                    intent.setAction(NEW_COMMIT_ACTION);
                    intent.putExtra("_scene", mISceneListenner.onQuery()); // 场景的数据
                    intent.putExtra("_package", mContext.getPackageName());// 场景在的包
                    intent.putExtra("_objhash", token); // 场景的hashcode
                    intent.setPackage(pkgname);
                    mContext.startService(intent);
                }
                Log.d("XiriScene",
                        "ADDSCENECOMMAND_ACTION startService "
                                + Uri.decode(intent.toURI()));
            } else if (FUZZY_SCENE_SERVICE_ACTION.equals(intent.getAction())) {
                Log.d("XiriScene",
                        "FUZZY_SCENE_SERVICE_ACTION "
                                + Uri.decode(intent.toURI()));
                Log.d("SCENE_TIME", "StartTime " + System.currentTimeMillis());
                Log.d("XiriScene",
                        "mContext getPackagename " + mContext.getPackageName());
                if (intent.hasExtra("_objhash")
                        && intent.getStringExtra("_objhash").equals(token + "")) {
                    if (intent.hasExtra("_scene")) {
                        String sceneId = intent.getStringExtra("_scene");
                        Log.d("SCENE_TIME", "fromIntent sceneId " + sceneId);
                        try {
                            JSONObject mJsonObject = new JSONObject(
                                    mISceneListenner.onQuery());
                            String userSceneId = mJsonObject
                                    .getString("_scene");

                            Log.d("SCENE_TIME", "userSceneId  " + sceneId);
                            if (userSceneId.equals(sceneId)) {
                                mISceneListenner.onExecute(intent);
                                Log.d("SCENE_TIME",
                                        "EndTime " + System.currentTimeMillis());
                                Log.d("XiriScene",
                                        "FUZZY_SCENE_SERVICE_ACTION exe "
                                                + Uri.decode(intent.toURI()));
                            }
                        } catch (Exception e) {
                        }

                    } else {
                        mISceneListenner.onExecute(intent);
                        Log.d("XiriScene",
                                "else Scene exe " + Uri.decode(intent.toURI()));
                    }
                }

            } else if (ASK_SCENE_FILTER_ACTION.equals(intent.getAction())){
                if (mISceneListenner != null) {
                    mISceneListenner.onTextFilter(intent);
                } 
            }
        }
    };
    
    public void finishTextFilter(Intent intent2, boolean filter){
        String pkgname = intent2.getStringExtra("pkgname") == null ? "com.iflytek.xiri"
                : intent2.getStringExtra("pkgname");
        intent2.setAction(ACTION_FOCUSACTIVITYRESPONSE);
        intent2.putExtra("_filter", filter); // 是否处理数据
        intent2.putExtra("_package", mContext.getPackageName());// 场景在的包
        intent2.putExtra("_objhash", token); // 场景的hashcode
        intent2.setPackage(pkgname);
        mContext.startService(intent2);
    }

    public Scene(Context context) {
        mContext = context;
        token = this.hashCode();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(FUZZY_SCENE_SERVICE_ACTION);
        mIntentFilter.addAction(ADDSCENECOMMAND_ACTION);
        mIntentFilter.addAction(ASK_SCENE_FILTER_ACTION);
    }

    public void init(ISceneListener listenner) {
        mContext.registerReceiver(mReceiver, mIntentFilter);
        mISceneListenner = listenner;
    }

    public void release() {
        mContext.unregisterReceiver(mReceiver);
    }
}
