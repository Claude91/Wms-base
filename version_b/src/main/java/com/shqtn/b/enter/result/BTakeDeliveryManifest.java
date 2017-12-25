package com.shqtn.b.enter.result;

/**
 * 创建时间:2017/12/22
 * 描述:
 *
 * @author ql
 */

public class BTakeDeliveryManifest {


    /**
     * ikey : 1
     * asnNo : 11
     * recDate : 1512959333000
     * ownerCode : ALL
     * porder : 1111
     * whCode : 101
     * lineNum : 1
     * skuCode : 08600043
     * skuName : 三通
     * pQty : 10
     * unitCode : 01001
     * unitName : PCS
     * quantity : 0
     * batchFlag : Y
     * batchNoFlag : 2
     * serialFlag : Y
     * serialNoFlag : 1
     * ts : 1513526400000
     * orgnIkey : 0
     * pqty : 10
     */
    private String erpPorder; // ERP单据号
    private int ikey;
    private String asnNo;// ASN/收货单号
    private long recDate; // 收货日期
    private String ownerCode;//// 货主编码
    private String porder;// 采购订单
    private String whCode;
    private String lineNum;
    private String skuCode;
    private String skuName;
    private Double pQty; // 计划数量
    private String unitCode;
    private String unitName;
    private int quantity;// 收货数量
    private String batchFlag;// 批次管控标志位（Y:是，N：否）
    private String batchNoFlag;// 批次号获取方式
    private String serialFlag;// 序列号管控标志位（Y:是，N：否）
    private String serialNoFlag;// 序列号获取方式
    private String batchNo;
    private long ts;
    private int orgnIkey;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getErpPorder() {
        return erpPorder;
    }

    public void setErpPorder(String erpPorder) {
        this.erpPorder = erpPorder;
    }


    public void setpQty(Double pQty) {
        this.pQty = pQty;
    }

    public int getIkey() {
        return ikey;
    }

    public void setIkey(int ikey) {
        this.ikey = ikey;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public long getRecDate() {
        return recDate;
    }

    public void setRecDate(long recDate) {
        this.recDate = recDate;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getPorder() {
        return porder;
    }

    public void setPorder(String porder) {
        this.porder = porder;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Double getPQty() {
        return pQty;
    }

    public void setPQty(Double pQty) {
        this.pQty = pQty;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag;
    }

    public String getBatchNoFlag() {
        return batchNoFlag;
    }

    public void setBatchNoFlag(String batchNoFlag) {
        this.batchNoFlag = batchNoFlag;
    }

    public String getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(String serialFlag) {
        this.serialFlag = serialFlag;
    }

    public String getSerialNoFlag() {
        return serialNoFlag;
    }

    public void setSerialNoFlag(String serialNoFlag) {
        this.serialNoFlag = serialNoFlag;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getOrgnIkey() {
        return orgnIkey;
    }

    public void setOrgnIkey(int orgnIkey) {
        this.orgnIkey = orgnIkey;
    }

}
