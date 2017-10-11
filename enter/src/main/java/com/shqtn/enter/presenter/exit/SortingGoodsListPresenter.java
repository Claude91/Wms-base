package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.SortingGoods;
import com.shqtn.base.bean.params.SortingManifestDetailsParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/10/11.
 */

public class SortingGoodsListPresenter extends AbstractListActivityPresenter {


    private String mOperateManifest;
    private CommonAdapter<SortingGoods> mGoodsAdapter;

    private ArrayList<SortingGoods> mGoodsList;
    private SortingManifestDetailsParams mManifestDetailsParams = new SortingManifestDetailsParams();

    private ResultCallback mGoodsListCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            super.onAfter();
            getView().cancelProgressDialog();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            ArrayList<SortingGoods> rows = getRows(response.getRows(), SortingGoods.class);
            mGoodsList = rows;
            mGoodsAdapter.update(mGoodsList);
        }
    };

    @Override
    public void init() {
        super.init();
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);

        mManifestDetailsParams.setDeliveryNo(mOperateManifest);
        mManifestDetailsParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());

        mGoodsAdapter = new CommonAdapter<SortingGoods>(getAty().getContext(), null, R.layout.item_goods_sorting) {
            @Override
            public void setItemContent(ViewHolder holder, SortingGoods sortingGoods, int position) {
                double sortingQuantity = sortingGoods.getSortingQuantity();
                double pqty = sortingGoods.getPqty();
                double stockQty = sortingGoods.getStockQty();
                holder.setLabelText(R.id.item_goods_sorting_ltv_sku, sortingGoods.getSkuCode())
                        .setLabelText(R.id.item_goods_sorting_ltv_name, sortingGoods.getSkuName())
                        .setLabelText(R.id.item_goods_sorting_ltv_batch_no, sortingGoods.getBatchNo())
                        .setLabelText(R.id.item_goods_sorting_ltv_std, sortingGoods.getStd())
                        .setLabelText(R.id.item_goods_sorting_ltv_over_qty, String.valueOf(sortingQuantity))
                        .setLabelText(R.id.item_goods_sorting_ltv_plan_qty, String.valueOf(pqty))
                        .setLabelText(R.id.item_goods_sorting_ltv_depot_have_qty, String.valueOf(stockQty));
            }
        };

        ListActivityController.View view = getView();
        view.setTitle("分拣货品列表");

        view.setLabelName("任务单据号");
        view.setLabelContent(mOperateManifest);
        view.displayLabel();
        view.setEditTextHint("请输入货品");

        view.setAdapter(mGoodsAdapter);
        view.setListViewModel(PullToRefreshBase.Mode.DISABLED);

        view.setScanningType(CodeCallback.TAG_GOODS);
        refresh();
    }


    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        for (SortingGoods sortingGoods : mGoodsList) {
            if (GoodsUtils.isSame(sortingGoods, goods)) {
                toGoodsOperate(sortingGoods, goods.getGoodsQty());
                return;
            }
        }

        getView().displayMsgDialog("扫描货品不存在当前任务单,请重新扫描");
    }

    private void toGoodsOperate(SortingGoods sortingGoods, double goodsQty) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.OPERATE_GOODS, sortingGoods);
        bundle.putDouble(C.SCANNING_GOODS_QTY, goodsQty);
        Class activity = InfoLoadUtils.getInstance().getExitActivityLoad().getSortingGoodsOperateActivity(bundle);
        getAty().startActivity(activity, bundle);
    }

    @Override
    public void clickItem(int position) {
        SortingGoods sortingGoods = mGoodsList.get(position - 1);
        toGoodsOperate(sortingGoods, 0);
    }

    @Override
    public void refresh() {
        ModelService.post(ApiUrl.URL_SORTING_GET_MANIFEST_DETAILS, mManifestDetailsParams, mGoodsListCallback);
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
