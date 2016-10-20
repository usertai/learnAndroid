package com.example.he.autocompletetextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * autoCompleteTextView 控件 demo
 */
public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView textView;
    private MultiAutoCompleteTextView textView2;
    private ArrayAdapter<String> adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (AutoCompleteTextView) findViewById(R.id.t1);
        textView2= (MultiAutoCompleteTextView) findViewById(R.id.t2);
        list = new ArrayList<String>();
        list.add("shanghai1");
        list.add("shanghai2");
        list.add("beijing");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        textView.setAdapter(adapter);
        textView2.setAdapter(adapter);
        textView2.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
