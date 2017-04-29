package com.iflytek.xiri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONObject;

import com.iflytek.xiri.video.channel.ChannelItem;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public abstract class AppService extends Service {
    public static final String TAG = "IAppService";
    private static final String mSDKVer = "2";
    private boolean mToastTip = false;
    public static final int SOURCE_UNKNOWN = 0;
    public static final int SOURCE_PHONE = 1;
    public static final int SOURCE_REMOTE = 2;
    public static final String NOTIFIYACTION = "com.iflytek.xiri2.app.NOTIFY";
    public static final String CALLACTION = "com.iflytek.xiri2.app.CALL";
    public static final String NEWCALLACTION = "tv.yuyin.app.CALL";
    public static final String RAWTEXTANWSERACTION = "tv.yuyin.app.global.RESPONSE";
    public static final String SELFACTION = "tv.yuyin.self";
    public static String ACTION_GLOBALOPERATE = "tv.yuyin.global.ASK.operate";
    public static IAppListener mAppListener;
    public static ILocalAppListener mLocalAppListener;
    public static ITVLiveListener mTVListener;
    public static ITVBackListener mTVBackListener;
    public static IVideoIntentListener mVideoIntentListener;
    public static IVideoSearchListener mVideoSearchListener;
    public static IVideoItemListener mVideoDetailListener;
    public static IAppStoreListener mAppStoreListener;
    private static Context mContext;
    private static ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            Log.d("IAppService", "ServiceConnection onServiceConnected");
        }

        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("IAppService", "ServiceConnection onServiceDisconnected");
            Intent intent = new Intent("tv.yuyin.START");
            intent.setPackage("tv.yuyin");
            mContext.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    };

    public interface ILocalAppListener {
        void onExecute(Intent intent);
    }

    public interface IAppListener {
        void onTextFilter(Intent intent);
        void onExecute(Intent intent);
    }

    public interface IAppStoreListener {
        void onExecute(String name);
    }

    public interface ITVLiveListener {
        void onOpen(int source);

        void onChangeChannel(int channelNum, int source);

        void onChangeChannel(String channelName, String windowid, int source);

        void onNextChannel(int source);

        void onPrevChannel(int source);
    }

    public interface ITVBackListener {
        /**
         * @param channelName
         */
        void onChangeChannel(String channelName, Date starttime, Date endtime,
                String windowid);

        void onProgram(String channelName, Date starttime, Date endtime,
                String windowid);

        void onOpen();

        void onNextChannel();

        void onPrevChannel();
    }

    public interface IVideoIntentListener {
        void onExecute(Intent intent);
    }

    public interface IVideoSearchListener {
        void onSearch(String urlbase, String prompt);
    }

    public interface IVideoItemListener {
        void onShow(String id);
    }

    // 通知初始化
    protected abstract void onInit();

    public static void setAppListener(IAppListener lsn) {
        mAppListener = lsn;
    }

    public static void setLocalAppListener(ILocalAppListener lsn) {
        mLocalAppListener = lsn;
    }

    public static void setTVLiveListener(ITVLiveListener lsn) {
        mTVListener = lsn;
    }

    public static void setTVBackListener(ITVBackListener lsn) {
        mTVBackListener = lsn;
    }

    public static void setVideoIntentListener(IVideoIntentListener lsn) {
        mVideoIntentListener = lsn;
    }

    public static void setVideoSearchListener(IVideoSearchListener lsn) {
        mVideoSearchListener = lsn;
    }

    public static void setVideoItemListener(IVideoItemListener lsn) {
        mVideoDetailListener = lsn;

    }

    public static void setAppStoreListener(IAppStoreListener lsn) {
        mAppStoreListener = lsn;
    }

    // 直播
    /**
     * 描述： 通过应用名提交直播应用的频道列表。
     * 
     * @param ctx
     * @param appName
     *            播放设备的名称,例如: DTV, 机顶盒… 仅管理设为null
     * @param channelList
     *            频道列表，NULL则删除该频道列表
     */
    public static void updateTVChannel(Context ctx,
            ArrayList<ChannelItem> channelList) {
        Log.d(TAG, "updateTVChannel");
        Intent in = new Intent(CALLACTION);
        in.putExtra("_action", "UPLOAD");
        in.putExtra("_type", "tv_live");
        in.putExtra("channellist", (Serializable) channelList);
        in.putExtra("packagename", ctx.getPackageName());
        in.setPackage("com.iflytek.xiri");
        ctx.startService(in);
        Intent in2 = new Intent(NEWCALLACTION);
        in2.putExtra("_action", "UPLOAD");
        in2.putExtra("_type", "tv_live");
        in2.putExtra("channellist", (Serializable) channelList);
        in2.putExtra("packagename", ctx.getPackageName());
        in2.setPackage("tv.yuyin");
        ctx.startService(in2);
    }
    
    public static void updateContact(Context ctx,
            ArrayList<String> contactList) {
        Log.d(TAG, "updateContact");
        Intent in = new Intent(CALLACTION);
        in.putExtra("_action", "UPLOAD");
        in.putExtra("_type", "contact");
        in.putExtra("contactList", contactList);
        in.putExtra("packagename", ctx.getPackageName());
        in.setPackage("com.iflytek.xiri");
        ctx.startService(in);
        Intent in2 = new Intent(NEWCALLACTION);
        in2.putExtra("_action", "UPLOAD");
        in2.putExtra("_type", "contact");
        in2.putExtra("contactList", contactList);
        in2.putExtra("packagename", ctx.getPackageName());
        in2.setPackage("tv.yuyin");
        ctx.startService(in2);
    }

    // 全局命令词
    /**
     * 描述： 通过应用提交全局命令词。
     * 
     * @param ctx
     * 
     * @param globalStr
     *            Json格式的全局命令词
     */
    public static void updateGlobal(Context ctx, String globalStr) {
        Log.d(TAG, "updateGlobal");
//        Intent in = new Intent(CALLACTION);   
        Intent in=new Intent(NEWCALLACTION);
        in.putExtra("_action", "UPLOAD");
        in.putExtra("_type", "global");
        in.putExtra("global", globalStr);
        in.putExtra("packagename", ctx.getPackageName());
     //   in.setPackage("com.iflytek.xiri");
     in.setPackage("tv.yuyin");
        ctx.startService(in);
    }

    /**
     * 描述： 设置当前电视直播应用的直播状态及正在直播的频道名称。
     * 
     * @param ctx
     * @param appName
     * @param live
     *            boolean 是否在直播状态
     * @param channelName
     *            正在直播的频道名称
     */
    public static void notifyTVLiveStatus(Context ctx, String channelName,
            Boolean live) {
        Log.d(TAG, "notifyLiveStatus" + " channelName=" + channelName);
        Intent in = new Intent(CALLACTION);
        in.putExtra("_action", "REPORTSTATUS");
        in.putExtra("_type", "tv_live");
        in.putExtra("channelname", channelName);
        in.putExtra("packagename", ctx.getPackageName());
        in.putExtra("tvlive", live);
        in.setPackage("com.iflytek.xiri");
        ctx.startService(in);
        Intent in2 = new Intent(NEWCALLACTION);
        in2.putExtra("_action", "REPORTSTATUS");
        in2.putExtra("_type", "tv_live");
        in2.putExtra("channelname", channelName);
        in2.putExtra("packagename", ctx.getPackageName());
        in2.putExtra("tvlive", live);
        in2.setPackage("tv.yuyin");
        ctx.startService(in2);
    }

    public static void notifyTVBackStatus(Context ctx, String channelName,
            Boolean back) {
        Log.d(TAG, "notifyLiveStatus" + " channelName=" + channelName);
        Intent in = new Intent(CALLACTION);
        in.putExtra("_action", "REPORTSTATUS");
        in.putExtra("_type", "tv_back");
        in.putExtra("channelname", channelName);
        in.putExtra("packagename", ctx.getPackageName());
        in.putExtra("tvback", back);
        in.setPackage("com.iflytek.xiri");
        ctx.startService(in);
        Intent in2 = new Intent(NEWCALLACTION);
        in2.putExtra("_action", "REPORTSTATUS");
        in2.putExtra("_type", "tv_back");
        in2.putExtra("channelname", channelName);
        in2.putExtra("packagename", ctx.getPackageName());
        in2.putExtra("tvback", back);
        in2.setPackage("tv.yuyin");
        ctx.startService(in2);
    }
    
    public static void finishTextFilter(Intent in, boolean filter){
        in.putExtra("_pkg", mContext.getPackageName());
        in.putExtra("_filter", filter);
        in.setAction(RAWTEXTANWSERACTION);
        in.setPackage("tv.yuyin");
        mContext.startService(in);
        in.setPackage("com.iflytek.xiri");
        mContext.startService(in);
    }

    // /**
    // * 描述： 用户设置当前激活的的电视直播应用。
    // *
    // * @param appName 需要设置为激活的电视直播应用名称
    // */
    // private static void requestActive(Context ctx)
    // {
    // Intent in = new Intent(CALLACTION);
    // in.putExtra("_action", "REQUESTACTIVE");
    // ctx.startService(in);
    // Log.d(TAG, "set Active App " + in.toURI());
    // }
    //
    // private static void unActive(Context ctx)
    // {
    // Intent in = new Intent(CALLACTION);
    // in.putExtra("_action", "UNACTIVE");
    // ctx.startService(in);
    // Log.d(TAG, "set Active App null");
    // }
    private String getOriginalString(String string) {
        String KEY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqwyDiDx5fFiRQkwTa1I/oK3UI9TJmDH1o9wbkFsZNgTRl7uGMTKoa0D39juYnJ85ouCH99Jyced3JFLGZG/w8J8EKDKbhPPqWgVVxI9H4WkYDoRavGU8rh5zKoV4sEzuS7TgjDG3JQzGM4U7RnQAbbn4QtGrHmaR0Xuf1R1OhzQIDAQAB";
        String content = "";
        try {
            if (string.length() > 172) {
                String source = string.substring(0, string.length() - 172);
                String sign = string.substring(string.length() - 172);
                MessageDigest md = MessageDigest.getInstance("MD5", "BC");
                byte[] md5 = md.digest(source.getBytes("utf-8"));
                if (RSAUtils.verify(md5, KEY_PUBLIC, sign)) {
                    content = source;
                }
            }
        } catch (IOException e) {
            // close_print_exception
        } catch (Exception e) {
            // close_print_exception
        }
        return content;
    }
    
