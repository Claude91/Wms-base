package com.shqtn.enter.info.impl;

import android.os.Bundle;
import android.support.annotation.DrawableRes;

import com.shqtn.enter.FunctionBean;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IFunctionLoad;

import java.util.List;

/**
 * 控制 主页功能页面加载
 *
 * @author android strivei_bug@yeah.net
 *         Created by android on 2017/9/25.
 */

public class FunctionLoadImpl implements IFunctionLoad {
    private IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad;

    public FunctionLoadImpl() {
        functionMainActivityLoad = InfoLoadUtils.getInstance().getFunctionMainActivityLoad();
    }

    @Override
    public void addEnterFunction(List<FunctionBean> list) {
        try {
            //收货
            FunctionBean takeDelMain = getTakeDelMain(functionMainActivityLoad);
            list.add(takeDelMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            //上架
            FunctionBean rackUpMain = getRackUpMain(functionMainActivityLoad);
            list.add(rackUpMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            //装箱
            Bundle bundle = new Bundle();
            FunctionBean takeBoxMain = createFunctionBean("装箱", R.drawable.home_take_box
                    , bundle, functionMainActivityLoad.getQualityInspectionMain(bundle));
            list.add(takeBoxMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            //质检
            Bundle bundle = new Bundle();
            FunctionBean qualityInspectionMain = createFunctionBean("质检", R.drawable.home_quanlity_checking
                    , bundle, functionMainActivityLoad.getQualityInspectionMain(bundle));
            list.add(qualityInspectionMain);
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
        try {
            FunctionBean enterPallet = getEnterPalletMain(functionMainActivityLoad);
            list.add(enterPallet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FunctionBean enterPallet = getPalletManagerHaveCodeInMain(functionMainActivityLoad);
            list.add(enterPallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOtherFunction(List<FunctionBean> list) {
        //printBox
        //下架
        try {
            FunctionBean printBox = getPrintBoxMain(functionMainActivityLoad);
            list.add(printBox);
        } catch (NullPointerException e) {
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
            //分拣
            FunctionBean sortingMain = getSortingMain(functionMainActivityLoad);
            mExitFunctionList.add(sortingMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            //包装
            FunctionBean packingMain = getPackingMain(functionMainActivityLoad);
            mExitFunctionList.add(packingMain);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            //包装出库
            FunctionBean packingMain = getPackingOutMain(functionMainActivityLoad);
            mExitFunctionList.add(packingMain);
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

    public FunctionBean getPackingOutMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("包装出库");
        bean.setIconId(com.shqtn.base.R.drawable.bz);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getPackingOutMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getPalletManagerHaveCodeInMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("有号入托");
        bean.setIconId(com.shqtn.base.R.drawable.home_pallet_manager);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getPMHaveCodeInMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


    public FunctionBean getPackingMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("包装");
        bean.setIconId(com.shqtn.base.R.drawable.bz);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getPackingMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean createFunctionBean(String label, @DrawableRes int iconId, Bundle bundle, Class clazz) {
        FunctionBean bean = new FunctionBean();
        bean.setName(label);
        bean.setBundle(bundle);
        bean.setAtyClazzName(clazz.getCanonicalName());
        bean.setIconId(iconId);
        return bean;
    }


    public FunctionBean getDepotOutMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("出库");
        bean.setIconId(com.shqtn.base.R.drawable.home_depot_out);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getDepotOutMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    public FunctionBean getSortingMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("分拣");
        bean.setIconId(com.shqtn.base.R.drawable.home_goods_adjust);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getSortingMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    public FunctionBean getRackDownMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("下架");
        bean.setIconId(com.shqtn.base.R.drawable.home_rack_down);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getRackDownMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


    public FunctionBean getEnterPalletMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("入托");
        bean.setIconId(com.shqtn.base.R.drawable.home_rack_up);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getEnterPalletMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    public FunctionBean getCheckQuantityMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("盘点");
        bean.setIconId(com.shqtn.base.R.drawable.home_quanlity_checking);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getCheckQuantityMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    public FunctionBean getGoodsAdjustMain(IActivityLoad.FunctionMainActivityLoad inActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("货位调整");
        bean.setIconId(com.shqtn.base.R.drawable.home_goods_adjust);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = inActivityLoad.getGoodsAdjustMainActivity(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


    public FunctionBean getRackUpMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("上架");
        bean.setIconId(com.shqtn.base.R.drawable.home_rack_up);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getRackUpMainActivity(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    public FunctionBean getTakeDelMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("收货");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getTakeDelMainActivity(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }

    private FunctionBean getPrintBoxMain(IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad) {
        FunctionBean bean = new FunctionBean();
        bean.setName("打印");
        bean.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        Bundle bundle = bean.getBundle();
        Class takeDelManifestListActivity = functionMainActivityLoad.getPrintBoxMain(bundle);
        bean.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        return bean;
    }


}
