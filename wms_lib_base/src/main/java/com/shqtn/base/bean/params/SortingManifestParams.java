package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/31.
 */

public class SortingManifestParams {
    private String whCode;
    private int page;
    private int pageSize;

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
