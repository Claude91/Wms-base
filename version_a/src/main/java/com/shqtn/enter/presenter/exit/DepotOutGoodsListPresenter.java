package com.shqtn.enter.presenter.exit;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.DepotOutGoods;
import com.shqtn.base.bean.exit.DepotOutManifest;
import com.shqtn.base.bean.params.DepotOutGoodsListParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/10/11.
 */

public class DepotOutGoodsListPresenter extends AbstractListActivityPresenter {
    private ArrayList<DepotOutGoods> mGoodsList = new ArrayList<>();
    private CommonAdapter<DepotOutGoods> mGoodsAdapter;

    private String mOperateArea;
    private DepotOutManifest mOperateManifest;

    private DepotOutGoodsListParams mGoodsListParams;

    private ResultCallback mGoodsListCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {

            int page = mGoodsListParams.getPage();
            ArrayList<DepotOutGoods> l = getRows(response.getData(), DepotOutGoods.class);
            if (page == C.PAGE) {
                mGoodsList.clear();
            }
            if (l == null || l.size() == 0) {
                ToastUtils.show(getAty().getContext(), "没有更多数据");
                mGoodsAdapter.update(mGoodsList);
                return;
            }
            mGoodsList.addAll(l);
            mGoodsAdapter.update(mGoodsList);

            page++;
            mGoodsListParams.setPage(page);

        }
    };

    @Override
    public void init() {
        super.init();
        mOperateArea = getBundle().getString(C.AREA_CODE);
        mOperateManifest = getBundle().getParcelable(C.MANIFEST_BEAN);

        mGoodsAdapter = new CommonAdapter<DepotOutGoods>(getAty().getContext(), null, R.layout.item_goods_depot_out) {
            @Override
            public void setItemContent(ViewHolder holder, DepotOutGoods depotOutGoods, int position) {
                holder.setLabelText(R.id.item_goods_depot_out_ltv_batch_no, depotOutGoods.getBatchNo());
                holder.setLabelText(R.id.item_goods_depot_out_ltv_sku, depotOutGoods.getSkuCode());
                holder.setLabelText(R.id.item_goods_depot_out_ltv_name, depotOutGoods.getSkuName());
                holder.setLabelText(R.id.item_goods_depot_out_ltv_over_qty, String.valueOf(depotOutGoods.getDeliveryQty()));
                holder.setLabelText(R.id.item_goods_depot_out_ltv_plan_qty, String.valueOf(depotOutGoods.getQuantity()));
                holder.setLabelText(R.id.item_goods_depot_out_ltv_std, depotOutGoods.getStd());
                holder.setLabelText(R.id.item_goods_depot_out_ltv_unit, depotOutGoods.getUnitName());

            }
        };
        getView().setAdapter(mGoodsAdapter);
        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);

        getView().setTitle("出库货品列表");
        getView().setEditTextHint("请填写货品");

        getView().setScanningType(CodeCallback.TAG_GOODS);

        mGoodsListParams = new DepotOutGoodsListParams();
        mGoodsListParams.setPage(C.PAGE);
        mGoodsListParams.setPageSize(C.PAGE_SIZE);
        mGoodsListParams.setAreaCode(mOperateArea);
        mGoodsListParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        mGoodsListParams.setDeliveryNo(mOperateManifest.getDeliveryNo());

        refresh();

    }


    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        if (hasGoods(goods)) {
            double qty = getGoodsQty(goods);
            ArrayList<DepotOutGoods> sameGoods = GoodsUtils.getManifestOfGoodsSame(mGoodsList, goods);
            for (DepotOutGoods sameGood : sameGoods) {
                if (0 > sameGood.getGoodsQty()) {
                    toGoodsDetailsOperateActivity(sameGood, qty);
                    return;
                }
            }

            getView().displayMsgDialog("当前货品在任务单中已经出库完成");
        }
    }

    private void toGoodsDetailsOperateActivity(DepotOutGoods sameGood, double qty) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.OPERATE_GOODS, sameGood);
        bundle.putDouble(C.SCANNING_GOODS_QTY, qty);

        Class activity = InfoLoadUtils.getInstance().getExitActivityLoad().getDepotOutGoodsOperateActivity(bundle);
        getAty().startActivity(activity, bundle);
    }

    private boolean hasGoods(CodeGoods goods) {
        if (goods == null) {
            getView().displayMsgDialog("错误货品");
            return false;
        }
        ArrayList<DepotOutGoods> sameList = GoodsUtils.getManifestOfGoodsSame(mGoodsList, goods);
        if (sameList == null || sameList.size() <= 0) {
            getView().displayMsgDialog("货品不存在当前任务单中或已经出库完成");
            return false;
        }
        return true;
    }

    private double getGoodsQty(CodeGoods goods) {
        return goods.getGoodsQty();
    }

    @Override
    public void clickItem(int position) {
        DepotOutGoods clickItem = mGoodsList.get(position - 1);
        toGoodsDetailsOperateActivity(clickItem, -1);
    }

    @Override
    public void refresh() {
        mGoodsListParams.setPage(C.PAGE);
        loadMoreData();
    }

    @Override
    public void onPullUpToRefresh() {
        super.onPullUpToRefresh();
        loadMoreData();
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }

    public void loadMoreData() {
        ModelService.post(ApiUrl.URL_DEPOT_OUT_GOODS_LIST, mGoodsListParams, mGoodsListCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
