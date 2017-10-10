package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.enter.QualityInspectionGoodsOperateActivity;
import com.shqtn.enter.activity.enter.RackUpGoodsOperateActivity;
import com.shqtn.enter.activity.enter.TakeDelGoodsOperateActivity;
import com.shqtn.enter.controller.impl.lpn.RackUpAbstractLpnSubmitPresenter;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.enter.TakeDeliveryGoodsPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class EnterActivityLoadImpl implements IActivityLoad.EnterActivityLoad {
    @Override
    public Class getTakeDelGoodsListActivity(Bundle bundle) {
        bundle.putString(C.PRESENTER, TakeDeliveryGoodsPresenter.class.getCanonicalName());
        return ListActivity.class;
    }

    @Override
    public Class getTakeDelGoodsOperateActivity(Bundle bundle) {
        return TakeDelGoodsOperateActivity.class;
    }

    @Override
    public Class getRackUpLpnOperateActivity(Bundle bundle) {
        bundle.putString(C.PRESENTER, RackUpAbstractLpnSubmitPresenter.class.getCanonicalName());
        return LpnSubmitActivity.class;
    }

    @Override
    public Class getRackUpGoodsOperateActivity(Bundle bundle) {
        return RackUpGoodsOperateActivity.class;
    }

    @Override
    public Class getQualityInspectionGoodsOperateActivity(Bundle bundle) {
        return QualityInspectionGoodsOperateActivity.class;
    }
}
