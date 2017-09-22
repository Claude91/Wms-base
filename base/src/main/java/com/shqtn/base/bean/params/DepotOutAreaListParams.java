package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/20.
 */

public class DepotOutAreaListParams {
    private int page;
    private int pageSize;
    private String whcode;

    private String areaFlag = "3";

    public String getAreaFlag() {
        return areaFlag;
    }

    public void setAreaFlag(String areaFlag) {
        this.areaFlag = areaFlag;
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

    public String getWhcode() {
        return whcode;
    }

    public void setWhcode(String whcode) {
        this.whcode = whcode;
    }
}
