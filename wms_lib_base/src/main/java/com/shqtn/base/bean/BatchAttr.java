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
    private String attrValue;
    private String description;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attrValue);
        dest.writeString(this.description);
    }

    public BatchAttr() {
    }

    protected BatchAttr(Parcel in) {
        this.attrValue = in.readString();
        this.description = in.readString();
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
