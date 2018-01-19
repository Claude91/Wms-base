package com.shqtn.b.enter.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IManifest;

/**
 * 创建时间:2018/1/10
 * 描述:
 *
 * @author ql
 */

public class BTakeBoxManifest implements IManifest, Parcelable {
    private String docNo;
    private long ikey;

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    @Override
    public String getManifest() {
        return docNo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docNo);
        dest.writeLong(this.ikey);
    }

    public BTakeBoxManifest() {
    }

    protected BTakeBoxManifest(Parcel in) {
        this.docNo = in.readString();
        this.ikey = in.readLong();
    }

    public static final Parcelable.Creator<BTakeBoxManifest> CREATOR = new Parcelable.Creator<BTakeBoxManifest>() {
        @Override
        public BTakeBoxManifest createFromParcel(Parcel source) {
            return new BTakeBoxManifest(source);
        }

        @Override
        public BTakeBoxManifest[] newArray(int size) {
            return new BTakeBoxManifest[size];
        }
    };
}
