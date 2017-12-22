package com.shqtn.base.bean.params;


import com.shqtn.base.model.TakeDeliveryModel;

import java.util.ArrayList;

/**
 * Created by android on 2017/7/18.
 */

public class TakeDeliveryLpnSubmitParams {
    private String lpnNo;
    private ArrayList<TakeDeliveryModel.GoodsOfLpn> skuList;
    private String whCode;

    public String getLpnNo() {
        return lpnNo;
    }

    public void setLpnNo(String lpnNo) {
        this.lpnNo = lpnNo;
    }

    public ArrayList<TakeDeliveryModel.GoodsOfLpn> getSkuList() {
        return skuList;
    }

    public void setSkuList(ArrayList<TakeDeliveryModel.GoodsOfLpn> skuList) {
        this.skuList = skuList;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }


}
