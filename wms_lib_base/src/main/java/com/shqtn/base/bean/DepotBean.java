package com.shqtn.base.bean;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017-7-10.
 */
public class DepotBean {

    /**
     * rs : true
     * data : [{"whcode":"01","whname":"手机主辅料仓库"},{"whcode":"02","whname":"PC机材料仓库"}]
     */
    private String whcode;
    private String whname;

    public String getWhcode() {
        return whcode;
    }

    public void setWhcode(String whcode) {
        this.whcode = whcode;
    }

    public String getWhname() {
        return whname;
    }

    public void setWhname(String whname) {
        this.whname = whname;
    }


}
