package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import com.shqtn.base.bean.BatchAttrVo;
import com.shqtn.base.bean.base.IGoods;

/**
 * @author ql
 * Created by android on 2017/7/20.
 */

public class RackDownGoods extends IGoods implements Parcelable {

    private long pickingIkey;//货架的ikey;
    private String skuCode;//货品编码
    private String unitName;//货品单位
    private String batchNo;//货品名称

    private String locationCode;//货位编码
    /**
     * 货位名称
     **/
    private String locationName;
    /**
     * 批次属性
     */
    private ArrayList<BatchAttrVo> batList;
    private String targetLocCode;
    private String whCode;//仓库编码
    private String areaCode;//区域编码
    private String skuName;//货品名称
    private String std;//规格
    private double quantity;//计划数量
    private double putOffQuantity;//累计下架数量
    private String ownerId;//货主编码

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public long getPickingIkey() {
        return pickingIkey;
    }

    public void setPickingIkey(long pickingIkey) {
        this.pickingIkey = pickingIkey;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Override
    public String getGoodsSku() {
        return skuCode;
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

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    public ArrayList<BatchAttrVo> getBatList() {
        return batList;
    }

    public void setBatList(ArrayList<BatchAttrVo> batList) {
        this.batList = batList;
    }

    public String getTargetLocCode() {
        return targetLocCode;
    }

    public void setTargetLocCode(String targetLocCode) {
        this.targetLocCode = targetLocCode;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPutOffQuantity() {
        return putOffQuantity;
    }

    public void setPutOffQuantity(double putOffQuantity) {
        this.putOffQuantity = putOffQuantity;
    }

    @Override
    public double getGoodsQty() {
        return quantity - putOffQuantity;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public RackDownGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.pickingIkey);
        dest.writeString(this.skuCode);
        dest.writeString(this.unitName);
        dest.writeString(this.batchNo);
        dest.writeString(this.locationCode);
        dest.writeString(this.locationName);
        dest.writeList(this.batList);
        dest.writeString(this.targetLocCode);
        dest.writeString(this.whCode);
        dest.writeString(this.areaCode);
        dest.writeString(this.skuName);
        dest.writeString(this.std);
        dest.writeDouble(this.quantity);
        dest.writeDouble(this.putOffQuantity);
        dest.writeString(this.ownerId);
    }

    protected RackDownGoods(Parcel in) {
        this.pickingIkey = in.readLong();
        this.skuCode = in.readString();
        this.unitName = in.readString();
        this.batchNo = in.readString();
        this.locationCode = in.readString();
        this.locationName = in.readString();
        this.batList = new ArrayList<BatchAttrVo>();
        in.readList(this.batList, BatchAttrVo.class.getClassLoader());
        this.targetLocCode = in.readString();
        this.whCode = in.readString();
        this.areaCode = in.readString();
        this.skuName = in.readString();
        this.std = in.readString();
        this.quantity = in.readDouble();
        this.putOffQuantity = in.readDouble();
        this.ownerId = in.readString();
    }

    public static final Creator<RackDownGoods> CREATOR = new Creator<RackDownGoods>() {
        @Override
        public RackDownGoods createFromParcel(Parcel source) {
            return new RackDownGoods(source);
        }

        @Override
        public RackDownGoods[] newArray(int size) {
            return new RackDownGoods[size];
        }
    };
}
