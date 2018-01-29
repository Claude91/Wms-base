package com.shqtn.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/7/20.
 */

public class BatchAttrVo implements Parcelable {
    private String attrType; // 批次属性类型
    private String attrValue; // 批次属性值

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attrType);
        dest.writeString(this.attrValue);
    }

    public BatchAttrVo() {
    }

    protected BatchAttrVo(Parcel in) {
        this.attrType = in.readString();
        this.attrValue = in.readString();
    }

    public static final Parcelable.Creator<BatchAttrVo> CREATOR = new Parcelable.Creator<BatchAttrVo>() {
        @Override
        public BatchAttrVo createFromParcel(Parcel source) {
            return new BatchAttrVo(source);
        }

        @Override
        public BatchAttrVo[] newArray(int size) {
            return new BatchAttrVo[size];
        }
    };
}
