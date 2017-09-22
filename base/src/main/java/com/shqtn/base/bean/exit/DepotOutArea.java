package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/7/20.
 */

public class DepotOutArea implements Parcelable {

    //{"rs":true,"total":1,"rows":[{"areaCode":"FJ001","areaName":"分拣区域"}]}
    private String areaName;//分拣区编号
    private String areaCode;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.areaName);
        dest.writeString(this.areaCode);
    }

    public DepotOutArea() {
    }

    protected DepotOutArea(Parcel in) {
        this.areaName = in.readString();
        this.areaCode = in.readString();
    }

    public static final Parcelable.Creator<DepotOutArea> CREATOR = new Parcelable.Creator<DepotOutArea>() {
        @Override
        public DepotOutArea createFromParcel(Parcel source) {
            return new DepotOutArea(source);
        }

        @Override
        public DepotOutArea[] newArray(int size) {
            return new DepotOutArea[size];
        }
    };
}
