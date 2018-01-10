package com.shqtn.b.enter.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.b.enter.ui.BTakeBoxGoodsSelectActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * 装箱操作
 * 任务单详情， 货品列表
 *
 * @date 2018/1/10
 */

public class BTakeBoxGoodsListFromManifestPresenter extends AbstractListActivityPresenter {

    private CommonAdapter<TakeBoxGoods> mGoodsAdapter;

    private ResultCallback mCallback = new ResultCallback() {
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
            mGoodsList = getRows(response.getData(), TakeBoxGoods.class);
            mGoodsAdapter.update(mGoodsList);
            getView().displayLabel();
            getView().setLabelName("操作任务单号");
            getView().setLabelContent(mManifest);
            getView().setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);

            if (mGoodsList == null || mGoodsList.size() == 0) {
                getView().displayMsgDialog("当前任务单无货品");
            }

        }
    };
    private ArrayList<TakeBoxGoods> mGoodsList;
    private String mManifest;

    @Override
    public void init() {
        super.init();

        mGoodsAdapter = new CommonAdapter<TakeBoxGoods>(getAty().getContext(), null, R.layout.item_goods_take_box) {
            @Override
            public void setItemContent(ViewHolder holder, TakeBoxGoods takeBoxGoods, int position) {
                double quantity = takeBoxGoods.getQuantity();
                holder.setLabelText(R.id.item_goods_take_box_ltv_batchNo, takeBoxGoods.getBatchNo())
                        .setLabelText(R.id.item_goods_take_box_ltv_name, takeBoxGoods.getSkuName())
                        .setLabelText(R.id.item_goods_take_box_ltv_qty, String.valueOf(quantity))
                        .setLabelText(R.id.item_goods_take_box_ltv_sku, takeBoxGoods.getSkuCode())
                        .setLabelText(R.id.item_goods_take_box_ltv_std, takeBoxGoods.getStd())
                        .setLabelText(R.id.item_goods_take_box_ltv_unit, takeBoxGoods.getUnitName());
            }
        };

        mManifest = getBundle().getString(C.MANIFEST_STR);

        ListActivityController.View view = getView();
        view.setTitle("装箱货品列表");
        view.setEditTextHint("请输入货品");

        view.setScanningType(CodeCallback.TAG_GOODS);

        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_END);
        view.setAdapter(mGoodsAdapter);

        refresh();
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        if (mGoodsList == null) {
            return;
        }

        for (TakeBoxGoods takeBoxGoods : mGoodsList) {
            if (GoodsUtils.isSame(takeBoxGoods, goods)) {
                toGoodsOperateActivity(takeBoxGoods);
                return;
            }
        }

        getView().displayMsgDialog("货品不在当前任务单中,请重新扫描 ");
    }

    private void toGoodsOperateActivity(TakeBoxGoods takeBoxGoods) {
        Bundle bundle = new Bundle();

        bundle.putString(C.MANIFEST_STR, mManifest);
        bundle.putParcelable(C.OPERATE_GOODS, takeBoxGoods);

        getAty().startActivity(BTakeBoxGoodsSelectActivity.class, bundle);
    }

    @Override
    public void clickItem(int position) {
        TakeBoxGoods takeBoxGoods = mGoodsList.get(position - 1);
        toGoodsOperateActivity(takeBoxGoods);
    }

    @Override
    public void refresh() {
        if (StringUtils.isEmpty(mManifest)) {
            getView().onRefreshComplete();
            return;
        }
        queryManifestOfGoodsList(mManifest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    private void queryManifestOfGoodsList(String manifest) {
        getView().displayProgressDialog("查询中");
        ModelService.post(ApiUrl.URL_TAKE_BOX_QUERY_PRODUCT_LIST + manifest, null, mCallback);
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
