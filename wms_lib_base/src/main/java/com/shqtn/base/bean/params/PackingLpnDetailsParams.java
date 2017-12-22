package com.shqtn.base.bean.params;

/**
 * Created by android on 2017/8/2.
 */

public class PackingLpnDetailsParams {
    /**
     * 1.boxCode;--箱号
     2.packStatus; --箱号状态(0：未装箱；1：装箱中；2：装箱完成；3：已出库)
     */
    private String boxCode;

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

}
