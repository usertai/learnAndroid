package com.example.he.viewpagergallery;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 利用ViewPager打造画廊效果
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager  mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);


    }


}
