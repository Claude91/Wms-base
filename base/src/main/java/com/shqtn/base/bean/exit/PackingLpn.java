package com.shqtn.base.bean.exit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/8/2.
 */

public class PackingLpn implements Parcelable {
    /**
     * 1.hikey;--包装任务主表IKEY
     2.packageNo;--包装任务单号
     3.boxCode;--箱号
     4.ownerName;--货主名称
     5.packStatus; --箱号状态(0：未装箱；1：装箱中；2：装箱完成；3：已出库)
     */

    private long hikey;
    private String packageNo;
    private String boxCode;
    private String ownerName;
    private String packStatus;

    public long getHikey() {
        return hikey;
    }

    public void setHikey(long hikey) {
        this.hikey = hikey;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(String packStatus) {
        this.packStatus = packStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.hikey);
        dest.writeString(this.packageNo);
        dest.writeString(this.boxCode);
        dest.writeString(this.ownerName);
        dest.writeString(this.packStatus);
    }

    public PackingLpn() {
    }

    protected PackingLpn(Parcel in) {
        this.hikey = in.readLong();
        this.packageNo = in.readString();
        this.boxCode = in.readString();
        this.ownerName = in.readString();
        this.packStatus = in.readString();
    }

    public static final Parcelable.Creator<PackingLpn> CREATOR = new Parcelable.Creator<PackingLpn>() {
        @Override
        public PackingLpn createFromParcel(Parcel source) {
            return new PackingLpn(source);
        }

        @Override
        public PackingLpn[] newArray(int size) {
            return new PackingLpn[size];
        }
    };
}
