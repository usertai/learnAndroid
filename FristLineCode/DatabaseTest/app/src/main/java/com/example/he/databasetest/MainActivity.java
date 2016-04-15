package com.example.he.databasetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
private MyDatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        databaseHelper=new MyDatabaseHelper(this,"BookStore.db",null,3);
        Button createDatabase= (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_database:
                databaseHelper.getWritableDatabase();
                break;
        }
    }
}
