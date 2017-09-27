package com.shqtn.enter.presenter;

import android.media.MediaCodecInfo;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.bean.params.RackUpGoodsListParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.ListActivityPresenterImpl;

import java.util.ArrayList;

/**
 * Created by android on 2017/9/27.
 */

public class RackUpGoodsListPresenter extends ListActivityPresenterImpl {


    private CommonAdapter<RackUpGoods> mRackUpGoodsAdapter;
    private RackUpGoodsListParams mGoodsListParams = new RackUpGoodsListParams();
    private ArrayList<RackUpGoods> mGoodsList;
    /**
     * 用于刷新列表
     */
    private ResultCallback mGoodsListCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            super.onAfter();
            getView().onRefreshComplete();
            getView().cancelProgressDialog();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {

            mGoodsList = getRows(response.getData(), RackUpGoods.class);
            mRackUpGoodsAdapter.update(mGoodsList);
            if (mGoodsList == null || mGoodsList.size() == 0) {
                getView().displayMsgDialog("当前仓库没有要上架的货品");
            }

        }
    };

    @Override
    public void init() {
        super.init();

        mRackUpGoodsAdapter = new CommonAdapter<RackUpGoods>(getAty().getContext(), null, R.layout.item_goods_or_lpn) {
            StringBuilder sb = new StringBuilder();

            @Override
            public void setItemContent(ViewHolder holder, RackUpGoods rackUpGoods, int position) {
                View goodsGroup = holder.getViewById(R.id.item_goods_or_lpn_goods_group);
                View lpnGroup = holder.getViewById(R.id.item_goods_or_lpn_lpn_group);

                if (RackUpGoods.TAG_LPN.equals(rackUpGoods.getPalletflag())) {
                    lpnGroup.setVisibility(View.VISIBLE);
                    goodsGroup.setVisibility(View.GONE);

                    holder.setLabelText(R.id.item_goods_or_lpn_lpn_no, rackUpGoods.getPalletno());

                } else {
                    lpnGroup.setVisibility(View.GONE);
                    goodsGroup.setVisibility(View.VISIBLE);

                    holder.setLabelText(R.id.item_goods_or_lpn_goods_ltv_name, rackUpGoods.getSkuName());
                    holder.setLabelText(R.id.item_goods_or_lpn_goods_ltv_sku_code, rackUpGoods.getInvcode());
                    holder.setLabelText(R.id.item_goods_or_lpn_goods_ltv_batch_no, rackUpGoods.getBatchno());
                    holder.setLabelText(R.id.item_goods_or_lpn_goods_ltv_unit, rackUpGoods.getUnitName());

                    holder.setLabelText(R.id.item_goods_or_lpn_goods_ltv_std, rackUpGoods.getPalletno());
                    double pQty = rackUpGoods.getPquantity();
                    double iQty = rackUpGoods.getIinsumquantity();
                    sb.delete(0, sb.length());
                    sb.append(iQty).append("/").append(pQty);
                    holder.setLabelText(R.id.item_goods_or_lpn_goods_ltv_plan_qty, rackUpGoods.getPalletno());

                }

            }
        };

        ListActivityController.View view = getView();
        view.setTitle("上架货品列表");
        getView().setListViewModel(PullToRefreshBase.Mode.PULL_FROM_END);
        view.setScanningType(CodeCallback.TAG_GOODS, CodeCallback.TAG_LPN);
        view.hideLabel();
        view.setEditTextHint("请输入货品编码");
        view.setAdapter(mRackUpGoodsAdapter);

        refresh();
    }

    @Override
    public void clickItem(int position) {
        RackUpGoods rackUpGoods = mGoodsList.get(position);
        if (RackUpGoods.TAG_LPN.equals(rackUpGoods.getPalletflag())) {
            toRackUpLpnOperateActivity(rackUpGoods);
        } else {
            toRackUpGoodsOperateActivity(rackUpGoods);
        }

    }

    private void toRackUpLpnOperateActivity(RackUpGoods rackUpGoods) {

    }

    private void toRackUpGoodsOperateActivity(RackUpGoods rackUpGoods) {

    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        getView().displayProgressDialog("匹配中");
        ArrayList<RackUpGoods> manifestOfGoodsSame = GoodsUtils.getManifestOfGoodsSame(mGoodsList, goods);
        if (manifestOfGoodsSame == null || manifestOfGoodsSame.size() == 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("当前仓库没有找到该货品")
                    .append("\r\n")
                    .append("sku:")
                    .append(goods.getSkuCode())
                    .append("\r\n")
                    .append("批次号:").append(goods.getBatchNo());
            getView().displayMsgDialog(sb.toString());
            getView().cancelProgressDialog();
            return;
        }
        //如果只有一个那么直接跳转
        if (manifestOfGoodsSame.size() == 1) {
            RackUpGoods rackUpGoods = manifestOfGoodsSame.get(0);
            toRackUpGoodsOperateActivity(rackUpGoods);
            return;
        }

        //如果查询到多个货品那么在页面显示用户再次点击，显示清空选项
        getView().setListViewModel(PullToRefreshBase.Mode.DISABLED);
        mRackUpGoodsAdapter.update(manifestOfGoodsSame);
        ToastUtils.show(getAty().getContext(), "查询成功");

    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        getView().displayProgressDialog("匹配中");
        for (RackUpGoods rackUpGoods : mGoodsList) {
            if (RackUpGoods.TAG_LPN.equals(rackUpGoods.getPalletflag())) {
                if (lpn.getLpnNo().equals(rackUpGoods.getPalletno())) {
                    toRackUpLpnOperateActivity(rackUpGoods);
                }
            }
        }
    }

    @Override
    public void refresh() {
        ModelService.post(ApiUrl.URL_RACK_UP_DEPOT_GOODS_LIST, mGoodsListParams, mGoodsListCallback);
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

    @Override
    public void clickClearSelect() {
        super.clickClearSelect();
        getView().setListViewModel(PullToRefreshBase.Mode.PULL_FROM_END);
        mRackUpGoodsAdapter.update(mGoodsList);

    }
}
