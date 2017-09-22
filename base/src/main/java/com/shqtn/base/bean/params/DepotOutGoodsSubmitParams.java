package com.shqtn.base.bean.params;

import java.util.ArrayList;

import com.shqtn.base.bean.exit.SerialNo;

/**
 * Created by mrqiu on 2017/7/25.
 */

public class DepotOutGoodsSubmitParams {
    private ArrayList<SerialNo> ikeyList;
    private double quantity;

    public ArrayList<SerialNo> getIkeyList() {
        return ikeyList;
    }

    public void setIkeyList(ArrayList<SerialNo> ikeyList) {
        this.ikeyList = ikeyList;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
