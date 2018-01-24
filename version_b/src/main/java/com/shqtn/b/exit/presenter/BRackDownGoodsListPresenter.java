package com.shqtn.b.exit.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.b.exit.result.BRackDownGoods;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.params.RackDownGoodsListParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.HintUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/10/10.
 */

public class BRackDownGoodsListPresenter extends AbstractListActivityPresenter {

    private String mOperateRack;
    private String mOperateManifest;
    private CommonAdapter<BRackDownGoods> mGoodsListAdapter;
    private ArrayList<BRackDownGoods> mGoodsList;
    private RackDownGoodsListParams mGoodsListParams = new RackDownGoodsListParams();
    /**
     * 用于获取货品列表
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
            mGoodsList = getRows(response.getData(), BRackDownGoods.class);
            mGoodsListAdapter.update(mGoodsList);
        }
    };

    @Override
    public void init() {
        super.init();
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);
        mOperateRack = getBundle().getString(C.RACK_NO);
        mGoodsListParams.setDocNo(mOperateManifest);
        mGoodsListParams.setLocationCode(mOperateRack);
        mGoodsListAdapter = new CommonAdapter<BRackDownGoods>(getAty().getContext(), null, R.layout.item_rack_goods) {
            @Override
            public void setItemContent(ViewHolder holder, BRackDownGoods rackDownGoods, int position) {
                View viewById = holder.getViewById(R.id.item_rack_goods_ltv_rack_code);
                String locationCode = rackDownGoods.getLocationCode();
                if (StringUtils.isEmpty(locationCode)) {
                    if (viewById.getVisibility() != View.GONE) {
                        viewById.setVisibility(View.GONE);
                    }
                } else {
                    if (viewById.getVisibility() != View.VISIBLE) {
                        viewById.setVisibility(View.VISIBLE);
                    }
                    holder.setLabelText(R.id.item_rack_goods_ltv_rack_code, locationCode);
                }


                holder.setLabelText(R.id.item_rack_goods_ltv_batchNO, rackDownGoods.getBatchNo());
                holder.setLabelText(R.id.item_rack_goods_ltv_sku, rackDownGoods.getSkuCode());
                holder.setLabelText(R.id.item_rack_goods_ltv_name, rackDownGoods.getSkuName());
                holder.setLabelText(R.id.item_rack_goods_ltv_std, rackDownGoods.getStd());
                holder.setLabelText(R.id.item_rack_goods_ltv_unit, rackDownGoods.getUnitName());
                double putOffQuantity = rackDownGoods.getPutOffQuantity();
                holder.setLabelText(R.id.item_rack_goods_ltv_over_down_qty, String.valueOf(putOffQuantity));
                double quantity = rackDownGoods.getQuantity();
                holder.setLabelText(R.id.item_rack_goods_ltv_plan_down_qty, String.valueOf(quantity));
            }
        };
        ListActivityController.View view = getView();
        view.setTitle("下架货品列表");

        view.setLabelName("下架任务单号");
        view.displayLabel();
        view.setLabelContent(mOperateManifest);

        view.setAdapter(mGoodsListAdapter);

        view.setScanningType(CodeCallback.TAG_GOODS, CodeCallback.TAG_LPN);
        view.setEditTextHint("请扫描货品或箱子");

        view.setListViewModel(PullToRefreshBase.Mode.DISABLED);
        refresh();
    }


    @Override
    public void clickItem(int position) {
        BRackDownGoods rackDownGoods = mGoodsList.get(position - 1);
        toRackDownGoodsOperateActivity(rackDownGoods, -1);
    }

    /**
     * 跳转到下架货品详细操作页面
     *
     * @param rackDownGoods 操作的货品
     * @param initQty       扫描货品的数量
     */
    private void toRackDownGoodsOperateActivity(BRackDownGoods rackDownGoods, double initQty) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.OPERATE_GOODS, rackDownGoods);
        bundle.putDouble(C.SCANNING_GOODS_QTY, initQty);

        Class rackDownGoodsOperateActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getRackDownGoodsOperateActivity(bundle);
        getAty().startActivity(rackDownGoodsOperateActivity, bundle);
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        double goodsQty = goods.getGoodsQty();
        ArrayList<BRackDownGoods> arraylist = GoodsUtils.getManifestOfGoodsSame(mGoodsList, goods);
        getView().cancelProgressDialog();
        if (arraylist == null) {
            getView().displayMsgDialog("不包含该货品");

        } else {
            /**
             * 先选择 符合当前货品数量的要求的
             * 选择数量最大的进行操作
             */
            BRackDownGoods maxQtyRackDownGoods = null;
            for (int i = 0; i < arraylist.size(); i++) {
                BRackDownGoods item = arraylist.get(i);
                if (i == 0) {
                    maxQtyRackDownGoods = item;
                }
                if (item.getGoodsQty() >= goodsQty) {
                    maxQtyRackDownGoods = item;
                    break;
                }
                if (!(maxQtyRackDownGoods.getGoodsQty() > item.getGoodsQty())) {
                    maxQtyRackDownGoods = item;
                }
            }

            toRackDownGoodsOperateActivity(maxQtyRackDownGoods, goodsQty);
        }
    }


    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        if (isSubmitLpn(lpn)) {
            Bundle bundle = new Bundle();
            Class rackDownLpnSubmitActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getRackDownLpnSubmitActivity(bundle);
            bundle.putParcelableArrayList(C.GOODS_LIST, mGoodsList);
            bundle.putString(C.OPERATE_RACK_NO, mOperateRack);
            bundle.putString(C.MANIFEST_STR, mOperateManifest);
            bundle.putParcelable(C.OPERATE_LPN, lpn);
            getAty().startActivity(rackDownLpnSubmitActivity, bundle);


        }
    }

    private boolean isSubmitLpn(CodeLpn lpn) {
        GoodsUtils.B<CodeGoods> a = GoodsUtils.isLpnAtManifsetOfGoodsList(lpn.getGoodsList(), mGoodsList);
        //判断箱子中货品 是否都在 当前列表中
        if (!a.isAt) {
            String hint = HintUtils.getNotHaveGoods(a.notAtGoods.getGoodsSku(), a.notAtGoods.getBatchNo());
            getView().displayMsgDialog(hint);
            return false;
        }
        GoodsUtils.C<BRackDownGoods, CodeGoods> b = GoodsUtils.isQuantitySatisfactory(lpn.getGoodsList(), mGoodsList);
        //判断数量是否满足
        if (!b.isSatisfactory) {
            double planQty = 0;
            for (CodeGoods planGood : b.planGoods) {
                planQty = NumberUtils.getDouble(planGood.getGoodsQty() + planQty);
            }
            double hasQty = 0;
            for (BRackDownGoods nowGood : b.nowGoods) {
                hasQty = NumberUtils.getDouble(nowGood.getQuantity() - nowGood.getPutOffQuantity() + hasQty);
            }
            BRackDownGoods operateGoods = b.nowGoods.get(0);
            String hint = HintUtils.getQuantityIsNotSatisfied(planQty, hasQty, operateGoods.getSkuName(), operateGoods.getSkuCode(), operateGoods.getBatchNo());
            getView().displayMsgDialog(hint);
            return false;
        }
        return true;
    }

    @Override
    public void refresh() {
        ModelService.post(ApiUrl.URL_RACK_DOWN_QUERY_RACK_PRODUCT_LIST, mGoodsListParams, mGoodsListCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }
}
