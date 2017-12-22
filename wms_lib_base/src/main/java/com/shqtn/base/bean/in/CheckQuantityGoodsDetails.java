package com.shqtn.base.bean.in;

import com.shqtn.base.bean.base.IGoods;

/**
 * Created by android on 2017/8/3.
 */

public class CheckQuantityGoodsDetails extends IGoods {
    /**
     * 1.skuCode;--货品编码
     * 2.skuName;--货品名称
     * 3.batchNo;--批次号
     * 4.accQty;--账面数量
     * 5.locCode;--货位编码
     * 6.locName;--货位名称
     * 7.ownerCode;--货主编码
     * 8.ownerName;--货主名称
     * <p>
     * <p>
     * 1.unitName;--单位名称
     * 2.std;--货品名称
     * 3.accQty;--账面数量
     * 4.ikey;--盘点任务明细Ikey
     */
    private String skuCode;
    private String skuName;
    private String batchNo;
    private String locCode;
    private String locName;
    private String ownerCode;
    private String ownerNome;
    private String unitName;
    private String std;
    private double accQty;
    private long ikey;

    private double addQty;//添加数量

    private String skuClassCode;
    private String skuClassName;

    @Override
    public String getGoodsBatchNo() {
        return batchNo;
    }

    @Override
    public String getGoodsSku() {
        return skuCode;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerNome() {
        return ownerNome;
    }

    public void setOwnerNome(String ownerNome) {
        this.ownerNome = ownerNome;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public double getAccQty() {
        return accQty;
    }

    public void setAccQty(double accQty) {
        this.accQty = accQty;
    }

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public double getAddQty() {
        return addQty;
    }

    public void setAddQty(double addQty) {
        this.addQty = addQty;
    }

    public String getSkuClassCode() {
        return skuClassCode;
    }

    public void setSkuClassCode(String skuClassCode) {
        this.skuClassCode = skuClassCode;
    }

    public String getSkuClassName() {
        return skuClassName;
    }

    public void setSkuClassName(String skuClassName) {
        this.skuClassName = skuClassName;
    }
}
