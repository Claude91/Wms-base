package com.shqtn.base.info.code;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by android on 2017/7/14.
 */

public class CodeLpn implements Parcelable {

    private String lpnNo;

    /**
     * 当前lpn中的货品数量
     */
    private ArrayList<CodeGoods> goodsList;

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    public ArrayList<CodeGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<CodeGoods> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lpnNo);
        dest.writeList(this.goodsList);
    }

    public CodeLpn() {
    }

    protected CodeLpn(Parcel in) {
        this.lpnNo = in.readString();
        this.goodsList = new ArrayList<CodeGoods>();
        in.readList(this.goodsList, CodeGoods.class.getClassLoader());
    }

    public static final Parcelable.Creator<CodeLpn> CREATOR = new Parcelable.Creator<CodeLpn>() {
        @Override
        public CodeLpn createFromParcel(Parcel source) {
            return new CodeLpn(source);
        }

        @Override
        public CodeLpn[] newArray(int size) {
            return new CodeLpn[size];
        }
    };
}
