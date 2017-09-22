package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/7.
 */

public class GoodsAdjustLpnSubmitParams {
    private String whCode; // 仓库编码
    private String lpnNo; // 托盘号
    private String sourceLocCode; // 源货位编码
    private String targetLocCode; // 目标货位编码

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
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
