package com.shqtn.enter.presenter.in;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.in.GoodsAdjustGoods;
import com.shqtn.base.bean.params.GoodsAdjustGoodsParams;
import com.shqtn.base.bean.params.GoodsAdjustGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.listener.OnClickDeleteListener;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.bean.ItemGoods;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/9/29.
 */

public class GoodsAdjustAddMoveGoodsPresenter extends AbstractListActivityPresenter {

    public String mOperateRackNo;
    public GoodsAdjustGoodsParams mGoodsParams = new GoodsAdjustGoodsParams();

    public ArrayList<ItemGoods> mAddMoveGoodsList = new ArrayList<>();
    public CommonAdapter<ItemGoods> mGoodsAdapter;
    public ResultCallback mGoodsDetailsCallback = new ResultCallback() {
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
            ArrayList<GoodsAdjustGoods> rows = getRows(response.getRows(), GoodsAdjustGoods.class);
            if (rows == null || rows.size() == 0) {
                getView().displayMsgDialog("货品不存在");
                return;
            }

            GoodsAdjustGoods goodsAdjustGoods = rows.get(0);
            double goodsQty = mScanningGoods.getGoodsQty();
            if (goodsQty <= 0) {
                goodsQty = 1;
            }
            for (ItemGoods adjustGoods : mAddMoveGoodsList) {
                if (GoodsUtils.isSame(adjustGoods, goodsAdjustGoods)) {

                    double addTotalQty = adjustGoods.getAdjQty();
                    addTotalQty = NumberUtils.getDouble(addTotalQty + goodsQty);
                    adjustGoods.setAdjQty(addTotalQty);

                    mGoodsAdapter.update(mAddMoveGoodsList);
                    return;
                }
            }

            ItemGoods itemGoods = new ItemGoods(goodsAdjustGoods, goodsQty);
            mAddMoveGoodsList.add(itemGoods);
            mGoodsAdapter.update(mAddMoveGoodsList);

        }
    };
    public CodeGoods mScanningGoods;
    public int REQUEST_CODE = 1;

    public ItemGoods mOperateQtyGoods;
    EditQuantityDialog.OnResultListener resultListener = new EditQuantityDialog.OnResultListener() {
        @Override
        public void clickOk(double quantity) {
            mOperateQtyGoods.setAdjQty(quantity);
            mGoodsAdapter.update(mAddMoveGoodsList);
            getView().cancelEditQty();
        }

        @Override
        public void clickCancel() {
            getView().cancelEditQty();
        }
    };

    @Override
    public void init() {
        super.init();
        mOperateRackNo = getBundle().getString(C.RACK_NO);

        mGoodsParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        mGoodsParams.setLocCode(mOperateRackNo);
        getView().displayLabel();
        getView().setLabelName("操作货位");
        getView().setLabelContent(mOperateRackNo);


        mGoodsAdapter = new CommonAdapter<ItemGoods>(getAty().getContext(), null, R.layout.item_goods_adjust) {
            @Override
            public void setItemContent(ViewHolder holder, ItemGoods itemGoods, int position) {
                holder.setLabelText(R.id.item_goods_adjust_name, itemGoods.getSkuName())
                        .setLabelText(R.id.item_goods_adjust_sku, itemGoods.getSkuCode())
                        .setLabelText(R.id.item_goods_adjust_batch_no, itemGoods.getBatchNo())
                        .setLabelText(R.id.item_goods_adjust_unit, itemGoods.getUnitName())
                        .setLabelText(R.id.item_goods_adjust_qty, String.valueOf(itemGoods.getAdjQty()));
            }
        };
        getView().setAdapter(mGoodsAdapter);

        getView().setEditTextHint("货品或托盘");

        getBottomView().displayBottomGroup();
        getBottomView().setRightText("下一步(F4)");
        getBottomView().setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                锁定货位上货品 ，锁定成功后再进行下一步
                 */
                toLockMoveGoods(mAddMoveGoodsList);

            }
        });

        getBottomView().setLeftText("清空内容");
        getBottomView().setLeftTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddMoveGoodsList.clear();
                mGoodsAdapter.update(mAddMoveGoodsList);
            }
        });
        getView().onRefreshComplete();
        getView().setListViewModel(PullToRefreshBase.Mode.DISABLED);

        getView().displayEditQtyDelBtn(new OnClickDeleteListener() {
            @Override
            public void clickDelete() {
                mAddMoveGoodsList.remove(mOperateQtyGoods);
            }
        });

    }

    /**
     * 去锁定货品
     *
     * @param addMoveGoodsList
     */
    public void toLockMoveGoods(ArrayList<ItemGoods> addMoveGoodsList) {
        getView().displayProgressDialog("锁定货品中");
        ArrayList<GoodsAdjustGoodsSubmitParams.SubmitMovePro> list = new ArrayList<>();
        for (int m = 0; m < addMoveGoodsList.size(); m++) {
            ItemGoods bean = addMoveGoodsList.get(m);
            GoodsAdjustGoodsSubmitParams.SubmitMovePro submitBean = new GoodsAdjustGoodsSubmitParams.SubmitMovePro();
            //  5.batchNo;--批次号
            submitBean.setBatchNo(bean.getBatchNo());
            //2.skuCode;--存货编码
            submitBean.setSkuCode(bean.getSkuCode());
            // 4.locCode;--货位编码
            submitBean.setLocCode(mOperateRackNo);
            //  6.skuIkey;--存货IKEY
            submitBean.setSkuIkey(bean.getSkuIkey());
            //1.ownerId;--货主ID
            submitBean.setOwnerId(bean.getOwnerId());
            // 3.whCode;--仓库编码
            submitBean.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
            //7.adjQty;--货位调整数量
            submitBean.setAdjQty(bean.getAdjQty());
            list.add(submitBean);
        }
        GoodsAdjustGoodsSubmitParams httpBean = new GoodsAdjustGoodsSubmitParams();
        httpBean.setStockposSearchList(list);

        ModelService.post(ApiUrl.URL_GOODS_ADJUST_LOCK_RACK, httpBean, new ResultCallback() {
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
                toInputTargetRackActivity();
            }
        });
    }

    public void toInputTargetRackActivity() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(C.GOODS_LIST, mAddMoveGoodsList);
        bundle.putString(C.RACK_NO, mOperateRackNo);
        Class aty = InfoLoadUtils.getInstance().getInActivityLoad().getGoodsAdjustTargetRackActivity(bundle);
        getAty().startActivity(aty, bundle, REQUEST_CODE);
    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.OPERATE_LPN, lpn);
        bundle.putString(C.RACK_NO, mOperateRackNo);
        Class aty = InfoLoadUtils.getInstance().getInActivityLoad().getGoodsAdjustLpnSubmitActivity(bundle);
        getAty().startActivity(aty, bundle);
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        mScanningGoods = goods;
        mGoodsParams.setBatchNo(goods.getBatchNo());
        mGoodsParams.setSkuCode(goods.getSkuCode());
        ModelService.post(ApiUrl.URL_GOODS_ADJUST_QUERY_PRO, mGoodsParams, mGoodsDetailsCallback);
    }

    @Override
    public void clickItem(int position) {
        mOperateQtyGoods = mAddMoveGoodsList.get(position - 1);


        getView().displayEditQty(resultListener);
    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getAty().closeActivity();
            getAty().setResult(Activity.RESULT_OK);
        }
    }
}
