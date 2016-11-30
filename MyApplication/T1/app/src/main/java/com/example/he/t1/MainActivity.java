package com.example.he.t1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private movingImage image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= (movingImage) findViewById(R.id.imageV);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ccccc");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        image.autoMouse(event);
        return  false;
    }



}
