package com.shqtn.b.enter.params;

import com.shqtn.base.bean.BatchAttr;
import com.shqtn.base.bean.SerialNoVo;

import java.util.List;

/**
 * 创建时间:2017/12/28
 * 描述:
 *
 * @author ql
 */

public class TakeDelSubmitParams {
    private long ikey;
    private String asnNo;// ASN/收货单号
    private String ownerCode;//// 货主编码
    private String whCode;
    private String skuCode;
    private String batchNo;
    private double accQuantity;//数量
    private long orgnIkey;
    private List<BatchAttr> batchAttrList; // 批次属性

    private List<SerialNoVo> serialNoList; // 序列号匹配，如果为空则不需要进行匹配

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getAsnNo() {
        return asnNo;
    }

    public void setAsnNo(String asnNo) {
        this.asnNo = asnNo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
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

    public double getAccQuantity() {
        return accQuantity;
    }

    public void setAccQuantity(double accQuantity) {
        this.accQuantity = accQuantity;
    }

    public long getOrgnIkey() {
        return orgnIkey;
    }

    public void setOrgnIkey(long orgnIkey) {
        this.orgnIkey = orgnIkey;
    }

    public List<BatchAttr> getBatchAttrList() {
        return batchAttrList;
    }

    public void setBatchAttrList(List<BatchAttr> batchAttrList) {
        this.batchAttrList = batchAttrList;
    }

    public List<SerialNoVo> getSerialNoList() {
        return serialNoList;
    }

    public void setSerialNoList(List<SerialNoVo> serialNoList) {
        this.serialNoList = serialNoList;
    }
}
