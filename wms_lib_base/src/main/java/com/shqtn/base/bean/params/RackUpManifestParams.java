package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/7/18.
 */

public class RackUpManifestParams {
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
    private String whcode;//仓库编码（每个设备都有一个选择仓库功的功能）
    /**
     * 托盘号
     */
    private String palletno;
    /**
     * 货品的存货编码
     */
    private String invcode;
    /**
     * 批次号
     */
    private String batchno;
    /**
     * 理货单号
     */
    private String ccode;

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

    public String getWhcode() {
        return whcode;
    }

    public void setWhcode(String whcode) {
        this.whcode = whcode;
    }

    public String getPalletno() {
        return palletno;
    }

    public void setPalletno(String palletno) {
        this.palletno = palletno;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }
}
