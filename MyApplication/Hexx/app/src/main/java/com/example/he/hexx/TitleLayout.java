package com.example.he.hexx;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by he on 2017/2/27.
 */

public class TitleLayout extends LinearLayout {

    private static final String TAG="TitleLayout";


    private View mView;
    private Bitmap bitmap;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.title, this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bj);
//        Drawable drawable = new BitmapDrawable(null, sacleBitmap(bitmap, 0.5f));
//        mView.setBackground(drawable);
//        startAnimator();
//        preforAnimate(bitmap.getWidth(),bitmap.getWidth()/2);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startAnimator();
                preforAnimate(bitmap.getWidth()/2,bitmap.getWidth()/4);
                return false;
            }
        });

    }


    private void startAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "scaleX", 1f, 0.8f);
        animator.setDuration(5000);
        animator.start();
    }

    private Bitmap sacleBitmap(Bitmap bitmap, int requestWidth) {
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),resId);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
//        int requestHeight= (int) (height*(1f-fraction));
        return Bitmap.createBitmap(bitmap, requestWidth/2, 0, requestWidth, height);
    }


    private void preforAnimate(final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator evaluator = new IntEvaluator();

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                int requestWidth = evaluator.evaluate(fraction, start, end);
                Log.d(TAG, "Width "+bitmap.getWidth());
                Log.d(TAG, "requestWidth "+requestWidth);
                Drawable drawable = new BitmapDrawable(null, sacleBitmap(bitmap,requestWidth));
                mView.setBackground(drawable);
            }
        });
        valueAnimator.setDuration(5000).start();
    }


}
