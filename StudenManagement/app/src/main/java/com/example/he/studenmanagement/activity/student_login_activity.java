package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.he.studenmanagement.R;

/**
 * 学生登录界面
 * Created by he on 2016/9/30.
 */
public class student_login_activity extends Activity {
    private EditText name;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_login_layout);

        name = (EditText) findViewById(R.id.student_login_activity_name_input);
        password = (EditText) findViewById(R.id.student_login_activity_password_input);
        login = (Button) findViewById(R.id.student_login_activity_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动学生登录后的界面并将学生的账户（id）传过去
                Intent intent = new Intent(student_login_activity.this, student_activity.class);

                intent.putExtra("id", name.getText().toString());
                startActivity(intent);
            }
        });


    }
}
