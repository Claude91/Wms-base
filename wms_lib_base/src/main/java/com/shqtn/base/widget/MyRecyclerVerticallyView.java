package com.shqtn.base.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017-2-7.
 */
public class MyRecyclerVerticallyView extends RecyclerView {
    public MyRecyclerVerticallyView(Context context) {
        super(context);
        init(context);
    }

    public MyRecyclerVerticallyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRecyclerVerticallyView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context){
        /*this.setLayoutManager(new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });*/
    }
}
