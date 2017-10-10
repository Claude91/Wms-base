package com.shqtn.enter.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;
import com.shqtn.base.bean.in.GoodsAdjustGoods;
import com.shqtn.enter.presenter.in.GoodsAdjustAddMoveGoodsPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class ItemGoods extends IGoods implements Parcelable {

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
