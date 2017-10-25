package com.shqtn.wms.p.impl;

import com.shqtn.enter.FunctionBean;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.IActivityLoad;

import java.util.List;

/**
 * Created by android on 2017/9/25.
 */

public class PFunctionLoadImpl extends com.shqtn.enter.info.impl.FunctionLoadImpl {
    private IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad;

    public PFunctionLoadImpl() {
        functionMainActivityLoad = InfoLoadUtils.getInstance().getFunctionMainActivityLoad();
    }

    @Override
    public void addEnterFunction(List<FunctionBean> list) {

        try {
            //上架
            FunctionBean rackUpMain = getRackUpMain(functionMainActivityLoad);
            list.add(rackUpMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void addInDepotFunction(List<FunctionBean> list) {
        try {
            FunctionBean goodsAdjust = getGoodsAdjustMain(functionMainActivityLoad);
            list.add(goodsAdjust);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FunctionBean checkQuantity = getCheckQuantityMain(functionMainActivityLoad);
            list.add(checkQuantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addExitFunction(List<FunctionBean> mExitFunctionList) {
        //下架
        try {
            FunctionBean rackDownMain = getRackDownMain(functionMainActivityLoad);
            mExitFunctionList.add(rackDownMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            //出库
            FunctionBean depotOutMain = getDepotOutMain(functionMainActivityLoad);
            mExitFunctionList.add(depotOutMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



}
