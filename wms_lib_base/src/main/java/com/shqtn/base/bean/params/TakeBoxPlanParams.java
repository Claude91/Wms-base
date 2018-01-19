package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/28.
 */

public class TakeBoxPlanParams {
    private String docNo; //当前的单号
    private String skuCode;//当前货品的SKU；
    private String batchNo;//货品的批次属性
    private String whCode;//仓库
    private Long ikey;

    public Long getIkey() {
        return ikey;
    }

    public void setIkey(Long ikey) {
        this.ikey = ikey;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }
}
