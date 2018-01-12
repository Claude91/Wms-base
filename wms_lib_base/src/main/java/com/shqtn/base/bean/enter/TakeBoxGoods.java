package com.shqtn.base.bean.enter;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;


/**
 * Created by android on 2017/7/19.
 */

public class TakeBoxGoods extends IGoods implements Parcelable {
    /**
     * 1.skuCode;--货品编码
     * 2.skuName;--货品名称
     * 3.quantity;--订单数量
     * 4.unitCode;--单位编码
     * 5.unitName;--单位名称
     * 6.batchNo;--批次号
     * 7.ikey;--到货单明细Ikey
     * 8.std;--规格
     */
    private String skuCode; //货物SKU编码
    private String skuName;//货品名称
    private double quantity; //总计打包数量
    private String unitCode; //货物基础单位编码
    private String unitName;
    private long ikey;
    private String std;
    private String batchNo;//批次号
    private String serialFlag;//是否是序列号管控 Y

    public String getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(String serialFlag) {
        this.serialFlag = serialFlag;
    }

    @Override
    public double getGoodsQty() {
        return quantity;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    @Override
    public String getGoodsSku() {
        return skuCode;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public TakeBoxGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
        dest.writeDouble(this.quantity);
        dest.writeString(this.unitCode);
        dest.writeString(this.unitName);
        dest.writeLong(this.ikey);
        dest.writeString(this.std);
        dest.writeString(this.batchNo);
        dest.writeString(this.serialFlag);
    }

    protected TakeBoxGoods(Parcel in) {
        this.skuCode = in.readString();
        this.skuName = in.readString();
        this.quantity = in.readDouble();
        this.unitCode = in.readString();
        this.unitName = in.readString();
        this.ikey = in.readLong();
        this.std = in.readString();
        this.batchNo = in.readString();
        this.serialFlag = in.readString();
    }

    public static final Creator<TakeBoxGoods> CREATOR = new Creator<TakeBoxGoods>() {
        @Override
        public TakeBoxGoods createFromParcel(Parcel source) {
            return new TakeBoxGoods(source);
        }

        @Override
        public TakeBoxGoods[] newArray(int size) {
            return new TakeBoxGoods[size];
        }
    };
}
