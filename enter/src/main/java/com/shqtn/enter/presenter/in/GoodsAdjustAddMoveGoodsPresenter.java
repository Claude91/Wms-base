package com.shqtn.enter.presenter.in;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.base.IGoods;
import com.shqtn.base.bean.in.GoodsAdjustGoods;
import com.shqtn.base.bean.params.GoodsAdjustGoodsParams;
import com.shqtn.base.controller.view.IAty;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/9/29.
 */

public class GoodsAdjustAddMoveGoodsPresenter extends AbstractListActivityPresenter {

    private String mOperateRackNo;
    private GoodsAdjustGoodsParams mGoodsParams = new GoodsAdjustGoodsParams();

    private ArrayList<ItemGoods> mAddMoveGoodsList = new ArrayList<>();
    private CommonAdapter<ItemGoods> mGoodsAdapter;
    private ResultCallback mGoodsDetailsCallback = new ResultCallback() {
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
    private CodeGoods mScanningGoods;
    private int REQUEST_CODE = 1;

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
                // TODO: 2017/9/29 添加转移到的目标货位
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
    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.OPERATE_LPN, lpn);
        Bundle presenter = LpnSubmitActivity.createPresenter(GoodsAdjustLpnSubmitPresenter.class);
        bundle.putAll(presenter);
        getAty().startActivity(LpnSubmitActivity.class, bundle);
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

    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }


    public static class ItemGoods extends IGoods implements Parcelable {

        public ItemGoods(GoodsAdjustGoods goods, double qty) {
            ownerId = goods.getOwnerId();
            skuCode = goods.getSkuCode();
            skuName = goods.getSkuName();
            batchNo = goods.getBatchNo();
            skuIkey = goods.getSkuIkey();
            unitName = goods.getUnitName();
            adjQty = qty;
            this.qty = goods.getAvailableQty();
        }

        //1.ownerId;--货主ID
        private String ownerId;
        //2.skuCode;--存货编码
        private String skuCode;
        //5.batchNo;--批次号
        private String batchNo;
        //6.skuIkey;--存货IKEY
        private long skuIkey;

        private double qty;
        //7.adjQty;--货位调整数量
        private double adjQty;

        private String skuName;
        private String unitName;

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        @Override
        public String getGoodsSku() {
            return skuCode;
        }

        @Override
        public String getGoodsBatchNo() {
            return batchNo;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public long getSkuIkey() {
            return skuIkey;
        }

        public void setSkuIkey(long skuIkey) {
            this.skuIkey = skuIkey;
        }

        public double getAdjQty() {
            return adjQty;
        }

        public void setAdjQty(double adjQty) {
            this.adjQty = adjQty;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ownerId);
            dest.writeString(this.skuCode);
            dest.writeString(this.batchNo);
            dest.writeLong(this.skuIkey);
            dest.writeDouble(this.qty);
            dest.writeDouble(this.adjQty);
            dest.writeString(this.skuName);
            dest.writeString(this.unitName);
        }

        protected ItemGoods(Parcel in) {
            this.ownerId = in.readString();
            this.skuCode = in.readString();
            this.batchNo = in.readString();
            this.skuIkey = in.readLong();
            this.qty = in.readDouble();
            this.adjQty = in.readDouble();
            this.skuName = in.readString();
            this.unitName = in.readString();
        }

        public static final Parcelable.Creator<ItemGoods> CREATOR = new Parcelable.Creator<ItemGoods>() {
            @Override
            public ItemGoods createFromParcel(Parcel source) {
                return new ItemGoods(source);
            }

            @Override
            public ItemGoods[] newArray(int size) {
                return new ItemGoods[size];
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getAty().closeActivity();
        }
    }
}
