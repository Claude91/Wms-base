package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.PalletDetailsActivity;
import com.shqtn.enter.activity.enter.RackUpGoodsOperateActivity;
import com.shqtn.enter.activity.in.CheckQuantityManifestOperateActivity;
import com.shqtn.enter.activity.in.GoodsAdjustTargetRackActivity;
import com.shqtn.enter.activity.in.PMHaveCodeAddGoodsOrLpnActivity;
import com.shqtn.enter.controller.impl.lpn.RackUpLpnSubmitPresenter;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.in.GoodsAdjustAddMoveGoodsPresenter;
import com.shqtn.enter.presenter.in.GoodsAdjustLpnSubmitPresenter;

/**
 * 库内操作
 * Created by android on 2017/9/30.
 *
 * @author android strive_bug@yeah.net
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

    @Override
    public Class getPalletManagerHaveCodeInLpnAddGoodsOrLpnActivity(Bundle bundle) {
        return PMHaveCodeAddGoodsOrLpnActivity.class;
    }

    @Override
    public Class getPalletDetailsActivity(Bundle bundle) {
        return PalletDetailsActivity.class;
    }
}
