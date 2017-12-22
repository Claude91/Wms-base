package com.shqtn.wms.presenter.enter.params;

/**
 * 创建时间:2017/12/18
 * 描述:
 *
 * @author ql
 */

public class TakeDeliveryScanningGoodsParams {
    private String whCode, skuCode, batchNo;

    public TakeDeliveryScanningGoodsParams() {
    }

    public TakeDeliveryScanningGoodsParams(String whCode, String skuCode, String batchNo) {
        this.whCode = whCode;
        this.skuCode = skuCode;
        this.batchNo = batchNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
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
}
