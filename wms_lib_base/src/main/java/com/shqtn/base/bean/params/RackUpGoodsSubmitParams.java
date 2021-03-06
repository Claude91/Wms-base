package com.shqtn.base.bean.params;

import com.shqtn.base.bean.SerialNoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/7/19.
 */

public class RackUpGoodsSubmitParams {

    private List<Pis> pisList;
    private String lpnNo;
    private String whCode;


    public List<Pis> getPisList() {
        return pisList;
    }

    public void setPisList(List<Pis> pisList) {
        this.pisList = pisList;
    }

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    /**
     * 上架操作  提交
     * ikey	Long	是	理货单ikey
     * iquantity	Double	是	上架数量
     * poscode	String	是	货位编码
     */
    public static class Pis{
        private long ikey;
        private double iquantity;
        private String poscode;//上架的货位

        private String rdcode; // 入库类型
        private String ccode; // 理货单号
        private ArrayList<SerialNoVo> serialList;

        public ArrayList<SerialNoVo> getSerialList() {
            return serialList;
        }

        public void setSerialList(ArrayList<SerialNoVo> serialList) {
            this.serialList = serialList;
        }


        public String getRdcode() {
            return rdcode;
        }

        public void setRdcode(String rdcode) {
            this.rdcode = rdcode;
        }

        public String getCcode() {
            return ccode;
        }

        public void setCcode(String ccode) {
            this.ccode = ccode;
        }

        public long getIkey() {
            return ikey;
        }

        public void setIkey(long ikey) {
            this.ikey = ikey;
        }

        public double getIquantity() {
            return iquantity;
        }

        public void setIquantity(double iquantity) {
            this.iquantity = iquantity;
        }

        public String getPoscode() {
            return poscode;
        }

        public void setPoscode(String poscode) {
            this.poscode = poscode;
        }
    }
}
