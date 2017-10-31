package com.shqtn.base.bean.enter;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;


/**
 * Created by android on 2017/7/14.
 */

public class TakeDeliveryGoods extends IGoods implements Parcelable {

    //{"rs":true,"data":[{"ikey":12792,"ihkey":10266,"asnNo":"SH20171025008","skuCode":"80000098","skuName":"AP11电动天窗总成(灰色）","batchNo":"20170617002","unitCode":"01001","unitName":"PCS","batchFlag":"Y","batchNoFlag":"0","serialFlag":"N","serialNoFlag":"0","ts":1508932788000,"batchAttrList":[],"serialNoList":[],"rquantity":328}]}

    private Long ikey;
    private Long ihkey;
    private String asnNo;
    private String skuCode;
    private String skuName;
    private String unitCode;
    private String unitName;
    private String assUnitCode;
    private String unitNamef;
    private String batchFlag;
    private String batchNoFlag;
    private String serialFlag;
    private String serialNoFlag;
    private long ts;
    private int exchRate;
    private double pQty;
    private double quantity;
    private double rquantity;//剩余数量

    public Long getIkey() {
        return ikey;
    }

    public void setIkey(Long ikey) {
        this.ikey = ikey;
    }

    public Long getIhkey() {
        return ihkey;
    }

    public void setIhkey(Long ihkey) {
        this.ihkey = ihkey;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public String getSkuCode() {

        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        setGoodsSku(skuCode);
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public String getAssUnitCode() {
        return assUnitCode;
    }

    public void setAssUnitCode(String assUnitCode) {
        this.assUnitCode = assUnitCode;
    }

    public String getUnitNamef() {
        return unitNamef;
    }

    public void setUnitNamef(String unitNamef) {
        this.unitNamef = unitNamef;
    }

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag;
    }

    public String getBatchNoFlag() {
        return batchNoFlag;
    }

    public void setBatchNoFlag(String batchNoFlag) {
        this.batchNoFlag = batchNoFlag;
    }

    public String getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(String serialFlag) {
        this.serialFlag = serialFlag;
    }

    public String getSerialNoFlag() {
        return serialNoFlag;
    }

    public void setSerialNoFlag(String serialNoFlag) {
        this.serialNoFlag = serialNoFlag;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getExchRate() {
        return exchRate;
    }

    public void setExchRate(int exchRate) {
        this.exchRate = exchRate;
    }

    public double getpQty() {
        return pQty;
    }

    public void setpQty(double pQty) {
        this.pQty = pQty;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRquantity() {
        return rquantity;
    }

    public void setRquantity(double rquantity) {
        this.rquantity = rquantity;
    }

    @Override
    public double getGoodsQty() {
        return rquantity;
    }

    public TakeDeliveryGoods() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.ikey);
        dest.writeValue(this.ihkey);
        dest.writeString(this.asnNo);
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
        dest.writeString(this.unitCode);
        dest.writeString(this.unitName);
        dest.writeString(this.assUnitCode);
        dest.writeString(this.unitNamef);
        dest.writeString(this.batchFlag);
        dest.writeString(this.batchNoFlag);
        dest.writeString(this.serialFlag);
        dest.writeString(this.serialNoFlag);
        dest.writeLong(this.ts);
        dest.writeInt(this.exchRate);
        dest.writeDouble(this.pQty);
        dest.writeDouble(this.quantity);
        dest.writeDouble(this.rquantity);
    }

    protected TakeDeliveryGoods(Parcel in) {
        this.ikey = (Long) in.readValue(Long.class.getClassLoader());
        this.ihkey = (Long) in.readValue(Long.class.getClassLoader());
        this.asnNo = in.readString();
        this.skuCode = in.readString();
        this.skuName = in.readString();
        this.unitCode = in.readString();
        this.unitName = in.readString();
        this.assUnitCode = in.readString();
        this.unitNamef = in.readString();
        this.batchFlag = in.readString();
        this.batchNoFlag = in.readString();
        this.serialFlag = in.readString();
        this.serialNoFlag = in.readString();
        this.ts = in.readLong();
        this.exchRate = in.readInt();
        this.pQty = in.readDouble();
        this.quantity = in.readDouble();
        this.rquantity = in.readDouble();
    }

    public static final Creator<TakeDeliveryGoods> CREATOR = new Creator<TakeDeliveryGoods>() {
        @Override
        public TakeDeliveryGoods createFromParcel(Parcel source) {
            return new TakeDeliveryGoods(source);
        }

        @Override
        public TakeDeliveryGoods[] newArray(int size) {
            return new TakeDeliveryGoods[size];
        }
    };
}
