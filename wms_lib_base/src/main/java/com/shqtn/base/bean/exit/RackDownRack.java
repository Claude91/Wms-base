package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/7/20.
 */

public class RackDownRack implements Parcelable {
    private String locationCode;//货位编码
    private String posno;//货架号
    private String floors;//层
    private String colums;//列
    private double totalLocNum;//货架上需要下架的总数量
    private String locationName;//货位名称


    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getPosno() {
        return posno;
    }

    public void setPosno(String posno) {
        this.posno = posno;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getColums() {
        return colums;
    }

    public void setColums(String colums) {
        this.colums = colums;
    }

    public double getTotalLocNum() {
        return totalLocNum;
    }

    public void setTotalLocNum(double totalLocNum) {
        this.totalLocNum = totalLocNum;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.locationCode);
        dest.writeString(this.posno);
        dest.writeString(this.floors);
        dest.writeString(this.colums);
        dest.writeDouble(this.totalLocNum);
        dest.writeString(this.locationName);
    }

    public RackDownRack() {
    }

    protected RackDownRack(Parcel in) {
        this.locationCode = in.readString();
        this.posno = in.readString();
        this.floors = in.readString();
        this.colums = in.readString();
        this.totalLocNum = in.readDouble();
        this.locationName = in.readString();
    }

    public static final Parcelable.Creator<RackDownRack> CREATOR = new Parcelable.Creator<RackDownRack>() {
        @Override
        public RackDownRack createFromParcel(Parcel source) {
            return new RackDownRack(source);
        }

        @Override
        public RackDownRack[] newArray(int size) {
            return new RackDownRack[size];
        }
    };
}
