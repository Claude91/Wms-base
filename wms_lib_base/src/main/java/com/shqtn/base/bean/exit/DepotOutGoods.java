package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.shqtn.base.bean.BatchAttr;
import com.shqtn.base.bean.base.IGoods;

/**
 * Created by mrqiu on 2017/7/21.
 */

public class DepotOutGoods extends IGoods implements Parcelable {

    private List<BatchAttr> batchAttrList;
    private String skuCode;//货品编码
    private String skuName;//货品名称
    private String std;//规格
    private String unitName;//单位
    private String batchNo;//批次属性
    private double quantity;//计划数量
    private double deliveryQty;//已下架数量
    private String serialNoFlag;//是否是序列号管控 判断是否是 Y

    public List<BatchAttr> getBatchAttrList() {
        return batchAttrList;
    }

    public void setBatchAttrList(List<BatchAttr> batchAttrList) {
        this.batchAttrList = batchAttrList;
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

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getDeliveryQty() {
        return deliveryQty;
    }

    public void setDeliveryQty(double deliveryQty) {
        this.deliveryQty = deliveryQty;
    }

    public String getSerialNoFlag() {
        return serialNoFlag;
    }

    public void setSerialNoFlag(String serialNoFlag) {
        this.serialNoFlag = serialNoFlag;
    }

    @Override
    public double getGoodsQty() {
        return quantity - deliveryQty;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    @Override
    public String getGoodsSku() {
        return skuCode;
    }

    public DepotOutGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.batchAttrList);
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
        dest.writeString(this.std);
        dest.writeString(this.unitName);
        dest.writeString(this.batchNo);
        dest.writeDouble(this.quantity);
        dest.writeDouble(this.deliveryQty);
        dest.writeString(this.serialNoFlag);
    }

    protected DepotOutGoods(Parcel in) {
        this.batchAttrList = in.createTypedArrayList(BatchAttr.CREATOR);
        this.skuCode = in.readString();
        this.skuName = in.readString();
        this.std = in.readString();
        this.unitName = in.readString();
        this.batchNo = in.readString();
        this.quantity = in.readDouble();
        this.deliveryQty = in.readDouble();
        this.serialNoFlag = in.readString();
    }

    public static final Creator<DepotOutGoods> CREATOR = new Creator<DepotOutGoods>() {
        @Override
        public DepotOutGoods createFromParcel(Parcel source) {
            return new DepotOutGoods(source);
        }

        @Override
        public DepotOutGoods[] newArray(int size) {
            return new DepotOutGoods[size];
        }
    };
}
