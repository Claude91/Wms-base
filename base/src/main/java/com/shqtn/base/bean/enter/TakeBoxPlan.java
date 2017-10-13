package com.shqtn.base.bean.enter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/7/28.
 */

public class TakeBoxPlan implements Parcelable {

    /**
     * 1.unitCode;--货品编码
     2.unitName;--货品名称
     3.qty;--装箱完成的数量
     4.unBoxedQty;--未装箱数量
     5.inBoxingQty;--装箱中数量
     6.packLevel;--包装等级
     7.conversionRate;--本包装最多装的数量(也就是包装形式相对于商亦辰的换算率)
     8.packageCode;--包装形式编码
     9.packageName;--包装形式名称
     */
    private int qty;//装箱完成数量
    private int unBoxedQty;//未装箱数量
    private int inBoxingQty;//装箱中数量
    private int conversionRate;//本包装最多装的数量(也就是包装形式相对于商亦辰的换算率)
    private String packageCode;//--包装形式编码
    private String packageName;//--包装形式名称
    private String unitCode;
    private String unitName;//包装名称
    private String packLevel;//包装等级

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getUnBoxedQty() {
        return unBoxedQty;
    }

    public void setUnBoxedQty(int unBoxedQty) {
        this.unBoxedQty = unBoxedQty;
    }

    public int getInBoxingQty() {
        return inBoxingQty;
    }

    public void setInBoxingQty(int inBoxingQty) {
        this.inBoxingQty = inBoxingQty;
    }

    public int getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(int conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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

    public String getPackLevel() {
        return packLevel;
    }

    public void setPackLevel(String packLevel) {
        this.packLevel = packLevel;
    }

    public TakeBoxPlan() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.qty);
        dest.writeInt(this.unBoxedQty);
        dest.writeInt(this.inBoxingQty);
        dest.writeInt(this.conversionRate);
        dest.writeString(this.packageCode);
        dest.writeString(this.packageName);
        dest.writeString(this.unitCode);
        dest.writeString(this.unitName);
        dest.writeString(this.packLevel);
    }

    protected TakeBoxPlan(Parcel in) {
        this.qty = in.readInt();
        this.unBoxedQty = in.readInt();
        this.inBoxingQty = in.readInt();
        this.conversionRate = in.readInt();
        this.packageCode = in.readString();
        this.packageName = in.readString();
        this.unitCode = in.readString();
        this.unitName = in.readString();
        this.packLevel = in.readString();
    }

    public static final Creator<TakeBoxPlan> CREATOR = new Creator<TakeBoxPlan>() {
        @Override
        public TakeBoxPlan createFromParcel(Parcel source) {
            return new TakeBoxPlan(source);
        }

        @Override
        public TakeBoxPlan[] newArray(int size) {
            return new TakeBoxPlan[size];
        }
    };
}
