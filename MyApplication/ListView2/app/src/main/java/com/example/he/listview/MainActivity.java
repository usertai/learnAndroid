package com.example.he.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.he.listview.R.layout.head;

/**
 * 该项目实现ListView的常用功能：ListView的适配以及优化、ListView的点击事件，
 * ListView的下拉刷新， ListView的分页，ListView的滑动动画、ListView的上下文菜单
 */


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<ItemBean> itemBeanList;
    private myBaseAdapter adapter;
    private ScaleAnimation animation;//一个缩放动画
    private LayoutAnimationController controller;//界面动画控制器

    private int i = 1;

    /**
     * 下拉刷新需要用到的成员变量
     */
    private int startY;
    private boolean top = false;//当ListView滑动到顶部时top为true
    private int headerHeight;//header的高度
    private View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lv);
        itemBeanList = new ArrayList<ItemBean>();
        adapter = new myBaseAdapter(this, itemBeanList);
        animation = new ScaleAnimation(0F, 1F, 0F, 1F);
        controller = new LayoutAnimationController(animation);

        init();//实例化ListView
        listView.setAdapter(adapter);
        this.registerForContextMenu(listView);//为ListView注册上下文菜单


        /**
         * 获取header布局以及向ListView中添加header
         */
        header = LayoutInflater.from(this).inflate(head, null);
        header.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//测量head
        headerHeight = header.getMeasuredHeight();//获取高度
//        Log.i("header",""+headerHeight);
        setTopPadding(-headerHeight);//设置header的向上边距
        listView.addHeaderView(header);//添加头布局


        /**
         * 滑动时启动动画
         */
//        listView.setRecyclerListener(new AbsListView.RecyclerListener() {
//
//            @Override
//            public void onMovedToScrapHeap(View view) {
//                animation.setDuration(300);//设置动画时长0.3s
////                controller.setInterpolator(new BounceInterpolator());//为动画添加插值器
//                controller.setOrder(LayoutAnimationController.ORDER_NORMAL);//设置动画顺序
//                controller.setDelay(0.2F);//设置延迟
//                listView.setLayoutAnimation(controller);//为listView添加界面动画，这样每个item都有动画效果
//                listView.startLayoutAnimation();//启动界面动画
//            }
//        });

        /**
         * 为ListView添加点击事件监听器,由于ListView中添加了header所以改变了itemBeanList中的位置，因此要进行-1操作
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemBean bean = itemBeanList.get(position - 1);
                Toast.makeText(MainActivity.this, bean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 为ListView设置滚动监听
         */

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        /**
         * 为ListView添加手势监听
         */

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    //手指按下
                    case MotionEvent.ACTION_DOWN:
                        int t = listView.getFirstVisiblePosition();//如果t为0则表示ListView已经滑动到顶部
                        if (t == 0) {
                            startY = (int) event.getY();
                            top = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:


                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (top)
                            headMove(event);
                        break;
                }


                return false;
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


    /**
     * 设置header的上边距达到隐藏header,逐渐显示header的效果
     *
     * @param topPadding
     */
    private void setTopPadding(int topPadding) {


        header.setPadding(header.getLeft(), topPadding, header.getRight(), header.getBottom());
        header.invalidate();
    }


    private void headMove(MotionEvent event) {
        int lastY = (int) event.getY();
        int space = lastY - startY;
        int topPadding = space - headerHeight;
//        header = LayoutInflater.from(this).inflate(head, null);
//        Log.i("space", "" + space);
////            listView.addHeaderView(header);
        if (i ==1) {
            Log.i("space", "" + space);
            setTopPadding(-topPadding);
//            listView.addHeaderView(header);
//            header.invalidate();
        }

        i++;

    }


}
