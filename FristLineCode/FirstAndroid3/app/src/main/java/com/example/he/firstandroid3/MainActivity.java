package com.example.he.firstandroid3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button,button2;
    private EditText editText;
    private ImageView imageView;
    private int count=1;//用于统计点击次数 实现换图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_layout);
        button = (Button) findViewById(R.id.button1);
        button2=(Button) findViewById(R.id.button2);
        editText=(EditText)findViewById(R.id.edit_text);
        imageView=(ImageView)findViewById(R.id.image_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=editText.getText().toString();
                Toast.makeText(MainActivity.this,data,Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count%2==1){
                    imageView.setImageResource(R.drawable.ic_launcher);
                }else{
                    imageView.setImageResource(R.drawable.second);
                }
                count++;
            }
        });

    }
}
