package com.example.he.myqq.UI;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.he.myqq.MainActivity;
import com.example.he.myqq.R;

/**
 * 主界面title布局
 * Created by he on 2017/1/26.
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener {

    private ImageView home;
    private Button news;
    private Button phone;
    private ImageView add;
    private Context mContext;


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.header, this);
        mContext = context;
        home = (ImageView) findViewById(R.id.home_);
        news = (Button) findViewById(R.id.news);
        phone = (Button) findViewById(R.id.phone);
        add = (ImageView) findViewById(R.id.add_);
        home.setOnClickListener(this);
        news.setOnClickListener(this);
        phone.setOnClickListener(this);
        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_:
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.news:
                Toast.makeText(mContext, "点击了消息按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.phone:
                Toast.makeText(mContext, "点击了通话按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_:

                break;
            default:
                break;
        }

    }
}
