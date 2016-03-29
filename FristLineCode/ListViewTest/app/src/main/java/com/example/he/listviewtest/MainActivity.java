package com.example.he.listviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private String data[] = {"Apple", "Banana", "Orange", "Grape", "1", "2", "3", "4", "5","6","7","8","9","10","11","12"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView=(ListView)findViewById(R.id.list_view);
        //与数据之间建立连接
        listView.setAdapter(adapter);

    }
}
