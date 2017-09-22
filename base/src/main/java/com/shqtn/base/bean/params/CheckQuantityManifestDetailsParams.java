package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/4.
 */
public class CheckQuantityManifestDetailsParams {

    /**
     * 1.docNo;--盘点单号
     * 2.whCode;--仓库编码
     */
    private String docNo;
    private String whCode;
    private int page;
    private int pageSize;

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
