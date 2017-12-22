package com.shqtn.base.bean.exit;

import com.shqtn.base.bean.base.IGoods;

/**
 * Created by android on 2017/8/2.
 */

public class PackingLpnOrGoods  extends IGoods{
    /**
     * 1.boxCode--箱号
     2.skuCode;--货品编码
     3.batchNo;--批次号
     4.quantity;--货品数量
     */
    private String boxCode;
    private String skuCode;
    private String batchNo;
    private double quantity;

    @Override
    public String getGoodsSku() {
        return skuCode;
    }

    @Override
    public String getGoodsBatchNo() {
        return getBatchNo();
    }

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
