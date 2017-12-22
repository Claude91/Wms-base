package com.shqtn.base.listener;

import android.view.View;


/**
 * Created by Administrator on 2016-11-4.
 */
public abstract class OnSelectHomeBottomListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        int selectStr = (int) v.getTag();
        selectTag(v,selectStr);
    }
    protected abstract void selectTag(View v, int selectTag);
}
