package com.example.he.asyncloding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * 为了提升ListView的效率，在ListView滚动过程中停止所有的加载项，当ListView停止滚动后再加载
 * Created by he on 2016/11/21.
 */

public class myAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private List<ItemBean> list;
    private Context context;
    private ListView listV;
    private int mStart, mEnd;
    private ImageLoader loader;
    private boolean first;


    public static String imagePath[];//用于储存imageURL


    public myAdapter(List<ItemBean> list, Context context, ListView listView) {
        this.list = list;
        this.context = context;
        this.listV = listView;
        first=true;
        loader = new ImageLoader(listV);
        imagePath = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            imagePath[i] = list.get(i).getImageUrl();
        }
        listV.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        viewHolder holder;
        ItemBean bean = list.get(position);

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            holder = new viewHolder();
            holder.image = (ImageView) view.findViewById(R.id.item_image);
            holder.title = (TextView) view.findViewById(R.id.tv_title);
            holder.content = (TextView) view.findViewById(R.id.tv_content);
            view.setTag(holder);

        } else {
            view = convertView;
            holder = (viewHolder) view.getTag();
        }

        //将每个图片与图片的url绑定,防止因为ListView的缓存造成的错位
        String urlTag = bean.getImageUrl();
        holder.image.setTag(urlTag);
        loader.loadingImageByAsyncTask(holder.image, urlTag);
        holder.title.setText(bean.getTitle());
        holder.content.setText(bean.getContent());

        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        //当ListView滑动停止时，从网络中加载图片
        if (scrollState == SCROLL_STATE_IDLE) {
            loader.loadImage(mStart, mEnd);
        } else {
            //当ListView正则滑动时，停止从网络中加载图片
            loader.cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;

        //第一次显示的时候调用
        if(first && visibleItemCount>0){
            loader.loadImage(mStart, mEnd);
            first=false;
        }


    }


    class viewHolder {
        ImageView image;
        TextView title;
        TextView content;
    }

}
