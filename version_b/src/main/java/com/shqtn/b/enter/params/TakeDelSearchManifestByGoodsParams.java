package com.shqtn.b.enter.params;

/**
 * 创建时间:2017/12/27
 * 描述:
 * 收货任务中，通过货品查询任务单号
 *
 * @author ql
 */

public class TakeDelSearchManifestByGoodsParams {
    private String skuCode;//货品
    private String whCode;//仓库

    private String batchNo;//批次号

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
