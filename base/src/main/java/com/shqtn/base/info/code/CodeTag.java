package com.shqtn.base.info.code;

/**
 * Created by android on 2017/7/14.
 */

public class CodeTag {
    //{"rs":true,"data":[
    // {"flagCode":"W01","flagDesc":"单据号","flagVale":"101"}
    // ,{"flagCode":"W02","flagDesc":"货位","flagVale":"201"}
    // ,{"flagCode":"W03","flagDesc":"LPN","flagVale":"301"}
    // ,{"flagCode":"W04","flagDesc":"SKU","flagVale":"999"}]}
    /**
     * 货位标识
     */
    public static final String FLAG_RACK = "W02";
    /**
     * 货品标识
     */
    public static final String FLAG_PRODUCT = "W04";
    /**
     * 托盘标识
     */
    public static final String FLAG_PALLET = "W03";
    /**
     * 单号标识
     */
    public static final String FLAG_MANIFEST = "W01";


    private String goods = FLAG_PRODUCT;
    private String lpn = FLAG_PALLET;
    private String manifest = FLAG_MANIFEST;
    private String rack = FLAG_RACK;

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }
}
