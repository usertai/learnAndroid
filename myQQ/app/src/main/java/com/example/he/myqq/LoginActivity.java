package com.example.he.myqq;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText userID;
    private EditText password;
    private Button login;
    private TextView forgetPassword;
    private TextView register;
    private CheckBox checkBox;
    private TextView clause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HideStatusBar.hide(this);

        userID = (EditText) findViewById(R.id.login_edit);
        password = (EditText) findViewById(R.id.password_edit);
        login = (Button) findViewById(R.id.login_button);
        forgetPassword = (TextView) findViewById(R.id.textView);
        register = (TextView) findViewById(R.id.textView2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        clause = (TextView) findViewById(R.id.textView5);

        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        clause.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                Log.d(TAG, "userName" + userID.getText().toString() + "  password" + password.toString());
                if (userID.getText().toString().equals("123456") && password.getText().toString().equals("123456")) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    //切换动画
                    overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                    this.finish();
                } else {
                    Toast.makeText(this, "账户或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.checkBox:
                if (!checkBox.isChecked()) {
                    login.setTextColor(Color.GRAY);
                    login.setClickable(false);
                } else {
                    login.setTextColor(Color.WHITE);
                    login.setClickable(true);
                }
                break;
            case R.id.textView:
                Toast.makeText(this, "暂不支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView2:
                Toast.makeText(this, "暂不支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView5:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
