package com.shqtn.base.bean.in;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;

/**
 * Created by android on 2017/8/7.
 */

public class GoodsAdjustGoods extends IGoods implements Parcelable {
    //{"ownerId":"HDL","skuCode":"YT01020003","skuName":"45um钢化玻璃","batchNo":"LOT20170407025","unitName":"公斤","availableQty":20,"ownerName":"好得睐"}
    /**
     * ownerId : HDL
     * skuCode : YT01020003
     * skuName : 45um钢化玻璃
     * batchNo : LOT20170407025
     * unitName : 公斤
     * availableQty : 20
     * ownerName : 好得睐
     */

    private String ownerId;//货主ID
    private String skuCode;//存货编码
    private String skuName;//存货名称
    private String batchNo;//批次号
    private String unitName;//单位名称
    private double availableQty;//库存可用数量
    private String ownerName;//货主ID
    private long skuIkey;

    public long getSkuIkey() {
        return skuIkey;
    }

    public void setSkuIkey(long skuIkey) {
        this.skuIkey = skuIkey;
    }


    @Override
    public String getGoodsSku() {
        return skuCode;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    @Override
    public double getGoodsQty() {
        return availableQty;
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(double availableQty) {
        this.availableQty = availableQty;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ownerId);
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
        dest.writeString(this.batchNo);
        dest.writeString(this.unitName);
        dest.writeDouble(this.availableQty);
        dest.writeString(this.ownerName);
        dest.writeLong(this.skuIkey);
    }

    public GoodsAdjustGoods() {
    }

    protected GoodsAdjustGoods(Parcel in) {
        this.ownerId = in.readString();
        this.skuCode = in.readString();
        this.skuName = in.readString();
        this.batchNo = in.readString();
        this.unitName = in.readString();
        this.availableQty = in.readDouble();
        this.ownerName = in.readString();
        this.skuIkey = in.readLong();
    }

    public static final Parcelable.Creator<GoodsAdjustGoods> CREATOR = new Parcelable.Creator<GoodsAdjustGoods>() {
        @Override
        public GoodsAdjustGoods createFromParcel(Parcel source) {
            return new GoodsAdjustGoods(source);
        }

        @Override
        public GoodsAdjustGoods[] newArray(int size) {
            return new GoodsAdjustGoods[size];
        }
    };
}
