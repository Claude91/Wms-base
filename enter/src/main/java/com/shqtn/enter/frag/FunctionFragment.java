package com.shqtn.enter.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.BaseFragment;
import com.shqtn.base.C;
import com.shqtn.base.GridAdapter;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.FunctionBean;
import com.shqtn.base.utils.RecycleViewDivider;
import com.shqtn.enter.FunctionChangeEvent;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.info.IFunctionLoad;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/9/21.
 */

public class FunctionFragment extends BaseFragment {


    private static final int GRID_COUNT = 2;
    private GridAdapter<FunctionBean> mInAdapter;
    private GridAdapter<FunctionBean> mEnterAdapter;
    private GridAdapter<FunctionBean> mExitAdapter;
    private List<FunctionBean> mInFunctionList;
    private List<FunctionBean> mEnterFunctionList;
    private List<FunctionBean> mExitFunctionList;

    public static FunctionFragment newInstance() {

        Bundle args = new Bundle();

        FunctionFragment fragment = new FunctionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            FunctionBean bean = null;
            int i = v.getId();
            if (i == R.id.function_enter) {
                bean = mEnterFunctionList.get(position);

            } else if (i == R.id.function_exit) {
                bean = mExitFunctionList.get(position);

            } else if (i == R.id.function_in) {
                bean = mInFunctionList.get(position);
            }
            if (bean == null) {
                return;
            }
            Intent intent = new Intent();
            intent.setClassName(getActivity(), bean.getAtyClazzName());
            Bundle bundle = bean.getBundle();
            intent.putExtra(BaseActivity.INTENT_BUNDLE, bundle);
            startActivity(intent);
        }
    };

    private RecyclerView rvEnter;
    private RecyclerView rvExit;
    private RecyclerView rvIn;

    @Override
    public int getCreateViewId() {
        return R.layout.frag_function;
    }

    @Override
    public void initData() {
        super.initData();
        FunctionChangeEvent.getInstance().register(this);
        IFunctionLoad functionLoad = InfoLoadUtils.getInstance().getFunctionLoad();
        if (functionLoad != null) {
            mInFunctionList = new ArrayList<>();
            functionLoad.addInDepotFunction(mInFunctionList);
            mEnterFunctionList = new ArrayList<>();
            functionLoad.addEnterFunction(mEnterFunctionList);
            mExitFunctionList = new ArrayList<>();
            functionLoad.addExitFunction(mExitFunctionList);
        }

        mInAdapter = new GridAdapter<FunctionBean>(getContext(), null, R.layout.item_grid_home_function) {

            @Override
            protected void onBindData(GridViewHolder holder, FunctionBean functionBean, int position) {
                holder.itemView.setId(R.id.function_in);
                holder.itemView.setTag(position);
                holder.itemView.setOnClickListener(mItemClickListener);

                holder.setText(R.id.item_grid_home_tv_text, functionBean.getName());
                holder.setImageView(R.id.item_grid_home_img, functionBean.getIconId());
            }
        };
        mEnterAdapter = new GridAdapter<FunctionBean>(getContext(), null, R.layout.item_grid_home_function) {

            @Override
            protected void onBindData(GridViewHolder holder, FunctionBean functionBean, int position) {
                holder.itemView.setId(R.id.function_enter);
                holder.itemView.setTag(position);
                holder.itemView.setOnClickListener(mItemClickListener);
                holder.setText(R.id.item_grid_home_tv_text, functionBean.getName());
                holder.setImageView(R.id.item_grid_home_img, functionBean.getIconId());
            }
        };
        mExitAdapter = new GridAdapter<FunctionBean>(getContext(), null, R.layout.item_grid_home_function) {

            @Override
            protected void onBindData(GridViewHolder holder, FunctionBean functionBean, int position) {
                holder.itemView.setId(R.id.function_exit);
                holder.itemView.setTag(position);
                holder.itemView.setOnClickListener(mItemClickListener);
                holder.setText(R.id.item_grid_home_tv_text, functionBean.getName());
                holder.setImageView(R.id.item_grid_home_img, functionBean.getIconId());
            }
        };

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FunctionChangeEvent.getInstance().unregister(this);
    }

    @Override
    public void bindView(View view) {
        rvEnter = view.findViewById(R.id.frag_home_depot_enter_recyclerView);
        rvExit = view.findViewById(R.id.frag_home_depot_exit_recyclerView);
        rvIn = view.findViewById(R.id.frag_home_depot_in_recyclerView);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IFunctionLoad iFunctionLoad) {
        mEnterFunctionList.clear();
        mExitFunctionList.clear();
        mInFunctionList.clear();
        iFunctionLoad.addEnterFunction(mEnterFunctionList);
        iFunctionLoad.addExitFunction(mExitFunctionList);
        iFunctionLoad.addInDepotFunction(mInFunctionList);
        mEnterAdapter.update(mEnterFunctionList);
        mExitAdapter.update(mExitFunctionList);
        mInAdapter.update(mInFunctionList);
    }

    @Override
    public void initWidget(View view) {
        super.initWidget(view);
        //设置不需要滑动，将滑动交给父类
        rvEnter.setLayoutManager(new GridLayoutManager(getActivity(), GRID_COUNT) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvExit.setLayoutManager(new GridLayoutManager(getActivity(), GRID_COUNT) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvIn.setLayoutManager(new GridLayoutManager(getActivity(), GRID_COUNT) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rvIn.addItemDecoration(new RecycleViewDivider(getContext(), 4));
        rvEnter.addItemDecoration(new RecycleViewDivider(getContext(), 4));
        rvExit.addItemDecoration(new RecycleViewDivider(getContext(), 4));
        rvIn.setAdapter(mInAdapter);
        rvEnter.setAdapter(mEnterAdapter);
        rvExit.setAdapter(mExitAdapter);

        mInAdapter.update(mInFunctionList);
        mEnterAdapter.update(mEnterFunctionList);
        mExitAdapter.update(mExitFunctionList);

    }

}
