package com.shqtn.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017-3-20.
 */
public class BatchAttr implements Parcelable {

    /*
     * "attrValue": "8",
       "description": "糖分",
       "skuCode": "A001000002"
     */
    private String skuCode;
    private String description;
    private String attrType;
    private String attrValue;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public BatchAttr() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.skuCode);
        dest.writeString(this.description);
        dest.writeString(this.attrType);
        dest.writeString(this.attrValue);
    }

    protected BatchAttr(Parcel in) {
        this.skuCode = in.readString();
        this.description = in.readString();
        this.attrType = in.readString();
        this.attrValue = in.readString();
    }

    public static final Creator<BatchAttr> CREATOR = new Creator<BatchAttr>() {
        @Override
        public BatchAttr createFromParcel(Parcel source) {
            return new BatchAttr(source);
        }

        @Override
        public BatchAttr[] newArray(int size) {
            return new BatchAttr[size];
        }
    };
}
