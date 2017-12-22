package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/28.
 */

public class QualityInspectionGoodsSubmitParams {
    private int ikey; // 主键IKEY  传回数据
    private double okQty; // 合格数量   传回数据
    private double assOkQty; // 辅合格数量  传回数据
    private double badQty; // 不良数量   传回数据
    private double scrapQty; // 报废数量  传回数据
    private String reasonCode; // 不良代码   传回数据，
    private long ts; // 时间戳    传回数据
    private String skuCode;
    private String batchNo;

    public int getIkey() {
        return ikey;
    }

    public void setIkey(int ikey) {
        this.ikey = ikey;
    }

    public double getOkQty() {
        return okQty;
    }

    public void setOkQty(double okQty) {
        this.okQty = okQty;
    }

    public double getAssOkQty() {
        return assOkQty;
    }

    public void setAssOkQty(double assOkQty) {
        this.assOkQty = assOkQty;
    }

    public double getBadQty() {
        return badQty;
    }

    public void setBadQty(double badQty) {
        this.badQty = badQty;
    }

    public double getScrapQty() {
        return scrapQty;
    }

    public void setScrapQty(double scrapQty) {
        this.scrapQty = scrapQty;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
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
