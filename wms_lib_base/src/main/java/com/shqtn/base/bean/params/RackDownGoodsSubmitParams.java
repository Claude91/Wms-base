package com.shqtn.base.bean.params;

import com.shqtn.base.bean.SerialNoVo;

import java.util.List;

/**
 * Created by android on 2017/7/20.
 */

public class RackDownGoodsSubmitParams {
    private long pickingIkey;//唯一的key
    private String docNo;//单据号
    private String locationCode;//下架的货位
    private String skuCode;//货品编码
    private String unitName;//货品单位
    private String batchNo;//货品批次号
    private String targetLocCode;//转移到货位编码
    private String whCode;//仓库编码
    private String areaCode;//区域编码
    private double downQuantity;//计划数量
    private String ownerId;//货主编码


    public long getPickingIkey() {
        return pickingIkey;
    }

    public void setPickingIkey(long pickingIkey) {
        this.pickingIkey = pickingIkey;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTargetLocCode() {
        return targetLocCode;
    }

    public void setTargetLocCode(String targetLocCode) {
        this.targetLocCode = targetLocCode;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public double getDownQuantity() {
        return downQuantity;
    }

    public void setDownQuantity(double downQuantity) {
        this.downQuantity = downQuantity;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
