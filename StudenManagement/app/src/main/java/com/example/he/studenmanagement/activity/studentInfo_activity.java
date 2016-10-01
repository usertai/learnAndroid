package com.example.he.studenmanagement.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.he.studenmanagement.R;
import com.example.he.studenmanagement.tools.Student;
import com.example.he.studenmanagement.tools.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示学生信息的activity
 * Created by he on 2016/10/1.
 */
public class studentInfo_activity extends Activity {
    private List<Student> studentList = new ArrayList<Student>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.studentinfo_activity_layout);
        initStudent();
        StudentAdapter adapter = new StudentAdapter(studentInfo_activity.this, R.layout.student_item, studentList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Student student = studentList.get(position);//捕获学生实例
                AlertDialog.Builder builder = new AlertDialog.Builder(studentInfo_activity.this);
                LayoutInflater factory = LayoutInflater.from(studentInfo_activity.this);
                final View textEntryView = factory.inflate(R.layout.stundent_info_layout, null);//加载AlertDialog自定义布局
                builder.setView(textEntryView);
                builder.setTitle("请选择相关操作");

                Button selectInfo = (Button) textEntryView.findViewById(R.id.student_info_select);//查看学生详细信息按钮
                selectInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次弹出一个alertDialog，用于显示详细学生信息
                        AlertDialog.Builder select_builder = new AlertDialog.Builder(studentInfo_activity.this);
                        select_builder.setTitle("学生详细信息");
                        StringBuilder sb = new StringBuilder();
                        sb.append("姓名：" + student.getName() + "\n");
                        sb.append("学号：" + student.getId() + "\n");
                        sb.append("手机号：" + student.getNumber() + "\n");
                        int math = student.getMathScore();//数学成绩
                        sb.append("数学成绩：" + math + "\n");
                        int chinese = student.getChineseScore();
                        sb.append("语文成绩：" + chinese + "\n");
                        int english = student.getEnglishScore();
                        sb.append("英语成绩：" + english + "\n");
                        int sum = math + chinese + english;//总成绩
                        sb.append("总成绩：" + sum + "\n");
                        select_builder.setMessage(sb.toString());
                        select_builder.create().show();

                    }
                });


                //删除学生信息
                Button delete_info = (Button) textEntryView.findViewById(R.id.student_info_delete);
                delete_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder delete_builder = new AlertDialog.Builder(studentInfo_activity.this);
                        delete_builder.setTitle("警告！！！！");
                        delete_builder.setMessage("您将删除该学生信息，此操作不可逆，请谨慎操作！");
                        delete_builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        delete_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //填写删除学生信息的相关逻辑


                            }
                        });
                        delete_builder.create().show();

                    }
                });

                //修改学生信息,通过intent传递旧学生信息
                Button update_info = (Button) textEntryView.findViewById(R.id.student_info_update);
                update_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到添加学生信息的界面,通过intent传递数据

                    }
                });


                builder.create().show();
            }
        });


    }


    //初始化学生信息
    private void initStudent() {
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "女"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "女"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "女"));
        studentList.add(new Student(11, 12, "201311054", 13, "A", "18895333", "123456", "男"));


    }


}
