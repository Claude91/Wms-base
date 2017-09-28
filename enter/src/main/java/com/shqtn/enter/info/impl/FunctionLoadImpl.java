package com.shqtn.enter.info.impl;

import android.support.annotation.NonNull;

import com.shqtn.enter.FunctionBean;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IFunctionLoad;
import com.shqtn.enter.info.IPresenterLoad;

import java.util.List;

/**
 * Created by android on 2017/9/25.
 */

public class FunctionLoadImpl implements IFunctionLoad {
    @Override
    public void addEnterFunction(List<FunctionBean> list) {

        InfoLoadUtils infoLoadUtils = InfoLoadUtils.getInstance();
        IActivityLoad activityLoad = infoLoadUtils.getActivityLoad();
        IPresenterLoad presenterLoad = infoLoadUtils.getPresenterLoad();

        FunctionBean takeDelMain = getTakeDelMain(activityLoad, presenterLoad);
        list.add(takeDelMain);

        FunctionBean rackUpMain = getRackUpMain(activityLoad, presenterLoad);
        list.add(rackUpMain);

    }

    /**
     * 添加上架的首页
     *
     * @param activityLoad
     * @param presenterLoad
     * @return
     */
    @NonNull
    private FunctionBean getRackUpMain(IActivityLoad activityLoad, IPresenterLoad presenterLoad) {
        FunctionBean rackUpManifest = new FunctionBean();

        Class rackUpGoodsListPresenter = presenterLoad.getRackUpGoodsListPresenter();
        Class rackUpGoodsListActivity = activityLoad.getRackUpGoodsListActivity();
        if (rackUpGoodsListActivity != null) {
            rackUpManifest.setAtyClazzName(rackUpGoodsListActivity.getCanonicalName());
        }
        if (rackUpGoodsListPresenter != null) {
            rackUpManifest.putPresenterName(rackUpGoodsListPresenter);
        }
        rackUpManifest.setTitle("上架");
        rackUpManifest.setName("上架");
        rackUpManifest.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        return rackUpManifest;
    }

    /**
     * 添加收货的首页
     *
     * @param activityLoad
     * @param presenterLoad
     * @return
     */
    @NonNull
    private FunctionBean getTakeDelMain(IActivityLoad activityLoad, IPresenterLoad presenterLoad) {
        FunctionBean takeDelManifest = new FunctionBean();

        Class takeDelManifestListPresenter = presenterLoad.getTakeDelManifestListPresenter();
        Class takeDelManifestListActivity = activityLoad.getTakeDelManifestListActivity();
        if (takeDelManifestListActivity != null) {
            takeDelManifest.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        }
        if (takeDelManifestListPresenter != null) {
            takeDelManifest.putPresenterName(takeDelManifestListPresenter);
        }
        takeDelManifest.setTitle("收货");
        takeDelManifest.setName("收货");
        takeDelManifest.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        return takeDelManifest;
    }

    @Override
    public void addExitFunction(List<FunctionBean> mExitFunctionList) {

    }

    @Override
    public void addInDepotFunction(List<FunctionBean> mInFunctionList) {
    }
}
