package com.shqtn.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by android on 2017/7/13.
 */

public abstract class GridAdapter<T> extends RecyclerView.Adapter<GridAdapter.GridViewHolder>{
    private Context mContext;
    private int resId;
    private List<T> data;

    public GridAdapter(Context context, List<T> data, int resId) {
        this.mContext = context;
        this.resId = resId;
        this.data = data;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(resId, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        onBindData(holder,data.get(position),position);
    }

    protected abstract void onBindData(GridViewHolder holder, T t, int position);


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void update(List<T> list) {
        data = list;
        notifyDataSetChanged();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public GridViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public <T> T getView(int id){
            return (T) itemView.findViewById(id);
        }

        public void setImageView(int id, int iconId){
            ((ImageView)itemView.findViewById(id)).setImageResource(iconId);
        }

        public void setText(int id,String text){
            ((TextView) itemView.findViewById(id)).setText(text);
        }

    }
}
