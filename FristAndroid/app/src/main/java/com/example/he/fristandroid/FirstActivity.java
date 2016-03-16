package com.example.he.fristandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by he on 2016/3/15.
 */
public class FirstActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);      //隐藏标题栏 必须在加载布局之前
        setContentView(R.layout.firstlayout);//加载布局
        Button button1 = (Button) findViewById(R.id.button1);//获取布局文件中定义的元素
        Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(FirstActivity.this, "这是第一个按钮", Toast.LENGTH_SHORT);
                //显示
                toast.show();
                //设置显示位置
                toast.setGravity(Gravity.CENTER, 10, 10);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this, "即将跳转", Toast.LENGTH_SHORT).show();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {

                }
                //显示Intent
//                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//                startActivity(intent);
                //隐式intent,参数指定action名
                Intent intent = new Intent("Action_Start");
                //添加category如果没有在AndroidManifest.xml中SecondActivity的intent-filter 中申明添加的Category就会报错
                intent.addCategory("Second_category");
                startActivity(intent);
            }
        });
    }

    @Override   //创建菜单选项
    public boolean onCreateOptionsMenu(Menu menu) {
        //给当前活动创建菜单
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override//获取菜单响应
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                //显示提醒
                Toast.makeText(FirstActivity.this, "you clicked add item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this, "you clicked remove item", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
