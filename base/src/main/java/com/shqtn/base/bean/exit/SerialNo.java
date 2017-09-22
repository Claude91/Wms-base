package com.shqtn.base.bean.exit;

/**
 * Created by android on 2017/7/26.
 */

public class SerialNo {
    //{"rs":true,"data":[{"ikey":10044,"serialNo":""},{"ikey":10045,"serialNo":""}]}
    private int ikey;
    private String serialNo;

    public int getIkey() {
        return ikey;
    }

    public void setIkey(int ikey) {
        this.ikey = ikey;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
