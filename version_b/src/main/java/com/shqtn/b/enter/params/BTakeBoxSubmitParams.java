package com.shqtn.b.enter.params;

import com.shqtn.base.bean.params.TakeBoxSubmitParams;

import java.util.List;

/**
 * 创建时间:2018/1/10
 * 描述:
 *
 * @author ql
 */

public class BTakeBoxSubmitParams {
    /**
     * 装箱完成
     */
    public static String SUBMIT_FLAG_TAKE_OVER = "1";

    /**
     * 只是提交
     */
    public static String SUBMIT_FLAG_TAKE_SUBMIT = "0";


    private String overFlag;//--按钮标志位：0：提交；1：装箱完成；
    private String fLpnNo;
    private double countNum;
    private String packLevel;
    private String docNo;
    private String whCode;

    private String packCode;//当添加的是已经包装好的箱号时添加的属性
    private String packStatus;//--包装状态
    /**
     * 一级包装时添加的属性
     */
    private String skuCode;
    private String batchNo;

    public String getOverFlag() {
        return overFlag;
    }

    public void setOverFlag(String overFlag) {
        this.overFlag = overFlag;
    }

    public String getfLpnNo() {
        return fLpnNo;
    }

    public void setfLpnNo(String fLpnNo) {
        this.fLpnNo = fLpnNo;
    }

    public double getCountNum() {
        return countNum;
    }

    public void setCountNum(double countNum) {
        this.countNum = countNum;
    }

    public String getPackLevel() {
        return packLevel;
    }

    public void setPackLevel(String packLevel) {
        this.packLevel = packLevel;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
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
