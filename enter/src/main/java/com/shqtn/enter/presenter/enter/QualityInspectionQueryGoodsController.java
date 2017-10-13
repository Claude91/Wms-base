package com.shqtn.enter.presenter.enter;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
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
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * 质检，扫描货品 获得货品列表，点击item跳转质检详情页面
 * Created by android on 2017/10/10.
 */

public class QualityInspectionQueryGoodsController extends AbstractListActivityPresenter {

    private CommonAdapter<QualityInspectionGoods> mGoodsAdapter;
    private ArrayList<QualityInspectionGoods> mScanningGoodsList;

    private QualityInspectionGoodsParams mScanningGoodsParams;
    private ResultCallback mGoodsListCallback;


    @Override
    public void init() {
        super.init();


        ListActivityController.View view = getView();
        view.setEditTextHint("输入货品编码");
        view.setScanningType(CodeCallback.TAG_GOODS);
        view.setListViewModel(PullToRefreshBase.Mode.DISABLED);
        view.setTitle("质检");

        mScanningGoodsParams = new QualityInspectionGoodsParams();

        mGoodsAdapter = new CommonAdapter<QualityInspectionGoods>(getAty().getContext(), null, R.layout.item_goods_adjust) {
            @Override
            public void setItemContent(ViewHolder holder, QualityInspectionGoods qualityInspectionGoods, int position) {
                double quantity = qualityInspectionGoods.getQuantity();
                holder.setLabelText(R.id.item_goods_adjust_qty, String.valueOf(quantity))
                        .setLabelText(R.id.item_goods_adjust_unit, qualityInspectionGoods.getUnitName())
                        .setLabelText(R.id.item_goods_adjust_batch_no, qualityInspectionGoods.getBatchNo())
                        .setLabelText(R.id.item_goods_adjust_sku, qualityInspectionGoods.getSkuName());
            }
        };
        view.setAdapter(mGoodsAdapter);


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
                    getView().displayMsgDialog(msg);
                }

                @Override
                public void onSuccess(ResultBean response) {
                    mScanningGoodsList = getRows(response.getData(), QualityInspectionGoods.class);
                    mGoodsAdapter.update(mScanningGoodsList);
                }
            };
        }
        ModelService.post(ApiUrl.URL_QUALITY_CHECKING_QUERY_DETAILS, mScanningGoodsParams, mGoodsListCallback);
    }

    @Override
    public void clickItem(int position) {
        QualityInspectionGoods qualityInspectionGoods = mScanningGoodsList.get(position - 1);
        toQualityInspectionOperate(qualityInspectionGoods);
    }

    /**
     * 跳转到货品详情页面
     *
     * @param qualityInspectionGoods
     */
    private void toQualityInspectionOperate(QualityInspectionGoods qualityInspectionGoods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.OPERATE_GOODS, qualityInspectionGoods);
        getAty().startActivity(InfoLoadUtils.getInstance().getEnterActivityLoad().getQualityInspectionGoodsOperateActivity(bundle), bundle);
    }

    @Override
    public void refresh() {
        getView().onRefreshComplete();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }
}
