package com.iflytek.xiri.scene;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

public class ProxyScene {

    private Context mContext;
    IntentFilter mIntentFilter;
    private IProxySceneListener mISceneListenner;
    public static final String ADDSCENECOMMAND_ACTION = "com.iflytek.xiri2.allActivity.QUERY";
    public static final String FUZZY_SCENE_SERVICE_ACTION = "com.iflytek.xiri2.scenes.EXECUTE";
    public static final String COMMIT_ACTION = "com.iflytek.xiri2.topActivity.COMMIT";
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
                                "else FocusScene exe "
                                        + Uri.decode(intent.toURI()));
                    }
                }
            }
        }
    };

    public ProxyScene(Context context) {
        mContext = context;
        token = this.hashCode();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(FUZZY_SCENE_SERVICE_ACTION);
        mIntentFilter.addAction(ADDSCENECOMMAND_ACTION);
    }

    public void init(IProxySceneListener listenner) {
        mContext.registerReceiver(mReceiver, mIntentFilter);
        mISceneListenner = listenner;
    }

    public void release() {
        mContext.unregisterReceiver(mReceiver);
    }
}
