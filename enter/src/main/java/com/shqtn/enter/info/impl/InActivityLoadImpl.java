package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.enter.RackUpGoodsOperateActivity;
import com.shqtn.enter.activity.in.GoodsAdjustTargetRackActivity;
import com.shqtn.enter.controller.impl.lpn.RackUpLpnSubmitPresenter;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.in.GoodsAdjustLpnSubmitPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class InActivityLoadImpl implements IActivityLoad.InActivityLoad {
    @Override
    public Class getGoodsAdjustTargetRackActivity(Bundle bundle) {
        return GoodsAdjustTargetRackActivity.class;
    }

    @Override
    public Class getGoodsAdjustLpnSubmitActivity(Bundle bundle) {
        bundle.putString(C.PRESENTER, GoodsAdjustLpnSubmitPresenter.class.getCanonicalName());
        return LpnSubmitActivity.class;
    }

    @Override
    public Class getRackUpLpnOperateActivity(Bundle bundle) {
        Bundle presenter = LpnSubmitActivity.createPresenter(RackUpLpnSubmitPresenter.class);
        bundle.putAll(presenter);
        return LpnSubmitActivity.class;
    }

    @Override
    public Class getRackUpGoodsOperateActivity(Bundle bundle) {
        return RackUpGoodsOperateActivity.class;
    }
}
