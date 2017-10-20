package com.shqtn.wms.p.impl;

import android.os.Bundle;

import com.shqtn.enter.FunctionBean;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IFunctionLoad;
import com.shqtn.enter.info.impl.*;

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

    private FunctionBean getPackingOutMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("包装出库");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getPackingOutMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getPackingMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("包装");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getPackingMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


    private FunctionBean getQualityInspectionMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("质检");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getQualityInspectionMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getDepotOutMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("出库");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getDepotOutMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getSortingMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("分拣");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getSortingMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getRackDownMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("下架");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getRackDownMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


    private FunctionBean getEnterPalletMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("入托");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getEnterPalletMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getCheckQuantityMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("盘点");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getCheckQuantityMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getGoodsAdjustMain(IActivityLoad.FunctionMainActivityLoad inActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("货位调整");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = inActivityLoad.getGoodsAdjustMainActivity(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getTakeBoxMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("装箱");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getTakeBoxMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getRackUpMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("上架");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getRackUpMainActivity(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getTakeDelMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("收货");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getTakeDelMainActivity(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


}
