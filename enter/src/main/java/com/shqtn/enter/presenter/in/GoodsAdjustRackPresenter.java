package com.shqtn.enter.presenter.in;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.in.GoodsAdjustGoods;
import com.shqtn.base.bean.params.GoodsAdjustRackDetailsQueryParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;
import com.shqtn.enter.utils.NormalInitView;

import java.util.ArrayList;

/**
 * Created by android on 2017/9/28.
 */

public class GoodsAdjustRackPresenter extends AbstractListActivityPresenter implements View.OnClickListener {


    private CommonAdapter<GoodsAdjustGoods> mGoodsAdapter;
    private GoodsAdjustRackDetailsQueryParams mRackDetailsParams;
    private ArrayList<GoodsAdjustGoods> mRackDetailsList = new ArrayList<>();

    private ResultCallback mRackDetailsCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            super.onAfter();
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            ArrayList<GoodsAdjustGoods> rows = getRows(response.getRows(), GoodsAdjustGoods.class);

            int page = mRackDetailsParams.getPage();
            if (page == C.PAGE) {
                mRackDetailsList.clear();
            }

            if (rows == null || rows.size() == 0) {
                getView().displayMsgDialog("无更多货品");
                mGoodsAdapter.update(mRackDetailsList);
                return;
            }
            getBottomView().displayBottomGroup();

            page++;
            mRackDetailsParams.setPage(page);

            mRackDetailsList.addAll(rows);

            mGoodsAdapter.update(mRackDetailsList);
        }
    };

    @Override
    public void init() {
        super.init();
        getView().setScanningType(CodeCallback.TAG_RACK);

        getView().hideLabel();

        getView().setEditTextHint("请输入描货品");


        mGoodsAdapter = new CommonAdapter<GoodsAdjustGoods>(getAty().getContext(), null, R.layout.item_goods_adjust) {
            @Override
            public void setItemContent(ViewHolder holder, GoodsAdjustGoods goodsAdjustGoods, int position) {
                holder.setLabelText(R.id.item_goods_adjust_name, goodsAdjustGoods.getSkuName())
                        .setLabelText(R.id.item_goods_adjust_sku, goodsAdjustGoods.getSkuCode())
                        .setLabelText(R.id.item_goods_adjust_batch_no, goodsAdjustGoods.getBatchNo())
                        .setLabelText(R.id.item_goods_adjust_unit, goodsAdjustGoods.getUnitName())
                        .setLabelText(R.id.item_goods_adjust_qty, String.valueOf(goodsAdjustGoods.getAvailableQty()));
            }
        };
        getView().setAdapter(mGoodsAdapter);
        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);
        getView().setTitle("货位调整");

        getView().setLabelName("操作货位");

        getBottomView().hideLeftText();
        getBottomView().setRightText("下一步(F4)");
        getBottomView().setRightTextOnClickListener(this);

        mRackDetailsParams = new GoodsAdjustRackDetailsQueryParams();

        DepotBean depot = DepotUtils.getDepot(getAty().getContext());
        if (depot == null) {
            NormalInitView.notSelectDepot(getView());
            return;
        } else {
            mRackDetailsParams.setWhCode(depot.getWhcode());
            mRackDetailsParams.setPage(C.PAGE);
            mRackDetailsParams.setPageSize(C.PAGE_SIZE);
        }

        refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            getView().setListViewModel(PullToRefreshBase.Mode.DISABLED);
        }
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    @Override
    public void onPullUpToRefresh() {
        super.onPullUpToRefresh();
        loadMoreData();

    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public void decodeRack(CodeRack rack) {
        super.decodeRack(rack);
        mRackDetailsParams.setLocCode(rack.getRackNo());
        refresh();
    }

    @Override
    public void refresh() {
        mRackDetailsParams.setPage(C.PAGE);
        loadMoreData();
    }

    private void loadMoreData() {
        if (mRackDetailsParams.getWhCode() == null) {
            NormalInitView.notSelectDepot(getView());
        }
        if (StringUtils.isEmpty(mRackDetailsParams.getLocCode())) {
            getView().onRefreshComplete();
        } else {
            ModelService.post(ApiUrl.URL_GOODS_ADJUST_PALLET_LIST, mRackDetailsParams, mRackDetailsCallback);
        }
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.activity_list_bottom_tv_right) {
            toNextOperateAddGoodsActivity();
        }
    }

    private void toNextOperateAddGoodsActivity() {
        if (mRackDetailsParams.getLocCode() == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(C.RACK_NO, mRackDetailsParams.getLocCode());
        Class goodsAdjustAddMoveGoodsActivity = InfoLoadUtils.getInstance().getInActivityLoad().getGoodsAdjustAddMoveGoodsActivity(bundle);
        getAty().startActivity(goodsAdjustAddMoveGoodsActivity, bundle);
    }

    private void toClearAll() {
        getBottomView().hideBottomGroup();
        mRackDetailsList.clear();
        mGoodsAdapter.update(mRackDetailsList);
        getView().hideLabel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_F1) {
            //toClearAll();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_F4) {
            toNextOperateAddGoodsActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
