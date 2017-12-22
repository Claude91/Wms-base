package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/18.
 */

public class RackDownManifestParams {
    /**
     * 页数中的条数
     */
    private int pageSize;
    /**
     * 页数
     */
    private int page;
    /**
     * 仓库编码
     */
    private String whCode;//仓库编码（每个设备都有一个选择仓库功的功能）

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

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }
}
