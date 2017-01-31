package com.example.he.myqq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.he.myqq.utils.HideStatusBar;
import com.example.he.myqq.utils.MyApplication;
import com.example.he.myqq.utils.SharedPreferrenceHelper;

/**
 * 引导界面
 */
public class SplashActivity extends AppCompatActivity {
    private static final int OK = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
                    boolean isSavePassword = SharedPreferrenceHelper.getSaveStatus(MyApplication.getContext());
                    Intent intent;
                    if (isSavePassword) {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    SplashActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        HideStatusBar.hide(this);
        handler.sendEmptyMessageDelayed(OK, 1500);
    }
}
