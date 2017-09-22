package com.shqtn.base.bean.params;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by android on 2017/8/1.
 */

public class RackUpLpnSubmitParams {

    private String lpnNo;

    private List<Pis> pisList;

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    public List<Pis> getPisList() {
        return pisList;
    }

    public void setPisList(List<Pis> pisList) {
        this.pisList = pisList;
    }

    public static class Pis implements Parcelable {

        private long ikey;
        private double iquantity;
        private String poscode;//上架的货位

        private String ccode;//任务单据号
        private String rdcode;

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

        public long getIkey() {
            return ikey;
        }

        public void setIkey(long ikey) {
            this.ikey = ikey;
        }

        public double getIquantity() {
            return iquantity;
        }

        public void setIquantity(double iquantity) {
            this.iquantity = iquantity;
        }

        public String getPoscode() {
            return poscode;
        }

        public void setPoscode(String poscode) {
            this.poscode = poscode;
        }

        public Pis() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.ikey);
            dest.writeDouble(this.iquantity);
            dest.writeString(this.poscode);
            dest.writeString(this.ccode);
            dest.writeString(this.rdcode);
        }

        protected Pis(Parcel in) {
            this.ikey = in.readLong();
            this.iquantity = in.readDouble();
            this.poscode = in.readString();
            this.ccode = in.readString();
            this.rdcode = in.readString();
        }

        public static final Creator<Pis> CREATOR = new Creator<Pis>() {
            @Override
            public Pis createFromParcel(Parcel source) {
                return new Pis(source);
            }

            @Override
            public Pis[] newArray(int size) {
                return new Pis[size];
            }
        };
    }

}
