package com.shqtn.base.bean.params;

import com.shqtn.base.bean.BatchAttr;
import com.shqtn.base.bean.SerialNoVo;

import java.util.List;

/**
 * Created by android on 2017/7/18.
 */

public class TakeDeliveryGoodsSubmitParams {
    /*{
    accQuantity = 1;
    batchAttrList =     (
            {
                    attrValue = 1;     // 新添加批次属性的值
    description = 234;
    skuCode = CSOOOO1;
    private String batchNo; // 批次号

    }
    )；*/
    private double accQuantity;
    private List<BatchAttr> batchList;
    private Long ihkey;
    private Long ikey;
    private String skuCode;
    private String skuName;
    private long ts;
    private String batchNo;

    List<SerialNoVo> serialNoList; // 序列号

    public double getAccQuantity() {
        return accQuantity;
    }

    public void setAccQuantity(double accQuantity) {
        this.accQuantity = accQuantity;
    }

    public List<BatchAttr> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<BatchAttr> batchList) {
        this.batchList = batchList;
    }

    public Long getIhkey() {
        return ihkey;
    }

    public void setIhkey(Long ihkey) {
        this.ihkey = ihkey;
    }

    public Long getIkey() {
        return ikey;
    }

    public void setIkey(Long ikey) {
        this.ikey = ikey;
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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<SerialNoVo> getSerialNoList() {
        return serialNoList;
    }

    public void setSerialNoList(List<SerialNoVo> serialNoList) {
        this.serialNoList = serialNoList;
    }



}
