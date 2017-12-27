package com.shqtn.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间:2017/12/26
 * 描述:
 *
 * @author ql
 */

public class SerialNoVo implements Parcelable {
    private String serialNo; // 序列号

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.serialNo);
    }

    public SerialNoVo() {
    }

    public SerialNoVo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return serialNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o != null && o instanceof String){
            return o.equals(serialNo);
        }

        if (o == null || getClass() != o.getClass()) return false;

        SerialNoVo that = (SerialNoVo) o;

        return serialNo != null ? serialNo.equals(that.serialNo) : that.serialNo == null;

    }

    @Override
    public int hashCode() {
        return serialNo != null ? serialNo.hashCode() : 0;
    }

    protected SerialNoVo(Parcel in) {
        this.serialNo = in.readString();
    }

    public static final Parcelable.Creator<SerialNoVo> CREATOR = new Parcelable.Creator<SerialNoVo>() {
        @Override
        public SerialNoVo createFromParcel(Parcel source) {
            return new SerialNoVo(source);
        }

        @Override
        public SerialNoVo[] newArray(int size) {
            return new SerialNoVo[size];
        }
    };
}
