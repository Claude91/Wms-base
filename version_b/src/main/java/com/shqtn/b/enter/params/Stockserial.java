package com.shqtn.b.enter.params;

/**
 * 创建时间:2018/1/12
 * 描述:
 *
 * @author ql
 */
public class Stockserial {
    /**
     * 字段 skuCode batchNo serialNo  qty  packNo udf01（收货单号）
     */
    private String skuCode;
    private String batchNo;
    private String serialNo;
    private double qty = 1.0d;
    private String packNo;
    private String udf01;


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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }

    public String getUdf01() {
        return udf01;
    }

    public void setUdf01(String udf01) {
        this.udf01 = udf01;
    }
}
