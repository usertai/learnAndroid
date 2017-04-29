package tv.yuyin.view.proxy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class VoiceService extends Service {
    private static final String tag = VoiceService.class.getSimpleName();
    private static IVoiceListener mVoiceListener = null;
    private static int iTokenId = -1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        ZLog.d(tag, "intent [" + intent.toUri(0) + "]");

        if (!ZAgreement.ACTION_CONNECT.equals(intent.getAction())) {
            return super.onStartCommand(intent, flags, startId);
        }

        if (intent.hasExtra("log.control")) {
            boolean open = intent.getBooleanExtra("log.control", true);
            ZLog.setLog(open);
            return super.onStartCommand(intent, flags, startId);
        }

        iTokenId = intent.getIntExtra(ZAgreement.K_TOKEN, -1);
        if (iTokenId == -1) {
            return super.onStartCommand(intent, flags, startId);
        }

        String operation = intent.getStringExtra(ZAgreement.K_OPERATION);
        if (operation == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        if (mVoiceListener == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        if (operation.equals(ZAgreement.V_OPERATION_onStart)) {
            mVoiceListener.onStart();
        } else if (operation.equals(ZAgreement.V_OPERATION_onVoice)) {
            int vol = intent.getIntExtra(ZAgreement.K_ONVOICE_vol, -1);
            if (vol != -1) {
                mVoiceListener.onVoice(vol);
            }
        } else if (operation.equals(ZAgreement.V_OPERATION_onRecognizing)) {
            mVoiceListener.onRecognizing();
        } else if (operation.equals(ZAgreement.V_OPERATION_onEnd)) {
            int code = intent.getIntExtra(ZAgreement.K_ONEND_code, -1);
            String msg = intent.getStringExtra(ZAgreement.K_ONEND_msg);

            if (code == -1) {
                return super.onStartCommand(intent, flags, startId);
            }

            boolean hasHandle = mVoiceListener.onEnd(code, msg);

            Intent in = genIntent(this);
            in.setPackage(ZAgreement.PACKAGE_YUYIN);
            in.setAction(ZAgreement.ACTION_REPORT_RESULT);
            in.putExtra(ZAgreement.K_HAS_HANDLE, hasHandle);
            startService(in);
            in.setPackage(ZAgreement.PACKAGE_XIRI);
            startService(in);
            
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public static class IVoice {
        public static boolean regist(Context context, String jsonData,
                IVoiceListener lsn) {
            if (context == null) {
                return false;
            }
            mVoiceListener = lsn;

            Intent in = VoiceService.genIntent(context);
            in.setAction(ZAgreement.ACTION_REGIST);
            in.putExtra(ZAgreement.K_REGIST_PARAM, jsonData);
            context.startService(in);
            in.setPackage(ZAgreement.PACKAGE_XIRI);
            context.startService(in);
            return true;
        }

        public static boolean unRegist(Context context) {
            if (context == null) {
                return false;
            }
            mVoiceListener = null;

            Intent in = VoiceService.genIntent(context);
            in.setAction(ZAgreement.ACTION_UNREGIST);
            context.startService(in);
            in.setPackage(ZAgreement.PACKAGE_XIRI);
            context.startService(in);
            return true;
        }

        public static void setLog(boolean open) {
            ZLog.setLog(open);
        }
    }

    protected static class ZLog {
        private static boolean bOpen = true;

        protected static void d(String tag, String msg) {
            if (bOpen) {
                Log.d(tag, msg);
            }
        }

        protected static void e(String tag, String msg) {
            if (bOpen) {
                Log.e(tag, msg);
            }
        }

        protected static void setLog(boolean open) {
            bOpen = open;
        }

        static {
            d(tag, "VoiceService version " + ZAgreement.PROTOCAL_VERSION);
        }
    }

    private static Intent genIntent(Context ctx) {
        Intent in = new Intent();
        in.putExtra(ZAgreement.K_PROTOCAL_VERSION, ZAgreement.PROTOCAL_VERSION);
        in.putExtra(ZAgreement.K_FROM_PACKAGE, ctx.getPackageName());
        in.putExtra(ZAgreement.K_TOKEN, iTokenId);
        return in;
    }
}
