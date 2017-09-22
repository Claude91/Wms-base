package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/3.
 */

public class CheckQuantityGoodsDetailsParams {
    /**
     * 1.whCode;--货主ID
     2.docNo;--存货编码
     3.skuCode;--仓库编码
     4.locCode;--货位编码
     5.batchNo;--批次号
     */
    private String whCode;
    private String docNo;
    private String skuCode;
    private String locCode;
    private String batchNo;

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
