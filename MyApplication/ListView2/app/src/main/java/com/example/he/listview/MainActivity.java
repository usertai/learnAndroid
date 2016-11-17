package com.example.he.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 该项目实现ListView的常用功能：ListView的适配以及优化、ListView的点击事件，
 * ListView的下拉刷新， ListView的分页，ListView的滑动动画、ListView的上下文菜单
 */


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<ItemBean> itemBeanList;
    private myBaseAdapter adapter;
    private ScaleAnimation animation;//一个缩放动画
    private LayoutAnimationController controller ;//界面动画控制器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        itemBeanList = new ArrayList<ItemBean>();
        adapter = new myBaseAdapter(this, itemBeanList);
        animation=new ScaleAnimation(0F, 1F, 0F, 1F);
        controller=new LayoutAnimationController(animation);

        init();
        listView.setAdapter(adapter);

//        listView.setAdapter(new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,new Integer[]{1,2,3,4,5,6,7,8,9,12,32,2,4,4,5,6,1,2,2,3,4,54}));

        this.registerForContextMenu(listView);//为ListView注册上下文菜单


        /**
         * 滑动时启动动画
         */
        listView.setRecyclerListener(new AbsListView.RecyclerListener() {

            @Override
            public void onMovedToScrapHeap(View view) {
                    animation.setDuration(300);//设置动画时长0.3s
//                controller.setInterpolator(new BounceInterpolator());//为动画添加插值器
                    controller.setOrder(LayoutAnimationController.ORDER_NORMAL);//设置动画顺序
                    controller.setDelay(0.2F);//设置延迟
                    listView.setLayoutAnimation(controller);//为listView添加界面动画，这样每个item都有动画效果
                    listView.startLayoutAnimation();//启动界面动画
            }
        });


        /**
         * 为ListView添加点击事件监听器
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBean bean = itemBeanList.get(position);
                Toast.makeText(MainActivity.this, bean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 重写该方法创建上下文菜单
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("这是上下文菜单");//上下文菜单的标题
        menu.setHeaderIcon(R.mipmap.ic_launcher);//上下文菜单的图标

        //添加上下文菜单选项
        menu.add(1, 1, 1, "菜单选项1");
        menu.add(1, 2, 1, "菜单选项2");
        menu.add(1, 3, 1, "菜单选项3");

    }

    /**
     * 上下文菜单的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "点击了菜单选项1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "点击了菜单选项2", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "点击了菜单选项3", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * 向ListView中加载数据
     */
    private void init() {
        for (int i = 0; i < 200; i++)
            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher, "标题" + i, "内容" + i));
    }


}
