package com.shqtn.base.listener;

import android.view.View;

/**
 * Created by Administrator on 2016-11-7.
 */
public class OnItemViewClickListener<T> implements View.OnClickListener {
    OnItemClickListener l;

    public void setOnClickListener(View itemView , T t){
        itemView.setTag(t);
        itemView.setOnClickListener(this);
    }

    public OnItemViewClickListener(OnItemClickListener l) {
        this.l = l;
    }

    @Override
    public void onClick(View v) {
        T bean = (T) v.getTag();
        l.onItemClick(v, bean);
    }
}
