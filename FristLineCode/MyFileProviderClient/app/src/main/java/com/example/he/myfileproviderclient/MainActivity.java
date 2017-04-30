package com.example.he.myfileproviderclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView image;//用于展示从第三方应用获取的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= (ImageView) findViewById(R.id.image_);
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/png");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK ){
            Uri returnUri = data.getData();
            if (returnUri!=null){
                try {
                   OutputStream imageStream=getContentResolver().openOutputStream(returnUri);

                    Log.d("AAAA",imageStream.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
