package com.example.he.asyncloding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by he on 2016/11/21.
 */

public class myAdapter extends BaseAdapter {

    private List<ItemBean> list;
    private Context context;

    public myAdapter(List<ItemBean> list, Context context) {
        this.list = list;
        this.context = context;
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
        View view ;
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

        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.title.setText(bean.getTitle());
        holder.content.setText(bean.getContent());

        return view;
    }


    class viewHolder {
        ImageView image;
        TextView title;
        TextView content;
    }

}
