package com.shqtn.base.info.code;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;
import com.shqtn.base.utils.NumberUtils;

/**
 * {"skuCode":"08600043","batchNo":"","ownerCode":"","quantity":"","skuName":"三通","unitName":"PCS","std":"配￠40PPR管"}
 * Created by android on 2017/7/14.
 */

public class CodeGoods extends IGoods implements Parcelable {
    /*货品		20	skuCode
        批次属性2		110	lot2
        批次属性3		120	lot3
        批次属性4		130	lot4
        批次属性5		140	lot5
        批次属性6		150	lot6
        批次属性7		160	lot7
        批次属性8		170	lot8
        批次属性9		180	lot9
        批次属性10		190	lot10
        批次号		30	batchNo
        序列号		40	serialNo
        数量		50
        生产日期		60	mfd
        保质日期		70	sdf
        有效日期		80	exp
        货主		90	ownerCode
        批次属性1		100	lot1*/
    private String mfd;
    private String sdf;
    private String exp;
    private String ownerCode;

    private String lot1;
    private String lot2;
    private String lot3;
    private String lot4;
    private String lot5;
    private String lot6;
    private String lot7;
    private String lot8;
    private String lot9;
    private String lot10;
    private String serialNo;

    private String uniqueId;
    private String quantity;

    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 当前货品的唯一标识
     */
    private String skuCode;

    private String skuName;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public double getQuantity() {
        return NumberUtils.getDouble(quantity);
    }

    public void setQuantity(double quantity) {
        this.quantity = String.valueOf(quantity);
    }


    @Override
    public double getGoodsQty() {
        return NumberUtils.getDouble(quantity);
    }

    public String getMfd() {
        return mfd;
    }

    public void setMfd(String mfd) {
        this.mfd = mfd;
    }

    public String getSdf() {
        return sdf;
    }

    public void setSdf(String sdf) {
        this.sdf = sdf;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getLot1() {
        return lot1;
    }

    public void setLot1(String lot1) {
        this.lot1 = lot1;
    }

    public String getLot2() {
        return lot2;
    }

    public void setLot2(String lot2) {
        this.lot2 = lot2;
    }

    public String getLot3() {
        return lot3;
    }

    public void setLot3(String lot3) {
        this.lot3 = lot3;
    }

    public String getLot4() {
        return lot4;
    }

    public void setLot4(String lot4) {
        this.lot4 = lot4;
    }

    public String getLot5() {
        return lot5;
    }

    public void setLot5(String lot5) {
        this.lot5 = lot5;
    }

    public String getLot6() {
        return lot6;
    }

    public void setLot6(String lot6) {
        this.lot6 = lot6;
    }

    public String getLot7() {
        return lot7;
    }

    public void setLot7(String lot7) {
        this.lot7 = lot7;
    }

    public String getLot8() {
        return lot8;
    }

    public void setLot8(String lot8) {
        this.lot8 = lot8;
    }

    public String getLot9() {
        return lot9;
    }

    public void setLot9(String lot9) {
        this.lot9 = lot9;
    }

    public String getLot10() {
        return lot10;
    }

    public void setLot10(String lot10) {
        this.lot10 = lot10;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        setGoodsBatchNo(batchNo);
        this.batchNo = batchNo;
    }

    public String getSkuCode() {

        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        setGoodsSku(skuCode);
        this.skuCode = skuCode;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    @Override
    public String getGoodsSku() {
        return skuCode;
    }

    public CodeGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mfd);
        dest.writeString(this.sdf);
        dest.writeString(this.exp);
        dest.writeString(this.ownerCode);
        dest.writeString(this.lot1);
        dest.writeString(this.lot2);
        dest.writeString(this.lot3);
        dest.writeString(this.lot4);
        dest.writeString(this.lot5);
        dest.writeString(this.lot6);
        dest.writeString(this.lot7);
        dest.writeString(this.lot8);
        dest.writeString(this.lot9);
        dest.writeString(this.lot10);
        dest.writeString(this.serialNo);
        dest.writeString(this.uniqueId);
        dest.writeString(this.quantity);
        dest.writeString(this.batchNo);
        dest.writeString(this.skuCode);
        dest.writeString(this.skuName);
    }

    protected CodeGoods(Parcel in) {
        this.mfd = in.readString();
        this.sdf = in.readString();
        this.exp = in.readString();
        this.ownerCode = in.readString();
        this.lot1 = in.readString();
        this.lot2 = in.readString();
        this.lot3 = in.readString();
        this.lot4 = in.readString();
        this.lot5 = in.readString();
        this.lot6 = in.readString();
        this.lot7 = in.readString();
        this.lot8 = in.readString();
        this.lot9 = in.readString();
        this.lot10 = in.readString();
        this.serialNo = in.readString();
        this.uniqueId = in.readString();
        this.quantity = in.readString();
        this.batchNo = in.readString();
        this.skuCode = in.readString();
        this.skuName = in.readString();
    }

    public static final Creator<CodeGoods> CREATOR = new Creator<CodeGoods>() {
        @Override
        public CodeGoods createFromParcel(Parcel source) {
            return new CodeGoods(source);
        }

        @Override
        public CodeGoods[] newArray(int size) {
            return new CodeGoods[size];
        }
    };
}
