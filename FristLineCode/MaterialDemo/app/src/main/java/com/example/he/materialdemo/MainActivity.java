package com.example.he.materialdemo;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        button = (Button) findViewById(R.id.b);
        textView = (TextView) findViewById(R.id.t);

        textView.post(new Runnable() {
            @Override
            public void run() {
                int cx = (textView.getLeft() + textView.getRight()) / 2;
                int cy = (textView.getTop() + textView.getBottom()) / 2;
                int finalRadius = textView.getWidth();
                //ViewAnimationUtils.createCircularReveal()方法
                // 可以使用一个附着在视图上的圆形，显示或隐藏这个视图。
                Animator animator = ViewAnimationUtils.createCircularReveal(textView, cx, cy, 0, finalRadius);
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(1000);
                animator.start();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //5.0以上的跳转动画
                getWindow().setExitTransition(new Explode());
                Intent intent = new Intent(MainActivity.this, SecondAcitvity.class);
                //执行跳转动画
                startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(MainActivity.this).toBundle());

//                button.setBackgroundResource(R.drawable.heart);

            }
        });

    }
}
