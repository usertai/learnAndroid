package com.example.he.myfileprovider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private File mInteriorRootDir;
    private static  final String  TAG=MainActivity.class.getSimpleName();
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        mInteriorRootDir=getFilesDir();

        if (mInteriorRootDir.canWrite()){
            File imageDir=new File(mInteriorRootDir.getAbsolutePath(),"images");
            if (!imageDir.exists()){
                imageDir.mkdirs();
            }

            File image=new File(imageDir.getAbsolutePath(),"default_image.png");
            FileOutputStream imageStream=null;
            try {
                imageStream=new FileOutputStream(image);
                imageBitmap.compress(Bitmap.CompressFormat.PNG,100,imageStream);
                imageStream.flush();
                imageStream.close();
                Log.d(TAG,"保存成功");
                
                //将图片插入系统图库
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(),
                            image.getAbsolutePath(), "default_image.png", null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //发送广播通知图库更新
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(image)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "onCreate: "+mInteriorRootDir.getAbsolutePath());

    }
}
