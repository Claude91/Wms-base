package com.shqtn.base.bean.params;

import java.util.ArrayList;

/**
 * Created by android on 2017/8/11.
 */

public class PalletHaveCodeInSubmitParams {

    private String palletNo;

    private String whCode;

    private ArrayList<DataBean> list;

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public ArrayList<DataBean> getList() {
        return list;
    }

    public void setList(ArrayList<DataBean> list) {
        this.list = list;
    }

    public static class DataBean {
        //货品需要提交的参数
        private String invcode; // 存货编码
        private String batchNo; // 批次号
        private Double iquantity; //数量

        //箱号提交的参数
        private String lpnNo;//箱号
        private String ownerId;

        public String getOwnerId() {
            return ownerId;
        }

        public String getInvcode() {
            return invcode;
        }

        public void setInvcode(String invcode) {
            this.invcode = invcode;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public double getIquantity() {
            return iquantity;
        }

        public void setIquantity(double iquantity) {
            this.iquantity = iquantity;
        }

        public String getLpnNo() {
            return lpnNo;
        }

        public void setLpnNo(String lpnNo) {
            this.lpnNo = lpnNo;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }
    }
}
