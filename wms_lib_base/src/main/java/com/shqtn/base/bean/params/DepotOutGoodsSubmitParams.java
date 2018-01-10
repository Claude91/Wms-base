package com.shqtn.base.bean.params;

import java.util.ArrayList;

import com.shqtn.base.bean.exit.SerialNo;

/**
 * Created by mrqiu on 2017/7/25.
 */

public class DepotOutGoodsSubmitParams {
    private ArrayList<SerialNo> ikeyList;
    private double outQuantity;

    public ArrayList<SerialNo> getIkeyList() {
        return ikeyList;
    }

    public void setIkeyList(ArrayList<SerialNo> ikeyList) {
        this.ikeyList = ikeyList;
    }

    public double getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(double outQuantity) {
        this.outQuantity = outQuantity;
    }
}
