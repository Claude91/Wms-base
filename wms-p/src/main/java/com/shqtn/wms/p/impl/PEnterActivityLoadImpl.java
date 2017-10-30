package com.shqtn.wms.p.impl;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.enter.QualityInspectionGoodsOperateActivity;
import com.shqtn.enter.activity.enter.RackUpGoodsOperateActivity;
import com.shqtn.enter.activity.enter.TakeBoxGoodsTakeSelectActivity;
import com.shqtn.enter.activity.enter.TakeBoxTakeOperateActivity;
import com.shqtn.enter.activity.enter.TakeDelGoodsOperateActivity;
import com.shqtn.enter.controller.impl.lpn.RackUpLpnSubmitPresenter;
import com.shqtn.enter.presenter.enter.TakeDeliveryGoodsPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class PEnterActivityLoadImpl extends com.shqtn.enter.info.impl.EnterActivityLoadImpl {
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
        bundle.putString(C.PRESENTER, RackUpLpnSubmitPresenter.class.getCanonicalName());
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

    @Override
    public Class getTakeBoxGoodsTakeSelectActivity(Bundle bundle) {
        return TakeBoxGoodsTakeSelectActivity.class;
    }

    @Override
    public Class getTakeBoxTakeOperateActivity(Bundle bundle) {
        return TakeBoxTakeOperateActivity.class;
    }
}
