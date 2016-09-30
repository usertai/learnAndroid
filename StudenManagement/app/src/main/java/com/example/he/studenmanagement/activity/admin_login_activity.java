package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.he.studenmanagement.R;

/**
 * 管理员登录界面
 * Created by he on 2016/9/30.
 */
public class admin_login_activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.admin_login_layout);
        
    }
}
