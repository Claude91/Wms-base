package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/31.
 */

public class TakeBoxCheckingBoxStatusParams {
    /**
     * 1.ikey;--到货明细Ikey
     2.lpnNo;--箱号
     3.packLevel;--包装等级
     4.flag;--0:扫描最外层箱号;1:不是扫描最外层箱号
     */
    private long ikey;//--到货明细Ikey
    private String lpnNo;//--箱号
    private String packLevel;//--包装等级
    private String flag;//--0:扫描最外层箱号;1:不是扫描最外层箱号

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    public String getPackLevel() {
        return packLevel;
    }

    public void setPackLevel(String packLevel) {
        this.packLevel = packLevel;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
