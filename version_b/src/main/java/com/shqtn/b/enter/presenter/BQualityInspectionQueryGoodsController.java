package com.shqtn.b.enter.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.b.enter.result.BQualityInspectionGoods;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.QualityInspectionGoods;
import com.shqtn.base.bean.params.QualityInspectionGoodsParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * 质检，扫描货品 获得货品列表，点击item跳转质检详情页面
 * Created by android on 2017/10/10.
 */

public class BQualityInspectionQueryGoodsController extends AbstractListActivityPresenter {

    private CommonAdapter<BQualityInspectionGoods> mGoodsAdapter;
    private ArrayList<BQualityInspectionGoods> mScanningGoodsList;

    private QualityInspectionGoodsParams mScanningGoodsParams;
    private ResultCallback mGoodsListCallback;

    private boolean isShowDialog = true;

    @Override
    public void init() {
        super.init();


        ListActivityController.View view = getView();
        view.setEditTextHint("输入货品编码");
        view.setScanningType(CodeCallback.TAG_GOODS);
        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);
        view.setTitle("质检");

        mScanningGoodsParams = new QualityInspectionGoodsParams();

        mGoodsAdapter = new CommonAdapter<BQualityInspectionGoods>(getAty().getContext(), null, R.layout.item_goods_adjust) {
            @Override
            public void setItemContent(ViewHolder holder, BQualityInspectionGoods qualityInspectionGoods, int position) {
                double quantity = qualityInspectionGoods.getQuantity();
                String skuCode = qualityInspectionGoods.getSkuCode();
                String skuName = qualityInspectionGoods.getSkuName();
                String batchNo = qualityInspectionGoods.getBatchNo();
                String unitName = qualityInspectionGoods.getUnitName();
                if (skuCode == null) {
                    skuCode = "";
                }
                if (skuName == null) {
                    skuName = "";
                }
                if (batchNo == null) {
                    batchNo = "";
                }
                if (unitName == null) {
                    unitName = "";
                }
                holder.setLabelText(R.id.item_goods_adjust_qty, String.valueOf(quantity))
                        .setLabelText(R.id.item_goods_adjust_unit, unitName)
                        .setLabelText(R.id.item_goods_adjust_batch_no, batchNo)
                        .setLabelText(R.id.item_goods_adjust_name, skuName)
                        .setLabelText(R.id.item_goods_adjust_sku, skuCode);
            }
        };
        view.setAdapter(mGoodsAdapter);
        view.onRefreshComplete();

    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        mScanningGoodsParams.setBatchNo(goods.getBatchNo());
        mScanningGoodsParams.setSkuCode(goods.getSkuCode());
        if (mGoodsListCallback == null) {
            mGoodsListCallback = new ResultCallback() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    getView().cancelProgressDialog();
                }

                @Override
                public void onFailed(String msg) {
                    mScanningGoodsList = null;
                    mGoodsAdapter.update(mScanningGoodsList);
                    if (isShowDialog) {
                        getView().displayMsgDialog(msg);
                    } else {
                        isShowDialog = true;
                    }

                }

                @Override
                public void onSuccess(ResultBean response) {
                    mScanningGoodsList = getRows(response.getData(), BQualityInspectionGoods.class);
                    mGoodsAdapter.update(mScanningGoodsList);
                }
            };
        }
        refresh();
    }

    @Override
    public void clickItem(int position) {
        BQualityInspectionGoods qualityInspectionGoods = mScanningGoodsList.get(position - 1);
        toQualityInspectionOperate(qualityInspectionGoods);
    }

    /**
     * 跳转到货品详情页面
     *
     * @param qualityInspectionGoods
     */
    private void toQualityInspectionOperate(BQualityInspectionGoods qualityInspectionGoods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.OPERATE_GOODS, qualityInspectionGoods);
        getAty().startActivity(InfoLoadUtils.getInstance().getEnterActivityLoad().getQualityInspectionGoodsOperateActivity(bundle), bundle, 2);
    }


    @Override
    public void refresh() {
        getView().onRefreshComplete();
        String skuCode = mScanningGoodsParams.getSkuCode();
        if (StringUtils.isEmpty(skuCode)) {
            return;
        }
        ModelService.post(ApiUrl.URL_QUALITY_CHECKING_QUERY_DETAILS, mScanningGoodsParams, mGoodsListCallback);
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                isShowDialog = false;
            }
        }
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
