package com.example.he.menutest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu可分为三种类型，OptionsMenu (选择菜单)
 * ContextMenu （上下文菜单） ，比如长按某个视图 弹出菜单选项就是上下文菜单
 * SubMenu(子菜单)
 */


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter adapter;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv);
        initList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        this.registerForContextMenu(listView);//注册上下文菜单

    }

    public void initList()

    {
        list.add("list1");
        list.add("list2");
        list.add("list3");
        list.add("list4");
        list.add("list5");
        list.add("list6");
        list.add("list7");
        list.add("list8");
        list.add("list9");

    }


    /**
     * 创建上下文菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        menu.add(1, 1, 1, "新添加的菜单");
        return true;
    }

    /**
     * 选择菜单响应事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_1:
                Toast.makeText(this, "这是选择菜单1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.options_2:
                Toast.makeText(this, "这是选择菜单2", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 创建上下文菜单
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        menu.setHeaderTitle("上下文菜单");
        menu.add(1, 01, 1, "上下文菜单1");
        menu.add(1, 02, 1, "上下文菜单2");
        menu.add(1, 03, 1, "上下文菜单3");
        menu.add(1, 04, 1, "上下文菜单4");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 01:
                Toast.makeText(this, "这是上下文菜单1", Toast.LENGTH_SHORT).show();
                break;
            case 02:
                Toast.makeText(this, "这是上下文菜单2", Toast.LENGTH_SHORT).show();
                break;
            case 03:
                Toast.makeText(this, "这是上下文菜单3", Toast.LENGTH_SHORT).show();
                break;
            case 04:
                Toast.makeText(this, "这是上下文菜单4", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}
