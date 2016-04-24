package com.example.he.choosepictest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 调用摄像头拍照并进行照片的修剪
 */

public class MainActivity extends Activity {
    private final static int TAKE_PHOTO = 1;
    private final static int CROP_PHOTO = 2;


    private Button take_photo;
    private ImageView photo;
    private Uri image_uri;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        take_photo = (Button) findViewById(R.id.take_photo);
        photo = (ImageView) findViewById(R.id.photo);

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");//图片保存到SD卡根目录
                try {
                    //如果存在就删除
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    image_uri = Uri.fromFile(file);//将文件转换为uri对象
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                    startActivityForResult(intent, TAKE_PHOTO);//启动相机程序

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(image_uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;

            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //对刚拍的相片进行修剪
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(image_uri));
                        photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
