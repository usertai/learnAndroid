package com.example.he.animatortest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_);
        button = (Button) findViewById(R.id.button_);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_:
                Toast.makeText(this, "点了图片的位置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_:
                /**
                 * 如果用传统的Animation,即使图片已经移动到了坐标为(100,0)的位置但是点击原来图片的位置（0.0）
                 * 还是会有Toast信息
                 */
//                TranslateAnimation animation = new TranslateAnimation(0, 100, 0, 0);
//                animation.setDuration(1000);
//                animation.setFillAfter(true);
//                imageView.startAnimation(animation);

                /**
                 * 使用属性动画中的ObjectAnimator则不会出现这种情况
                 */

                //平移和旋转动画同时进行
//                ObjectAnimator.ofFloat(imageView,"translationX",0F,100F).setDuration(1000).start();
//                ObjectAnimator.ofFloat(imageView,"rotation",0F,360F).setDuration(1000).start();

                /**
                 * 使用AnimatorSet，为imageView添加动画集合，效果和上面一样,增强了对属性动画的控制
                 */

                ObjectAnimator animator1= ObjectAnimator.ofFloat(imageView,"translationX",0F,100F);
                ObjectAnimator animator2= ObjectAnimator.ofFloat(imageView,"rotation",0F,360F);
                ObjectAnimator animator3=ObjectAnimator.ofFloat(imageView,"translationY",0,100);
                AnimatorSet set=new AnimatorSet();
//                set.playTogether(animator1,animator2);//添加动画效果,同时进行
//                set.playSequentially(animator1,animator2);//添加动画效果,相继进行
                set.play(animator1).with(animator2);//右移加旋转
                set.play(animator3).after(animator2);//最后下移
                set.setDuration(1000);
                set.start();

                break;
        }
    }
}
