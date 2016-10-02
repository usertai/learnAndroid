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
 * 添加学生信息的界面
 * Created by he on 2016/10/1.
 */
public class addStudent_info_activity extends Activity {

    private EditText name;
    private EditText sex;
    private EditText id;
    private EditText number;
    private EditText password;
    private EditText math;
    private EditText chinese;
    private EditText englise;

    private Button sure;//确定按钮

    Intent oldData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_student_info_layout);

        name = (EditText) findViewById(R.id.add_student_layout_name);
        sex = (EditText) findViewById(R.id.add_student_layout_sex);
        id = (EditText) findViewById(R.id.add_student_layout_id);
        number = (EditText) findViewById(R.id.add_student_layout_number);
        password = (EditText) findViewById(R.id.add_student_layout_password);
        math = (EditText) findViewById(R.id.add_student_layout_math);
        chinese = (EditText) findViewById(R.id.add_student_layout_chinese);
        englise = (EditText) findViewById(R.id.add_student_layout_english);


        oldData = getIntent();
        if (oldData.getStringExtra("haveData").equals("true")) {
            initInfo();//恢复旧数据
        }


        sure = (Button) findViewById(R.id.add_student_layout_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addStudent_info_activity.this, admin_activity.class);
                startActivity(intent);
            }
        });

    }

    //恢复旧数据
    private void initInfo() {
        String oldName = oldData.getStringExtra("name");
        name.setText(oldName);
        String oldSex = oldData.getStringExtra("sex");
        sex.setText(oldSex);
        String oldId = oldData.getStringExtra("id");
        id.setText(oldId);
        String oldNumber = oldData.getStringExtra("number");
        number.setText(oldNumber);
        String oldPassword = oldData.getStringExtra("password");
        password.setText(oldPassword);
        int mathScore = oldData.getIntExtra("mathScore", 0);
        math.setText(String.valueOf(mathScore));
        int chineseScore = oldData.getIntExtra("chineseScore", 0);
        chinese.setText(String.valueOf(chineseScore));
        int englishScore = oldData.getIntExtra("englishScore", 0);
        englise.setText(String.valueOf(englishScore));
    }


}
