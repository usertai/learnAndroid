package com.example.he.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by he on 2016/4/29.
 */
public class MyService extends Service {

    private  DownloadBinder downloadBinder=new DownloadBinder();

    class DownloadBinder extends Binder{
        public  void startDownload(){
            Toast.makeText(MyService.this,"startDownload",Toast.LENGTH_SHORT).show();
        }
        public  void getProgress(){
            Toast.makeText(MyService.this,"getProgress executed",Toast.LENGTH_SHORT).show();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"service create",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"service command",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"service destroy",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
