package com.example.he.viewpagertest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> viewList;
    private myViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewList = new ArrayList<View>();
        View v1 = View.inflate(this, R.layout.view1, null);
        View v2 = View.inflate(this, R.layout.view2, null);
        View v3 = View.inflate(this, R.layout.view3, null);
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        viewPager = (ViewPager) findViewById(R.id.vp);
        adapter = new myViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
    }
}
