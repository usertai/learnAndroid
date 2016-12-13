package com.example.he.toolbartest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * 基本上所有的控件都需要添加依赖
 * MaterialDesignTest内容包括
 * 1、Toolbar控件逐渐代替ActionBar
 * 2、侧滑菜单--->DrawerLayout控件
 * DrawerLayout是一个布局，在布局中允许放入两个直接子控件，
 * 第一个子控件是主屏幕显示的内容，第二个子控件是滑动菜单显示的内容
 * 3、NavigationView
 * 使用NavigationView需要 menu（用于显示具体的菜单项） 和 headerLayout（显示头部布局）
 * 4、FloatingActionButton
 *   一个具有悬浮效果的Button,功能和普通button一样
 * 5、Snackbar
 *      和Toast一样是提醒用户的一种工具，但是toast只能提醒用户发生了什么，不能交互
 *      SnackBar可以添加交互按钮
 * 6、CoordinatorLayout，见main.xml
 *      FrameLayout的加强版，普通情况下和FrameLayout的作用相同
 *      CoordinatorLayout可以监听其所有的子控件的各种事件，然后做出适当的响应
 *
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionButton= (FloatingActionButton) findViewById(R.id.fab);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //在ActionBar中显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            //在ActionBar中设置导航按钮的图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //将在navigationView中的call默认设置为选中
        navigationView.setCheckedItem(R.id.nav_call);
        //navigationView子菜单的监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();//关闭侧滑菜单
                return true;
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setAction添加交互
                Snackbar.make(v,"Snackbar显示的内容",Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Snackbar中添加的交互",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }


    /**
     * 创建选择菜单，即Toolbar中显示的内容
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * 选择菜单的点击
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "you click backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "you click delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "you click settings", Toast.LENGTH_SHORT).show();
                break;
            //按了导航按钮，弹出侧滑菜单
            case android.R.id.home:
                //与xml中drawerLayout的第二个子控件中layout_gravity属性保持一致）
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
