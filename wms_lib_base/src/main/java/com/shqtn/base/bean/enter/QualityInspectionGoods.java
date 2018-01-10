package com.shqtn.base.bean.enter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/7/28.
 */

public class QualityInspectionGoods implements Parcelable {

    /**
     * private Long ikey; // 主键IKEY  传回数据
     * private BigDecimal okQty; // 合格数量   传回数据
     * private BigDecimal assOkQty; // 辅合格数量  传回数据
     * private BigDecimal badQty; // 不良数量   传回数据
     * private BigDecimal scrapQty; // 报废数量  传回数据
     * private String reasonCode; // 不良代码   传回数据，
     * private Date ts; // 时间戳    传回数据
     * <p/>
     * private BigDecimal assQuantity; // 辅数量    总数 除以 换算率 =  当前包装形式的数量
     * <p/>
     * private String skuCode; // SKU编码
     * private String skuName;//货品名称
     * private String std;//规格
     * private String batchNo; // 批次号
     * private Date recDate; // 收货日期
     * private String unitCode; // 主计量单位
     * private String unitName;//单位
     * private String assUnitCode; // 辅计量单位
     * private String assUnitName;//辅单位
     * private BigDecimal exchRate; // 换算率
     * private String locCode; // 货位编码
     * private String locName;//货位
     * private BigDecimal quantity; // 数量
     * private String supplyCode; // 供应商编码
     * private String supplyName;//供应商
     * private String customerCode; // 客户编码
     * private String customerName;//客户
     * private String ownerCode; // 货主
     * private String ownerName; //货主描述
     */
    private String batchNo;//批次号
    private int ikey;//主键IKEY
    private String ownerCode;//货主
    private String skuCode;//SKU编码
    private String skuName;//货品名称
    private String std;//规格

    private long recDate;//收货日期
    private String unitCode;//主计量单位
    private String unitName;//单位
    private int exchRate;//换算率
    private String locCode;//货位编码
    private String locName;//货位
    private double quantity;//数量
    private int okQty;//合格数量
    private int assOkQty;//辅合格数量
    private int badQty;//不良品数量
    private int scrapQty; //报废数量
    private String supplyCode;//供应商编码
    private String customerCode;//客户编码

    private String ownerName;//货主描述
    private long ts;//时间戳

    private String orderNo;//单号

    public static final Creator<QualityInspectionGoods> CREATOR = new Creator<QualityInspectionGoods>() {
        @Override
        public QualityInspectionGoods createFromParcel(Parcel in) {
            return new QualityInspectionGoods(in);
        }

        @Override
        public QualityInspectionGoods[] newArray(int size) {
            return new QualityInspectionGoods[size];
        }
    };

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getIkey() {
        return ikey;
    }

    public void setIkey(int ikey) {
        this.ikey = ikey;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
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

    public long getRecDate() {
        return recDate;
    }

    public void setRecDate(long recDate) {
        this.recDate = recDate;
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

    public int getExchRate() {
        return exchRate;
    }

    public void setExchRate(int exchRate) {
        this.exchRate = exchRate;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getOkQty() {
        return okQty;
    }

    public void setOkQty(int okQty) {
        this.okQty = okQty;
    }

    public int getAssOkQty() {
        return assOkQty;
    }

    public void setAssOkQty(int assOkQty) {
        this.assOkQty = assOkQty;
    }

    public int getBadQty() {
        return badQty;
    }

    public void setBadQty(int badQty) {
        this.badQty = badQty;
    }

    public int getScrapQty() {
        return scrapQty;
    }

    public void setScrapQty(int scrapQty) {
        this.scrapQty = scrapQty;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public QualityInspectionGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.batchNo);
        dest.writeInt(this.ikey);
        dest.writeString(this.ownerCode);
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
        dest.writeString(this.std);
        dest.writeLong(this.recDate);
        dest.writeString(this.unitCode);
        dest.writeString(this.unitName);
        dest.writeInt(this.exchRate);
        dest.writeString(this.locCode);
        dest.writeString(this.locName);
        dest.writeDouble(this.quantity);
        dest.writeInt(this.okQty);
        dest.writeInt(this.assOkQty);
        dest.writeInt(this.badQty);
        dest.writeInt(this.scrapQty);
        dest.writeString(this.supplyCode);
        dest.writeString(this.customerCode);
        dest.writeString(this.ownerName);
        dest.writeLong(this.ts);
        dest.writeString(this.orderNo);
    }

    protected QualityInspectionGoods(Parcel in) {
        this.batchNo = in.readString();
        this.ikey = in.readInt();
        this.ownerCode = in.readString();
        this.skuCode = in.readString();
        this.skuName = in.readString();
        this.std = in.readString();
        this.recDate = in.readLong();
        this.unitCode = in.readString();
        this.unitName = in.readString();
        this.exchRate = in.readInt();
        this.locCode = in.readString();
        this.locName = in.readString();
        this.quantity = in.readDouble();
        this.okQty = in.readInt();
        this.assOkQty = in.readInt();
        this.badQty = in.readInt();
        this.scrapQty = in.readInt();
        this.supplyCode = in.readString();
        this.customerCode = in.readString();
        this.ownerName = in.readString();
        this.ts = in.readLong();
        this.orderNo = in.readString();
    }

}
