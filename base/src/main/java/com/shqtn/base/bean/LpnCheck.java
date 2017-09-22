package com.shqtn.base.bean;

import java.util.ArrayList;

/**
 * Created by android on 2017/7/31.
 */

public class LpnCheck {
    // //{"rs":true,"data":{"packSkuList":[]}}
    private ArrayList<LpnStatus> packSkuList;

    public ArrayList<LpnStatus> getPackSkuList() {
        return packSkuList;
    }

    public void setPackSkuList(ArrayList<LpnStatus> packSkuList) {
        this.packSkuList = packSkuList;
    }
}
