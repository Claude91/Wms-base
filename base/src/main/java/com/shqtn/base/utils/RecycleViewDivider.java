package com.shqtn.base.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by android on 2017/7/13.
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    private int margin;

    public RecycleViewDivider(Context context, int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}
