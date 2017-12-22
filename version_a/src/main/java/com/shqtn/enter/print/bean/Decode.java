package com.shqtn.enter.print.bean;

import com.shqtn.base.info.code.CodeGoods;

/**
 * 创建时间:2017/12/5
 * 描述:
 *
 * @author ql
 */

public class Decode extends CodeGoods {
    /*  {
          "skuCode":"08600043","batchNo":"","ownerCode":"","quantity":"","skuName":"三通","unitName":"PCS","std":"配￠40PPR管"}

      }*/
    private String skuName;
    private String unitName;
    private String std;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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
}
