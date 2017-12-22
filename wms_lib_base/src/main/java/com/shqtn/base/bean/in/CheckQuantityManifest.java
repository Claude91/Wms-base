package com.shqtn.base.bean.in;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/8/3.
 */

public class CheckQuantityManifest implements Parcelable {

    /**
     * 1.docNo;--盘点单号
     2.status;--盘点单状态(1:开盘)
     3.period;--盘点阶段(0:初盘;1:复盘;)
     4.checkType;--盘点类型(0:按仓库;1:按货位;2:按批次;3:按存货大类;4:按存货SKU;5:按货主)
     5.hikey;--盘点单表头IKEY
     */
    private String status;
    private String period;
    private String checkType;
    private long hikey;
    private String docNo;

    private long docDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public long getHikey() {
        return hikey;
    }

    public void setHikey(long hikey) {
        this.hikey = hikey;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public long getDocDate() {
        return docDate;
    }

    public void setDocDate(long docDate) {
        this.docDate = docDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.period);
        dest.writeString(this.checkType);
        dest.writeLong(this.hikey);
        dest.writeString(this.docNo);
        dest.writeLong(this.docDate);
    }

    public CheckQuantityManifest() {
    }

    protected CheckQuantityManifest(Parcel in) {
        this.status = in.readString();
        this.period = in.readString();
        this.checkType = in.readString();
        this.hikey = in.readLong();
        this.docNo = in.readString();
        this.docDate = in.readLong();
    }

    public static final Parcelable.Creator<CheckQuantityManifest> CREATOR = new Parcelable.Creator<CheckQuantityManifest>() {
        @Override
        public CheckQuantityManifest createFromParcel(Parcel source) {
            return new CheckQuantityManifest(source);
        }

        @Override
        public CheckQuantityManifest[] newArray(int size) {
            return new CheckQuantityManifest[size];
        }
    };
}
