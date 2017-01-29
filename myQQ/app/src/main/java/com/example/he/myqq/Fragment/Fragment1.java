package com.example.he.myqq.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.he.myqq.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 2017/1/29.
 */

public class Fragment1 extends Fragment {

    private List<String> mAppList;
    private AppAdapter mAdapter;
    private PullToRefreshListView ptrlv;
    private Activity mActivity;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        mView=view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mAppList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mAppList.add("item " + i);
        }
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
                loadUpMore();
                ptrlv.setOnRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
                loadBottomMore();
                ptrlv.setOnRefreshComplete();
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

                return false;
            }
        });

        mAdapter = new AppAdapter();
        ptrlv.setAdapter(mAdapter);

    }


    /**
     * 上拉刷新需要加载加载的数据
     */
    protected void loadUpMore() {
        SystemClock.sleep(2000);
        mAppList.add("A");
        mAppList.add("B");
        mAdapter.notifyDataSetChanged();


    }

    /**
     * 下拉加载更多需要加载的数据
     */

    protected void loadBottomMore() {
        SystemClock.sleep(2000);

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
            if (convertView == null) {
                convertView = View.inflate(mActivity.getApplicationContext(),
                        R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
//			holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
//			holder.tv_name.setText(item.loadLabel(getPackageManager()));
            holder.tv_name.setText(mAppList.get(position));
            holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
