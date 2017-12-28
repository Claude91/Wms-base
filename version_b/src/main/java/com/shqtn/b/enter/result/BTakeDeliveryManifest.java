package com.shqtn.b.enter.result;

import com.shqtn.base.bean.BatchAttr;
import com.shqtn.base.bean.BatchAttrVo;
import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.base.IGoods;
import com.shqtn.base.bean.params.TakeDeliveryGoodsSubmitParams;

import java.sql.Clob;
import java.util.List;

/**
 * 创建时间:2017/12/22
 * 描述:
 *
 * @author ql
 */

public class BTakeDeliveryManifest extends IGoods {


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
    private long ikey;
    private String asnNo;// ASN/收货单号
    private long recDate; // 收货日期
    private String ownerCode;//// 货主编码
    private String porder;// 采购订单
    private String whCode;
    private String lineNum;
    private String skuCode;
    private String skuName;
    private double pQty; // 计划数量
    private String unitCode;
    private String unitName;
    private double quantity;// 收货数量
    private String batchFlag;// 批次管控标志位（Y:是，N：否）
    private String batchNoFlag;// 批次号获取方式
    private String serialFlag;// 序列号管控标志位（Y:是，N：否）
    private String serialNoFlag;// 序列号获取方式
    private String batchNo;
    private double accQuantity;//数量
    private long ts;
    private int orgnIkey;
    private List<BatchAttr> batchAttrList; // 批次属性

    private List<SerialNoVo> serialNoList; // 序列号匹配，如果为空则不需要进行匹配
    private List<SerialNoVo> shSerialNoList; // 外部采集已经收货的序列号


    public double getAccQuantity() {
        return accQuantity;
    }

    public void setAccQuantity(double accQuantity) {
        this.accQuantity = accQuantity;
    }

    public List<SerialNoVo> getShSerialNoList() {
        return shSerialNoList;
    }

    public void setShSerialNoList(List<SerialNoVo> shSerialNoList) {
        this.shSerialNoList = shSerialNoList;
    }

    @Override
    public String getGoodsSku() {
        return skuCode;
    }

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    public double getpQty() {
        return pQty;
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


    public void setpQty(double pQty) {
        this.pQty = pQty;
    }

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

    public double getPQty() {
        return pQty;
    }

    public void setPQty(double pQty) {
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
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
