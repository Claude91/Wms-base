package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/28.
 */

public class QualityInspectionGoodsParams {
    private String skuCode; // SKU编码
    private String batchNo; // 批次号

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
}
