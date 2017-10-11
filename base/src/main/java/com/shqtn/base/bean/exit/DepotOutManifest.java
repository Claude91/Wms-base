package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IManifest;

/**
 * Created by mrqiu on 2017/7/21.
 */

public class DepotOutManifest implements Parcelable,IManifest {
    //{"rs":true,"total":2,"rows":[{"deliveryNo":"FH20170726049"},{"deliveryNo":"FH20170726051"}]}
    private String deliveryNo;

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deliveryNo);
    }

    public DepotOutManifest() {
    }

    protected DepotOutManifest(Parcel in) {
        this.deliveryNo = in.readString();
    }

    public static final Creator<DepotOutManifest> CREATOR = new Creator<DepotOutManifest>() {
        @Override
        public DepotOutManifest createFromParcel(Parcel source) {
            return new DepotOutManifest(source);
        }

        @Override
        public DepotOutManifest[] newArray(int size) {
            return new DepotOutManifest[size];
        }
    };

    @Override
    public String getManifest() {
        return deliveryNo;
    }
}
