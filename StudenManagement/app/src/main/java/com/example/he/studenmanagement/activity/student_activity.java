package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.he.studenmanagement.R;

/**
 * Created by he on 2016/10/2.
 */
public class student_activity extends Activity {
    private Button select;
    private Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_layout);

        select = (Button) findViewById(R.id.student_activity_selectInfo);
        changePassword = (Button) findViewById(R.id.student_activity_changePassword);

        //以AlertDialog的形式显示个人详细信息
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(student_activity.this);
                builder.setTitle("个人信息");
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                StringBuilder sb = new StringBuilder();
                sb.append("姓名：" + "xxx" + "\n");
                sb.append("学号：" + id + "\n");
                builder.setMessage(sb.toString());
                builder.create().show();
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(student_activity.this);
                LayoutInflater factory = LayoutInflater.from(student_activity.this);
                final View view = factory.inflate(R.layout.change_password_layout, null);
                builder.setView(view);
                builder.setTitle("修改密码");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                builder.create().show();
            }
        });


    }
}
