package com.shqtn.b.enter.result;

import android.os.Parcel;

import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.bean.exit.SerialNo;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2018/1/19
 * 描述:
 *
 * @author ql
 */

public class BRackUpGoods extends RackUpGoods {


    private ArrayList<SerialNoVo> serialList;

    public ArrayList<SerialNoVo> getSerialList() {
        return serialList;
    }

    public void setSerialList(ArrayList<SerialNoVo> serialList) {
        this.serialList = serialList;
    }

    public BRackUpGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.serialList);
    }

    protected BRackUpGoods(Parcel in) {
        super(in);
        this.serialList = in.createTypedArrayList(SerialNoVo.CREATOR);
    }

    public static final Creator<BRackUpGoods> CREATOR = new Creator<BRackUpGoods>() {
        @Override
        public BRackUpGoods createFromParcel(Parcel source) {
            return new BRackUpGoods(source);
        }

        @Override
        public BRackUpGoods[] newArray(int size) {
            return new BRackUpGoods[size];
        }
    };
}
