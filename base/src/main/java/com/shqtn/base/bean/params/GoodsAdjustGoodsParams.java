package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/7.
 */

public class GoodsAdjustGoodsParams {
       /*
    1.ownerId;--货主ID
      2.skuCode;--存货编码
      3.whCode;--仓库编码
      4.locCode;--货位编码
      5.batchNo;--批次号
     */

    private String ownerId;
    private String skuCode;
    private String whCode;
    private String locCode;
    private String batchNo;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

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
