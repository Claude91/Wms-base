package com.shqtn.base.bean;

/**
 * Created by android on 2017/7/31.
 */

public class LpnStatus {
    /**
     * 1.skuCode;--货品编码
     2.batchNo;--批次号
     3.skuQty;--小箱子已装货品数量
     4.packCode;--箱号
     4.packStatus;--箱子/货品 状态（10：未收货；20：已收货；30：理货完成；40：已上架；50：已下架；60：已出库；）
     */
    private String skuCode;
    private String batchNo;
    private int skuQty;


    private String std;
    private String unit;


    private String packCode;
    /**
     * 箱子/货品 状态（10：未收货；20：已收货；30：理货完成；40：已上架；50：已下架；60：已出库；）
     */
    private String packStatus;

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

    public int getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(int skuQty) {
        this.skuQty = skuQty;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(String packStatus) {
        this.packStatus = packStatus;
    }
}
