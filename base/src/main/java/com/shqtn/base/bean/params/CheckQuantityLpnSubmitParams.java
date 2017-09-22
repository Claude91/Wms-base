package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/4.
 */

public class CheckQuantityLpnSubmitParams {
    private Long taskIkey; // 盘点任务单号

    private String docNo; // 盘点单号

    private String lpnNo; // 盘点单号

    public Long getTaskIkey() {
        return taskIkey;
    }

    public void setTaskIkey(Long taskIkey) {
        this.taskIkey = taskIkey;
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
}
