package com.shqtn.wms.presenter.exit.params;

/**
 * 创建时间:2017/12/28
 * 描述:
 *
 * @author ql
 */

public class YTDepotOutManifestByGoodsParams {
    // 参数 skuCode，whCode，batchNo
    private String skuCode;
    private String whCode;
    private String batchNo;
    private int    page;
    private int    pageSize;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
