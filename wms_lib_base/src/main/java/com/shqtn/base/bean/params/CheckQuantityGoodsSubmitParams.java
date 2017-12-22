package com.shqtn.base.bean.params;

import java.util.ArrayList;

/**
 * Created by android on 2017/8/3.
 */

public class CheckQuantityGoodsSubmitParams {
    private ArrayList<Params> checkings;

    public ArrayList<Params> getCheckings() {
        return checkings;
    }

    public void setCheckings(ArrayList<Params> checkings) {
        this.checkings = checkings;
    }

    public static class Params {
        /**
         * 1.ikey;--盘点明细IKEY
         * 2.hikey;--主表IKEY
         * 3.docNo;--盘点单号
         * 4.ownerCode;--货主编码
         * 5.whCode;--仓库编码
         * 6.areaCode;--区域编码
         * 7.locCode;--货位编码
         * 8.skuClassCode;--大类编码
         * 9.skuCode;--存货编码
         * 10.batchNo;--批次号
         * 11.accQty;--账面数量
         * 12.actQty;--实盘数量
         */
        private long hikey;
        private String docNo;
        private String ownerCode;
        private String whCode;
        private String locCode;
        private String skuClassCode;
        private String skuCode;
        private String batchNo;
        private double accQty;
        private long docDate;
        /**
         * 1.ikey;--盘点明细IKEY
         * 2.actQty;--实盘数量
         */
        private long ikey;
        private double actQty;

        public long getHikey() {
            return hikey;
        }

        public void setHikey(long hikey) {
            this.hikey = hikey;
        }

        public String getDocNo() {
            return docNo;
        }

        public void setDocNo(String docNo) {
            this.docNo = docNo;
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

        public String getLocCode() {
            return locCode;
        }

        public void setLocCode(String locCode) {
            this.locCode = locCode;
        }

        public String getSkuClassCode() {
            return skuClassCode;
        }

        public void setSkuClassCode(String skuClassCode) {
            this.skuClassCode = skuClassCode;
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

        public double getAccQty() {
            return accQty;
        }

        public void setAccQty(double accQty) {
            this.accQty = accQty;
        }

        public long getDocDate() {
            return docDate;
        }

        public void setDocDate(long docDate) {
            this.docDate = docDate;
        }

        public long getIkey() {
            return ikey;
        }

        public void setIkey(long ikey) {
            this.ikey = ikey;
        }

        public double getActQty() {
            return actQty;
        }

        public void setActQty(double actQty) {
            this.actQty = actQty;
        }
    }

}
