package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IManifest;

/**
 * Created by android on 2017/7/31.
 */

public class SortingManifest implements IManifest, Parcelable {
    private String deliveryNo;
    private String owerName;//货主名称

    private String customerName;//客户名称

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deliveryNo);
        dest.writeString(this.owerName);
        dest.writeString(this.customerName);
    }

    public SortingManifest() {
    }

    protected SortingManifest(Parcel in) {
        this.deliveryNo = in.readString();
        this.owerName = in.readString();
        this.customerName = in.readString();
    }

    public static final Parcelable.Creator<SortingManifest> CREATOR = new Parcelable.Creator<SortingManifest>() {
        @Override
        public SortingManifest createFromParcel(Parcel source) {
            return new SortingManifest(source);
        }

        @Override
        public SortingManifest[] newArray(int size) {
            return new SortingManifest[size];
        }
    };

    @Override
    public String getManifest() {
        return deliveryNo;
    }
}
