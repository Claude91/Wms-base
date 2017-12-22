package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/1.
 */

public class RackDownLpnSubmitParams {
    private String whCode; // 仓库编码
    private String docNo; // 单据编号
    private String lpnNo; // 托盘号

    private long pickIkey;//拣货任务IKEY

    private String areaCode;//区域编码

    private String sourceLocCode; // 源货位编码   //  locationCode
    private String targetLocCode; // 目标货位编码

    /**
     * private Long pickIkey; // 拣货任务IKEY

     private String docNo; // 单据编号

     private String lpnNo; // 托盘号

     private String sourceLocCode; // 源货位编码

     private String targetLocCode; // 目标货位编码
     * @return
     */
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    public long getPickIkey() {
        return pickIkey;
    }

    public void setPickIkey(long pickIkey) {
        this.pickIkey = pickIkey;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSourceLocCode() {
        return sourceLocCode;
    }

    public void setSourceLocCode(String sourceLocCode) {
        this.sourceLocCode = sourceLocCode;
    }

    public String getTargetLocCode() {
        return targetLocCode;
    }

    public void setTargetLocCode(String targetLocCode) {
        this.targetLocCode = targetLocCode;
    }
}
