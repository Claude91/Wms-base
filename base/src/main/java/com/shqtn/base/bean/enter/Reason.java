package com.shqtn.base.bean.enter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2017/7/28.
 */

public class Reason implements Parcelable {
    private int ikey;
    private String createdBy;
    private long createdDate;
    private String modifyBy;
    private long modifyDate;
    private String saasid;
    private String reasonType;
    private String reasonCode;
    private String description;
    private int orgId;

    public int getIkey() {
        return ikey;
    }

    public void setIkey(int ikey) {
        this.ikey = ikey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getSaasid() {
        return saasid;
    }

    public void setSaasid(String saasid) {
        this.saasid = saasid;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ikey);
        dest.writeString(this.createdBy);
        dest.writeLong(this.createdDate);
        dest.writeString(this.modifyBy);
        dest.writeLong(this.modifyDate);
        dest.writeString(this.saasid);
        dest.writeString(this.reasonType);
        dest.writeString(this.reasonCode);
        dest.writeString(this.description);
        dest.writeInt(this.orgId);
    }

    public Reason() {
    }

    protected Reason(Parcel in) {
        this.ikey = in.readInt();
        this.createdBy = in.readString();
        this.createdDate = in.readLong();
        this.modifyBy = in.readString();
        this.modifyDate = in.readLong();
        this.saasid = in.readString();
        this.reasonType = in.readString();
        this.reasonCode = in.readString();
        this.description = in.readString();
        this.orgId = in.readInt();
    }

    public static final Parcelable.Creator<Reason> CREATOR = new Parcelable.Creator<Reason>() {
        @Override
        public Reason createFromParcel(Parcel source) {
            return new Reason(source);
        }

        @Override
        public Reason[] newArray(int size) {
            return new Reason[size];
        }
    };
}
