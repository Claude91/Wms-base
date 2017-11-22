package com.shqtn.base.bean.base;

/**
 * Created by android on 2017/7/18.
 */

public abstract class IGoods {

    private Double skuQty;
    private String goodsSku;
    private String goodsBatchNo;
    private boolean tag;

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public void setGoodsQty(Double skuQty) {
        this.skuQty = skuQty;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public void setGoodsBatchNo(String goodsBatchNo) {
        this.goodsBatchNo = goodsBatchNo;
    }

    public double getGoodsQty() {
        return skuQty;
    }


    public String getGoodsSku() {
        return goodsSku;
    }


    public String getGoodsBatchNo() {
        return goodsBatchNo;
    }

}
