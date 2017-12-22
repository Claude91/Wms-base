package com.shqtn.base.bean.params;

import java.util.ArrayList;

/**
 * Created by android on 2017/8/7.
 */

public class GoodsAdjustGoodsSubmitParams {
    public ArrayList<SubmitMovePro> getStockposSearchList() {
        return stockposSearchList;
    }

    public void setStockposSearchList(ArrayList<SubmitMovePro> stockposSearchList) {
        this.stockposSearchList = stockposSearchList;
    }

    private ArrayList<SubmitMovePro> stockposSearchList;

    public static class SubmitMovePro{
        //1.ownerId;--货主ID
        private String ownerId;
        //2.skuCode;--存货编码
        private String skuCode;
        //3.whCode;--仓库编码
        private String whCode;
        //4.locCode;--货位编码
        private String locCode;
        //5.batchNo;--批次号
        private String batchNo;
        //6.skuIkey;--存货IKEY
        private long skuIkey;
        //7.adjQty;--货位调整数量
        private double adjQty;
        //8.adjLocPos;--调整目标货位
        private String adjLocPos;

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }

        public String getWhCode() {
            return whCode;
        }

        public void setWhCode(String whCode) {
            this.whCode = whCode;
        }

        public String getLocCode() {
            return locCode;
        }

        public void setLocCode(String locCode) {
            this.locCode = locCode;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public long getSkuIkey() {
            return skuIkey;
        }

        public void setSkuIkey(long skuIkey) {
            this.skuIkey = skuIkey;
        }

        public double getAdjQty() {
            return adjQty;
        }

        public void setAdjQty(double adjQty) {
            this.adjQty = adjQty;
        }

        public String getAdjLocPos() {
            return adjLocPos;
        }

        public void setAdjLocPos(String adjLocPos) {
            this.adjLocPos = adjLocPos;
        }
    }
}
