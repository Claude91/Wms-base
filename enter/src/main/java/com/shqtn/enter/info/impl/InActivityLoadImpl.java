package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.enter.RackUpGoodsOperateActivity;
import com.shqtn.enter.activity.in.CheckQuantityManifestOperateActivity;
import com.shqtn.enter.activity.in.GoodsAdjustTargetRackActivity;
import com.shqtn.enter.controller.impl.lpn.RackUpAbstractLpnSubmitPresenter;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.in.GoodsAdjustAddMoveGoodsPresenter;
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
        Bundle presenter = LpnSubmitActivity.createPresenter(RackUpAbstractLpnSubmitPresenter.class);
        bundle.putAll(presenter);
        return LpnSubmitActivity.class;
    }

    @Override
    public Class getRackUpGoodsOperateActivity(Bundle bundle) {
        return RackUpGoodsOperateActivity.class;
    }

    @Override
    public Class getCheckQuantityManifestOperateActivity(Bundle bundle) {
        return CheckQuantityManifestOperateActivity.class;
    }

    @Override
    public Class getGoodsAdjustAddMoveGoodsActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(GoodsAdjustAddMoveGoodsPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }
}
