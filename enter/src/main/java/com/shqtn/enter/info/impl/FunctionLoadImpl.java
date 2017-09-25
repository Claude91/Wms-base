package com.shqtn.enter.info.impl;

import com.shqtn.enter.FunctionBean;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IDecodeCallbackLoad;
import com.shqtn.enter.info.IFunctionLoad;
import com.shqtn.enter.info.IPresenterLoad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/9/25.
 */

public class FunctionLoadImpl implements IFunctionLoad {
    @Override
    public List<FunctionBean> getEnterFunction() {
        ArrayList<FunctionBean> list = new ArrayList<>();

        InfoLoadUtils infoLoadUtils = InfoLoadUtils.getInstance();
        IActivityLoad activityLoad = infoLoadUtils.getActivityLoad();
        IPresenterLoad presenterLoad = infoLoadUtils.getPresenterLoad();
        IDecodeCallbackLoad decodeCallbackLoad = infoLoadUtils.getDecodeCallbackLoad();

        FunctionBean takeDelManifest = new FunctionBean();

        Class takeDelManifestListPresenter = presenterLoad.getTakeDelManifestListPresenter();
        Class takeDelManifestListActivity = activityLoad.getTakeDelManifestListActivity();
        Class takeDelManifestListDecodeCallback = decodeCallbackLoad.getTakeDelManifestListDecodeCallback();
        if (takeDelManifestListActivity != null) {
            takeDelManifest.setAtyClazzName(takeDelManifestListActivity.getCanonicalName());
        }
        if (takeDelManifestListPresenter != null) {
            takeDelManifest.setControllerName(takeDelManifestListPresenter.getCanonicalName());
        }
        if (takeDelManifestListDecodeCallback != null){
            takeDelManifest.setDecodeCallbackName(takeDelManifestListDecodeCallback.getCanonicalName());
        }
        takeDelManifest.setTitle("收货");
        takeDelManifest.setName("收货");
        takeDelManifest.setIconId(com.shqtn.base.R.drawable.home_take_delivery);
        list.add(takeDelManifest);

        return list;
    }

    @Override
    public List<FunctionBean> getExitFunction() {
        ArrayList<FunctionBean> list = new ArrayList<>();

        return list;
    }

    @Override
    public List<FunctionBean> getInDepotFunction() {
        ArrayList<FunctionBean> list = new ArrayList<>();

        return list;
    }
}
