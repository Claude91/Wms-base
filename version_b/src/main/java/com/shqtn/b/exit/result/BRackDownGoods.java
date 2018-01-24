package com.shqtn.b.exit.result;

import android.os.Parcel;

import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.exit.RackDownGoods;

import java.util.List;

/**
 * 创建时间:2018/1/24
 * 描述:
 *
 * @author ql
 */

public class BRackDownGoods extends RackDownGoods {

    private String serialNoFlag;//“Y” 是序列号管理

    private List<SerialNoVo> serials;//序列号集合

    public String getSerialNoFlag() {
        return serialNoFlag;
    }

    public void setSerialNoFlag(String serialNoFlag) {
        this.serialNoFlag = serialNoFlag;
    }

    public List<SerialNoVo> getSerials() {
        return serials;
    }

    public void setSerials(List<SerialNoVo> serials) {
        this.serials = serials;
    }

    public boolean isSerialOperate() {
        return "Y".equals(serialNoFlag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.serialNoFlag);
        dest.writeTypedList(this.serials);
    }

    public BRackDownGoods() {
    }

    protected BRackDownGoods(Parcel in) {
        super(in);
        this.serialNoFlag = in.readString();
        this.serials = in.createTypedArrayList(SerialNoVo.CREATOR);
    }

    public static final Creator<BRackDownGoods> CREATOR = new Creator<BRackDownGoods>() {
        @Override
        public BRackDownGoods createFromParcel(Parcel source) {
            return new BRackDownGoods(source);
        }

        @Override
        public BRackDownGoods[] newArray(int size) {
            return new BRackDownGoods[size];
        }
    };
}
