package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/10.
 */

public class PackingOutManifestSubmitParams {
    /**
     * 1.ikey;--包装任务Ikey
     2.packageNo;--包装任务单号
     */
    private long ikey;
    private String packageNo;

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }
}
