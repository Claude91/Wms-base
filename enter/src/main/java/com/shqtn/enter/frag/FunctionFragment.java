package com.shqtn.enter.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shqtn.base.BaseFragment;
import com.shqtn.base.GridAdapter;
import com.shqtn.base.bean.FunctionBean;
import com.shqtn.base.utils.RecycleViewDivider;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.R;

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
            intent.setClassName(getActivity(), bean.getClazzName());
            intent.putExtra(ListActivity.CONTROLLER, bean.getControllerName());
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
        mInFunctionList = getInFunctionList();
        mEnterFunctionList = getEnterFunctionList();
        mExitFunctionList = getExitFunctionList();
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
    public void bindView(View view) {
        rvEnter = view.findViewById(R.id.frag_home_depot_enter_recyclerView);
        rvExit = view.findViewById(R.id.frag_home_depot_exit_recyclerView);
        rvIn = view.findViewById(R.id.frag_home_depot_in_recyclerView);
    }

    /**
     * 添加库内功能
     *
     * @return
     */
    private List<FunctionBean> getInFunctionList() {
        List<FunctionBean> list = new ArrayList<>();
       /* list.add(FunctionBean.newListViewFunction("盘点", R.drawable.home_rack_down, CheckQuantityManifestController.class));
        list.add(FunctionBean.newListViewFunction("货位调整", R.drawable.home_rack_down, GoodsAdjustRackQueryController.class));
        list.add(FunctionBean.newListViewFunction("入托", R.drawable.home_pallet_manager, PalletHaveCodeInLpnQueryController.class));*/
        return list;
    }

    /**
     * 添加入库功能
     *
     * @return
     */
    private List<FunctionBean> getEnterFunctionList() {
        List<FunctionBean> list = new ArrayList<>();

   /*     list.add(FunctionBean.newListViewFunction("收货", R.drawable.home_take_delivery, TakeDeliveryManifestController.class));
        list.add(FunctionBean.newListViewFunction("上架", R.drawable.home_rack_up, RackUpGoodsListController.class));
        list.add(FunctionBean.newListViewFunction("质检", R.drawable.home_rack_up, QualityInspectionGoodsController.class));
        list.add(FunctionBean.newListViewFunction("装箱", R.drawable.home_take_box, TakeBoxQueryController.class));*/
        return list;
    }

    /**
     * 添加出库功能
     *
     * @return
     */
    private List<FunctionBean> getExitFunctionList() {
        List<FunctionBean> list = new ArrayList<>();
        /*list.add(FunctionBean.newListViewFunction("下架", R.drawable.home_rack_down, RackDownManifestController.class));
        list.add(FunctionBean.newListViewFunction("分拣", R.drawable.home_depot_out, SortingManifestController.class));
        list.add(FunctionBean.newListViewFunction("包装", R.drawable.home_depot_out, PackingManifestContoller.class));
        list.add(FunctionBean.newFunction("出库", R.drawable.home_depot_out, DepotOutActivity.class));*/
        return list;
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
