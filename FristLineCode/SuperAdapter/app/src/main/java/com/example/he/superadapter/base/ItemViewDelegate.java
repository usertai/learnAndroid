package com.example.he.superadapter.base;

import com.example.he.superadapter.ViewHolder;

/**
 * ListView,GridView中item 用于回调的接口
 * Created by he on 2017/5/9.
 */

public interface ItemViewDelegate<T> {

    /**
     * item 对应的layoutId
     * @return
     */
    int getItemViewLayoutId();


    boolean isForView(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
