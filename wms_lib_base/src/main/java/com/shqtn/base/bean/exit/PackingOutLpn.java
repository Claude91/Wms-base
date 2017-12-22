package com.shqtn.base.bean.exit;

/**
 * Created by android on 2017/8/10.
 */

public class PackingOutLpn {
//"rs":true,"rows":[{"ikey":10026,"boxCode":"0000006140","packStatus":"2"},{"ikey":10026,"boxCode":"0000006141","packStatus":"2"},{"ikey":10026,"boxCode":"0000006142","packStatus":"2"}
    /**
     * 1.ikey;--包装任务Ikey
     * 2.boxCode;--箱号
     * 3.packStatus;--箱号状态(0：未装箱；1：装箱中；2：装箱完成；3：已出库)
     */

    private String boxCode;
    private long ikey;
    private String packStatus;


    private int type;//用于显示的标记

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public long getIkey() {
        return ikey;
    }

    public void setIkey(long ikey) {
        this.ikey = ikey;
    }

    public String getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(String packStatus) {
        this.packStatus = packStatus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
