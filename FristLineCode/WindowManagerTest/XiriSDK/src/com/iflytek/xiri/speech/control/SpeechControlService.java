package com.iflytek.xiri.speech.control;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

public class SpeechControlService extends Service {
    private IControlCallback myCallback;
    private String TAG = SpeechControlService.class.getSimpleName();
    private boolean isFeedback = false;
    @Override
    public IBinder onBind(Intent arg0) {
        return myBind;
    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001 :
                    XiriStartSpeech();
                    break;
                case 0x002 :
                    XiriStartText((String) msg.obj);
                    break;
                case 0x003 :
                    try {
                        myCallback.onFeedback(isFeedback, (String) msg.obj,
                                msg.arg1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default :
                    break;
            }

            super.handleMessage(msg);
        }

    };
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    };
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
    };

    ISpeechControlService.Stub myBind = new ISpeechControlService.Stub() {

        @Override
        public void startSpeech() throws RemoteException {
            Log.d(TAG, "startSpeech");
            Message msg = new Message();
            msg.what = 0x001;
            mHandler.sendMessage(msg);
        }

        @Override
        public void startControl(String text) throws RemoteException {
            Log.d(TAG, "startControl " + text);
            Message msg = new Message();
            msg.what = 0x002;
            msg.obj = text;
            mHandler.sendMessage(msg);

        }

        @Override
        public void registerCallBack(IControlCallback cb)
                throws RemoteException {
            Log.d(TAG, "initcallback ");
            myCallback = cb;
        }
    };
    /**
     * 开始录音
     */
    public void XiriStartSpeech() {
    }
    /**
     * @param result
     *            处理结果
     * @param feedback
     *            播报内容
     * @param feedbackflag
     *            播报类型 0、英文 1、执行 2、静默 3、聊天对话 4、错误信息
     */
    public void FeedBack(boolean result, String feedback, int feedbackflag) {
        isFeedback = result;
        if (!TextUtils.isEmpty(feedback)) {
            Message msg = new Message();
            msg.what = 0x003;
            msg.obj = feedback;
            msg.arg1 = feedbackflag;
            mHandler.sendMessage(msg);
        }
    };
    /**
     * @param Text
     *            识别文本
     */
    public void XiriStartText(String Text) {
    }
}
