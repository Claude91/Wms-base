package com.shqtn.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shqtn.base.bean.enter.Reason;
import com.shqtn.base.widget.LabelTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/10/10.
 */

public abstract class RvCommonAdapter<E> extends RecyclerView.Adapter {
    private int viewId;
    private Context context;
    private List<E> data;

    public RvCommonAdapter(Context context, List<E> data, int viewId) {
        this.viewId = viewId;
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(viewId, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            onBindViewHolder((ItemViewHolder) holder, data, position);
        }
    }

    public abstract void onBindViewHolder(ItemViewHolder holder, List<E> data, int position);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void update(ArrayList<E> mReasonList) {
        data = mReasonList;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        public <T> T findView(int viewId) {
            return (T) itemView.findViewById(viewId);
        }

        public ItemViewHolder setTextView(int textId, String text) {
            ((TextView) itemView.findViewById(textId)).setText(text);
            return this;
        }

        public ItemViewHolder setLabelText(int labelId, String text) {
            ((LabelTextView) itemView.findViewById(labelId)).setText(text);
            return this;
        }
    }
}
