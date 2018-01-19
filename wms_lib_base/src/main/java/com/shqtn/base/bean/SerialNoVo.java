package com.shqtn.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建时间:2017/12/26
 * 描述:
 *{"ikey":11965,"createdBy":"张三","createdDate":1516347528000,"modifyBy":"张三","modifyDate":1516347565000,"saasid":"SYSTEM","orgnIkey":0,"orgn":"SYSTEM","skuCode":"18011001","batchNo":"LOT20180119021","serialNo":"012018011900000191","whCode":"ALL","locCode":"ALLJH","ownerCode":"ALL","qty":1,"status":"2","resourceIkey":19744,"sourceIkey":14961,"sourceCode":"0000065955"}
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.serialNo);
    }

    protected SerialNoVo(Parcel in) {
        this.serialNo = in.readString();
    }

    public static final Creator<SerialNoVo> CREATOR = new Creator<SerialNoVo>() {
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
