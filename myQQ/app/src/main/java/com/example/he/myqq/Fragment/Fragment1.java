package com.example.he.myqq.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.he.myqq.R;
import com.example.he.myqq.UI.refreshlistview.PullToRefreshBase;
import com.example.he.myqq.UI.refreshlistview.PullToRefreshListView;
import com.example.he.myqq.UI.swipelistview.SwipeMenu;
import com.example.he.myqq.UI.swipelistview.SwipeMenuListView;
import com.example.he.myqq.utils.MessageBean;
import com.example.he.myqq.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 2017/1/29.
 */

public class Fragment1 extends Fragment {

    private List<MessageBean> mAppList;

//    private List<String> mAppList;

    private AppAdapter mAdapter;
    private PullToRefreshListView ptrlv;
    private Activity mActivity;
    private View mView;

    private static final int LOAD_UP = 1;
    private static final int LOAD_DOWN = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_UP:
                    loadUpMore();
                    ptrlv.setOnRefreshComplete();
                    break;
                case LOAD_DOWN:
                    loadBottomMore();
                    ptrlv.setOnRefreshComplete();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        mView = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mAppList = new ArrayList<MessageBean>();
        for (int i = 0; i < 20; i++) {
            mAppList.add(new MessageBean("你好", "item" + i, "2017-1-30", "xxx"));
        }

//        mAppList = new ArrayList<String>();
//        for (int i = 0; i < 20; i++) {
//            mAppList.add("item"+i);
//        }


        ptrlv = (PullToRefreshListView) mView.findViewById(R.id.listView);
        initListView();
    }

    /**
     * ptrlv相关的一些初始化
     * setFunctionConfig  设置该控件的功能
     * REFRESH        下拉刷新，上拉加载更多
     * SWIPE          侧滑删除
     * ALL            以上两个功能都有
     */
    private void initListView() {
        ptrlv.init(MyApplication.getContext());
        ptrlv.setFunctionConfig(PullToRefreshListView.FunctionConfig.ALL);

        /**
         * 下拉刷新，上拉加载更多时有用
         */
        ptrlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<SwipeMenuListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
//                loadUpMore();

                handler.sendEmptyMessageDelayed(LOAD_UP, 2000);
//                ptrlv.setOnRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
//                loadBottomMore();

                handler.sendEmptyMessageDelayed(LOAD_DOWN, 2000);
//                ptrlv.setOnRefreshComplete();
            }
        });

        /**
         * 设置listview条目的点击事件
         */
        ptrlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mActivity.getApplicationContext(), "条目" + position + "被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 设置侧滑删除事件
         */
        ptrlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                mAppList.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mActivity.getApplicationContext(), "delete" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mAdapter = new AppAdapter();
        ptrlv.setAdapter(mAdapter);

    }


    /**
     * 上拉刷新需要加载加载的数据
     */
    protected void loadUpMore() {
//        SystemClock.sleep(2000);
        mAppList.add(new MessageBean("你好", "zcc", "2002", "xxx"));
//        mAppList.add("A");
        mAdapter.notifyDataSetChanged();


    }

    /**
     * 下拉加载更多需要加载的数据
     */

    protected void loadBottomMore() {
//        SystemClock.sleep(2000);

        /**
         * 根据获取的数据判断，上拉加载是否可用，没有更多数据时可以禁止
         */
        ptrlv.setPullLoadEnabled(true);

    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            MessageBean bean = mAppList.get(position);
            if (convertView == null) {
                view = LayoutInflater.from(mActivity.getApplication()).inflate(R.layout.item_list_app, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) view.findViewById(R.id.user_friends_pic);
                holder.name = (TextView) view.findViewById(R.id.user_friends_name);
                holder.info = (TextView) view.findViewById(R.id.user_friends_info);
                holder.time = (TextView) view.findViewById(R.id.user_friends_time);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            ImageView imageView = holder.icon;

            final String uri = mAppList.get(position).getUri();
            final String tag = (String) imageView.getTag();
//            if (!uri.equals(tag)) {
            imageView.setImageResource(R.mipmap.ic_launcher);
//            }
//            if (mIsGridViewIdle && mCanGetBitmapFromNetWork) {
//                imageView.setTag(uri);
//                //异步加载图片
//                mImageLoader.bindBitmap(uri, imageView, mImageWidth, mImageWidth);
////                mImageLoader.bindBitmap(uri, imageView, 300, 300);
//            }
            holder.name.setText(bean.getName());
            holder.info.setText(bean.getInfo());
            holder.time.setText(bean.getTime());

            return view;
        }

        class ViewHolder {
            ImageView icon;
            TextView name;
            TextView info;
            TextView time;

        }

        @Override
        public Object getItem(int position) {
            return mAppList.get(position);
        }
    }

//    class AppAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mAppList.size();
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = View.inflate(mActivity.getApplicationContext(),
//                        R.layout.item_list_app2, null);
//                new ViewHolder(convertView);
//            }
//            ViewHolder holder = (ViewHolder) convertView.getTag();
//            holder.tv_name.setText(mAppList.get(position));
//            holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
//            return convertView;
//        }
//
//        class ViewHolder {
//            ImageView iv_icon;
//            TextView tv_name;
//
//            public ViewHolder(View view) {
//                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
//                tv_name = (TextView) view.findViewById(R.id.tv_name);
//                view.setTag(this);
//            }
//        }
//
//        @Override
//        public Object getItem(int position) {
//            // TODO Auto-generated method stub
//            return null;
//        }
//    }


}
