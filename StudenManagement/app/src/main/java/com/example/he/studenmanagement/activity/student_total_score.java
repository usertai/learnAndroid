package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.example.he.studenmanagement.R;
import com.example.he.studenmanagement.tools.Student;
import com.example.he.studenmanagement.tools.StudentScoreAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示学生总成绩排名的activity
 * Created by he on 2016/10/2.
 */
public class student_total_score extends Activity {
    private ListView total_score;
    private List<Student> list = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_toal_score_layout);
        initInfo();
        StudentScoreAdapter adapter = new StudentScoreAdapter(student_total_score.this, R.layout.student_score_item, list);
        total_score = (ListView) findViewById(R.id.total_list_view);
        total_score.setAdapter(adapter);

    }


    //初始化数据
    private void initInfo() {
        list.add(new Student(22, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        list.add(new Student(133, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        list.add(new Student(41, 12, "201311054", 13, "A", "18895333", "123456", "女"));
        list.add(new Student(51, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        list.add(new Student(61, 12, "201311054", 13, "A", "18895333", "123456", "女"));
        list.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        list.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "女"));
        list.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));
    }


}
