package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;

/**
 * Created by android on 2017/8/1.
 */

public class SortingGoods extends IGoods implements Cloneable, Parcelable {
    //{"deliveryNo":"FH20170517003"
    // ,"skuCode":"10301003",
    // "skuName":"火腿片",
    // "std":"50g*100袋/箱",
    // "unitCode":"3601",
    // "unitName":"5kg/箱",
    // "pqty":50,"sortingQuantity":0,
    // "stockQty":0
    private String skuCode; // SKU编码


    private String skuName; // 货物名称

    private String std; // 规则

    private String unitCode; // 单位编码

    private String unitName; // 单位名称

    private double pqty; // 计划发货数量

    private double sortingQuantity; // 累计分拣数量

    private double stockQty;//拣货区在库数量


    private Long deliveryHikey; // 发货单主表IKEY
    private Long deliveryIkey; // 发货单明细IKEY
    private String batchNo; // 批次号 		//返回参数
    private String whCode; // 仓库编码 		//返回参数
    private String locCode; // 货位编码 	//返回参数
    private String owerId; // 货主ID 		//返回参数
    private String deliveryNo;//

    private double writQty;//填写数量  	//返回参数

    private String lpnNo; // LPN号 			//返回参数

    @Override
    public String getGoodsSku() {
        return skuCode;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public double getPqty() {
        return pqty;
    }

    public void setPqty(double pqty) {
        this.pqty = pqty;
    }

    public double getSortingQuantity() {
        return sortingQuantity;
    }

    public void setSortingQuantity(double sortingQuantity) {
        this.sortingQuantity = sortingQuantity;
    }

    public double getStockQty() {
        return stockQty;
    }

    public void setStockQty(double stockQty) {
        this.stockQty = stockQty;
    }

    public Long getDeliveryHikey() {
        return deliveryHikey;
    }

    public void setDeliveryHikey(Long deliveryHikey) {
        this.deliveryHikey = deliveryHikey;
    }

    public Long getDeliveryIkey() {
        return deliveryIkey;
    }

    public void setDeliveryIkey(Long deliveryIkey) {
        this.deliveryIkey = deliveryIkey;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getOwerId() {
        return owerId;
    }

    public void setOwerId(String owerId) {
        this.owerId = owerId;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public double getWritQty() {
        return writQty;
    }

    public void setWritQty(double writQty) {
        this.writQty = writQty;
    }

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
        dest.writeString(this.std);
        dest.writeString(this.unitCode);
        dest.writeString(this.unitName);
        dest.writeDouble(this.pqty);
        dest.writeDouble(this.sortingQuantity);
        dest.writeDouble(this.stockQty);
        dest.writeValue(this.deliveryHikey);
        dest.writeValue(this.deliveryIkey);
        dest.writeString(this.batchNo);
        dest.writeString(this.whCode);
        dest.writeString(this.locCode);
        dest.writeString(this.owerId);
        dest.writeString(this.deliveryNo);
        dest.writeDouble(this.writQty);
        dest.writeString(this.lpnNo);
    }

    public SortingGoods() {
    }

    protected SortingGoods(Parcel in) {
        this.skuCode = in.readString();
        this.skuName = in.readString();
        this.std = in.readString();
        this.unitCode = in.readString();
        this.unitName = in.readString();
        this.pqty = in.readDouble();
        this.sortingQuantity = in.readDouble();
        this.stockQty = in.readDouble();
        this.deliveryHikey = (Long) in.readValue(Long.class.getClassLoader());
        this.deliveryIkey = (Long) in.readValue(Long.class.getClassLoader());
        this.batchNo = in.readString();
        this.whCode = in.readString();
        this.locCode = in.readString();
        this.owerId = in.readString();
        this.deliveryNo = in.readString();
        this.writQty = in.readDouble();
        this.lpnNo = in.readString();
    }

    public static final Parcelable.Creator<SortingGoods> CREATOR = new Parcelable.Creator<SortingGoods>() {
        @Override
        public SortingGoods createFromParcel(Parcel source) {
            return new SortingGoods(source);
        }

        @Override
        public SortingGoods[] newArray(int size) {
            return new SortingGoods[size];
        }
    };
}
