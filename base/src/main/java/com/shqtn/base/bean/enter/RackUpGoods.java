package com.shqtn.base.bean.enter;

import android.os.Parcel;
import android.os.Parcelable;

import com.shqtn.base.bean.base.IGoods;


/**
 * Created by android on 2017/7/19.
 */

public class RackUpGoods extends IGoods implements Parcelable {
    public static final String TAG_LPN = "Y";

    private long ikey;
    private String ccode;
    private String rdcode;
    private String invcode;
    private int invkey;
    private String whcode;
    private String poscode;//货位编码
    private String batchno;
    private double iquantity;
    private double inum;
    private String assunit;
    private double iinsumquantity;
    private double pquantity;
    private String rdName;//入库类型名称

    private String skuName;//货品名称

    private String whName;//仓库名称

    private String posName;//货位名称
    private String unitName; // 单位名称
    private String std; // 规格

    private String palletno; // 托盘号

    private String palletflag; // 托盘标识//如果为Y  托盘

    public String getPalletno() {
        return palletno;
    }

    public void setPalletno(String palletno) {
        this.palletno = palletno;
    }

    public String getPalletflag() {
        return palletflag;
    }

    public void setPalletflag(String palletflag) {
        this.palletflag = palletflag;
    }

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getRdcode() {
        return rdcode;
    }

    public void setRdcode(String rdcode) {
        this.rdcode = rdcode;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }

    @Override
    public String getGoodsSku() {
        return invcode;
    }

    public int getInvkey() {
        return invkey;
    }

    public void setInvkey(int invkey) {
        this.invkey = invkey;
    }

    public String getWhcode() {
        return whcode;
    }

    public void setWhcode(String whcode) {
        this.whcode = whcode;
    }

    public String getPoscode() {
        return poscode;
    }

    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchno;
    }

    public double getIquantity() {
        return iquantity;
    }

    public void setIquantity(double iquantity) {
        this.iquantity = iquantity;
    }

    public double getInum() {
        return inum;
    }

    public void setInum(double inum) {
        this.inum = inum;
    }

    public String getAssunit() {
        return assunit;
    }

    public void setAssunit(String assunit) {
        this.assunit = assunit;
    }

    public double getIinsumquantity() {
        return iinsumquantity;
    }

    public void setIinsumquantity(double iinsumquantity) {

        this.iinsumquantity = iinsumquantity;
    }

    public double getPquantity() {
        return pquantity;
    }

    public void setPquantity(double pquantity) {
        this.pquantity = pquantity;
    }

    public String getRdName() {
        return rdName;
    }

    public void setRdName(String rdName) {
        this.rdName = rdName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }


    @Override
    public double getGoodsQty() {
        return pquantity - iinsumquantity;
    }

    public RackUpGoods() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ikey);
        dest.writeString(this.ccode);
        dest.writeString(this.rdcode);
        dest.writeString(this.invcode);
        dest.writeInt(this.invkey);
        dest.writeString(this.whcode);
        dest.writeString(this.poscode);
        dest.writeString(this.batchno);
        dest.writeDouble(this.iquantity);
        dest.writeDouble(this.inum);
        dest.writeString(this.assunit);
        dest.writeDouble(this.iinsumquantity);
        dest.writeDouble(this.pquantity);
        dest.writeString(this.rdName);
        dest.writeString(this.skuName);
        dest.writeString(this.whName);
        dest.writeString(this.posName);
        dest.writeString(this.unitName);
        dest.writeString(this.std);
        dest.writeString(this.palletno);
        dest.writeString(this.palletflag);
    }

    protected RackUpGoods(Parcel in) {
        this.ikey = in.readLong();
        this.ccode = in.readString();
        this.rdcode = in.readString();
        this.invcode = in.readString();
        this.invkey = in.readInt();
        this.whcode = in.readString();
        this.poscode = in.readString();
        this.batchno = in.readString();
        this.iquantity = in.readDouble();
        this.inum = in.readDouble();
        this.assunit = in.readString();
        this.iinsumquantity = in.readDouble();
        this.pquantity = in.readDouble();
        this.rdName = in.readString();
        this.skuName = in.readString();
        this.whName = in.readString();
        this.posName = in.readString();
        this.unitName = in.readString();
        this.std = in.readString();
        this.palletno = in.readString();
        this.palletflag = in.readString();
    }

    public static final Creator<RackUpGoods> CREATOR = new Creator<RackUpGoods>() {
        @Override
        public RackUpGoods createFromParcel(Parcel source) {
            return new RackUpGoods(source);
        }

        @Override
        public RackUpGoods[] newArray(int size) {
            return new RackUpGoods[size];
        }
    };
}
