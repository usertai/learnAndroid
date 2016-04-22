package com.example.he.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mian_layout);
        Button button = (Button) findViewById(R.id.send_notice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取manager
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification;
                Notification.Builder builder = new Notification.Builder(MainActivity.this).setSmallIcon(R.mipmap.ic_launcher).setTicker("this is ticker");
                notification=builder.setContentTitle("this is text").setContentText("hello").build();
                manager.notify(1,notification);//启动通知

            }
        });
    }
}