//    BroadcastReceiver mReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (mAppListener != null) {
//                mAppListener.onTextFilter(intent);
//            }
//            Intent sintent = new Intent(SELFACTION);
//            Log.d(TAG, "i hava received the broadcast");
//            intent.setPackage(context.getPackageName());
//            intent.setAction(SELFACTION);
//            context.startService(intent);
//            Log.d(TAG, "intent:" + intent.toURI());
//        }
//    };

    @Override
    public final IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("IAppService", "onCreate");
        mContext = this;
        Intent in = new Intent("tv.yuyin.START");
        in.setPackage("tv.yuyin");
        bindService(in, conn, Context.BIND_AUTO_CREATE);
        
        try {
            
            InputStream stream = getAssets().open("global.xiri");
            if (stream != null) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(stream));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
                String apkjson = getOriginalString(buffer.toString());
                Log.d(TAG, "global.xiri= " + apkjson);
                if (!TextUtils.isEmpty(apkjson)) {
                    JSONObject jo = new JSONObject(apkjson);
                    String glover = jo.optString("_sdk_version", "");
                    if (!glover.equals(mSDKVer))
                        mToastTip = true;
                }
            }
            stream = getAssets().open("local.xiri");
            if (stream != null) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(stream));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
                String apkjson = buffer.toString();
                Log.d(TAG, "local.xiri= " + apkjson);
                if (!TextUtils.isEmpty(apkjson)) {
                    JSONObject jo = new JSONObject(apkjson);
                    String locver = jo.optString("_sdk_version", "");
                    if (!locver.equals(mSDKVer))
                        mToastTip = true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate Exception");
            e.printStackTrace();
        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public final void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart");
        super.onStart(intent, startId);
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if (null != intent && NOTIFIYACTION.equals(intent.getAction())) {
            Log.d(TAG, "onStartCommand intent "
                    + (null == intent ? "is null." : intent.toURI())
                    + " mToastTip=" + mToastTip);
            if (mToastTip)
                Toast.makeText(this, "SDK与声明文件版本不一致", Toast.LENGTH_LONG).show();
            String action = intent.getStringExtra("_action");
            String command = intent.getStringExtra("_command");
            String localType = intent.getStringExtra("_localtype");
            if ((command == null || "".equals(command))
                    && "INIT".equals(action)) {
                Intent in = new Intent("tv.yuyin.START");
                in.setPackage("tv.yuyin");
                bindService(in, conn, Context.BIND_AUTO_CREATE);
                onInit();
            } else if ("_tv_live".equals(command) && null != mTVListener) {
                if ("CHANGECHANNELBYNAME".equals(action)) {
                    mTVListener.onChangeChannel(intent
                            .getStringExtra("channelname"),
                            intent.getStringExtra("_windowid") == null ? ""
                                    : intent.getStringExtra("_windowid"),
                            intent.getIntExtra("_source", SOURCE_UNKNOWN));
                } else if ("CHANGECHANNELBYNUMBER".equals(action)) {
                    mTVListener.onChangeChannel(
                            intent.getIntExtra("channelnumber", -1),
                            intent.getIntExtra("_source", SOURCE_UNKNOWN));
                } else if ("OPEN".equals(action)) {
                    mTVListener.onOpen(intent.getIntExtra("_source",
                            SOURCE_UNKNOWN));
                } else if ("NEXTCHANNEL".equals(action)) {
                    mTVListener.onNextChannel(intent.getIntExtra("_source",
                            SOURCE_UNKNOWN));
                } else if ("PREVCHANNEL".equals(action)) {
                    mTVListener.onPrevChannel(intent.getIntExtra("_source",
                            SOURCE_UNKNOWN));
                }
            } else if ("_tv_back".equals(command) && mTVBackListener != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                if ("CHANGECHANNELBYNAME".equals(action)) {
                    Date startdate = null;
                    Date enddate = null;
                    try {
                        if (!"".equals(intent.getStringExtra("startdate")))
                            startdate = sdf.parse(intent
                                    .getStringExtra("startdate")
                                    + " "
                                    + intent.getStringExtra("starttime"));
                        if (!"".equals(intent.getStringExtra("enddate")))
                            enddate = sdf.parse(intent
                                    .getStringExtra("enddate")
                                    + " "
                                    + intent.getStringExtra("endtime"));
                    } catch (ParseException e) {
                        // close_print_exception
                    }
                    mTVBackListener.onChangeChannel(intent
                            .getStringExtra("channelname"), startdate, enddate,
                            intent.getStringExtra("_windowid") == null ? ""
                                    : intent.getStringExtra("_windowid"));

                } else if ("CHANGEPROGRAM".equals(action)) {
                    Date startdate = null;
                    Date enddate = null;
                    try {
                        if (!"".equals(intent.getStringExtra("startdate")))
                            startdate = sdf.parse(intent
                                    .getStringExtra("startdate")
                                    + " "
                                    + intent.getStringExtra("starttime"));
                        if (!"".equals(intent.getStringExtra("enddate")))
                            enddate = sdf.parse(intent
                                    .getStringExtra("enddate")
                                    + " "
                                    + intent.getStringExtra("endtime"));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        // close_print_exception
                    }
                    mTVBackListener.onProgram(intent
                            .getStringExtra("channelname"), startdate, enddate,
                            intent.getStringExtra("_windowid") == null ? ""
                                    : intent.getStringExtra("_windowid"));

                } else if ("NEXTCHANNEL".equals(action)) {
                    mTVBackListener.onNextChannel();
                } else if ("PREVCHANNEL".equals(action)) {
                    mTVBackListener.onPrevChannel();
                } else if ("OPEN".equals(action)) {
                    mTVBackListener.onOpen();
                }
            } else if ("_video".equals(command)) {
                if ("EXECUTE".equals(action) && null != mVideoIntentListener) {
                    // String person = intent.getStringExtra("person");
                    // String area = intent.getStringExtra("area");
                    // String category = intent.getStringExtra("category");
                    // String modifier = intent.getStringExtra("modifier");
                    // int episode = intent.getIntExtra("episode", -1);
                    // String name = intent.getStringExtra("name");
                    // String popularity = intent.getStringExtra("popularity");
                    mVideoIntentListener.onExecute(intent);
                } else if ("SEARCH".equals(action)
                        && null != mVideoSearchListener) {
                    mVideoSearchListener.onSearch(intent.getStringExtra("url"),
                            intent.getStringExtra("title"));
                } else if ("OPENITEM".equals(action)
                        && null != mVideoDetailListener) {
                    mVideoDetailListener
                            .onShow(intent.getStringExtra("extrac"));
                }
            } else if ("_appstore".equals(command) && null != mAppStoreListener) {
                if ("EXECUTE".equals(action)) {
                    String name = intent.getStringExtra("name");
                    mAppStoreListener.onExecute(name);
                }
            } else if (null == localType && mAppListener != null) {
            	//获取语音传递过来的结果
                mAppListener.onExecute(intent);
            } else if (null != localType && mLocalAppListener != null) {
                mLocalAppListener.onExecute(intent);
            }
        } else if (null != intent && SELFACTION.equals(intent.getAction())) {
            mAppListener.onTextFilter(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
