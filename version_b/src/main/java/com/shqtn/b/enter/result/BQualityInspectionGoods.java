package com.shqtn.b.enter.result;

import android.os.Parcel;

import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.enter.QualityInspectionGoods;

import java.util.List;

/**
 * 创建时间:2018/1/8
 * 描述:
 *
 * @author ql
 */

public class BQualityInspectionGoods extends QualityInspectionGoods {

    private String serialFlag;// 序列号管控标志位（Y:是，N：否）
    private List<SerialNoVo> serialNoList; // 序列号匹配，如果为空则不需要进行匹配

    public String getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(String serialFlag) {
        this.serialFlag = serialFlag;
    }

    public List<SerialNoVo> getSerialNoList() {
        return serialNoList;
    }

    public void setSerialNoList(List<SerialNoVo> serialNoList) {
        this.serialNoList = serialNoList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.serialFlag);
        dest.writeTypedList(this.serialNoList);
    }

    public BQualityInspectionGoods() {
    }

    protected BQualityInspectionGoods(Parcel in) {
        super(in);
        this.serialFlag = in.readString();
        this.serialNoList = in.createTypedArrayList(SerialNoVo.CREATOR);
    }

    public static final Creator<BQualityInspectionGoods> CREATOR = new Creator<BQualityInspectionGoods>() {
        @Override
        public BQualityInspectionGoods createFromParcel(Parcel source) {
            return new BQualityInspectionGoods(source);
        }

        @Override
        public BQualityInspectionGoods[] newArray(int size) {
            return new BQualityInspectionGoods[size];
        }
    };
}
