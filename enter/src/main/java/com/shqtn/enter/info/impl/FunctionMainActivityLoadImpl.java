package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.DepotChangeAty;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.enter.QualityInspectionQueryGoodsController;
import com.shqtn.enter.presenter.enter.RackUpGoodsListPresenter;
import com.shqtn.enter.presenter.enter.TakeDeliveryManifestPresenter;
import com.shqtn.enter.presenter.exit.RackDownManifestListPresenter;
import com.shqtn.enter.presenter.in.GoodsAdjustRackPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class FunctionMainActivityLoadImpl implements IActivityLoad.FunctionMainActivityLoad {
    @Override
    public Class getTakeDelMainActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(TakeDeliveryManifestPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getRackUpMainActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(RackUpGoodsListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getGoodsAdjustMainActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(GoodsAdjustRackPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getDepotSelectActivity(Bundle bundle) {
        return DepotChangeAty.class;
    }

    @Override
    public Class getCheckQuantityMain(Bundle bundle) {
        return null;
    }

    @Override
    public Class getEnterPalletMain(Bundle bundle) {
        return null;
    }

    @Override
    public Class getTakeBoxMain(Bundle bundle) {
        return null;
    }

    @Override
    public Class getRackDownMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(RackDownManifestListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getSortingMain(Bundle bundle) {
        return null;
    }

    @Override
    public Class getDepotOutMain(Bundle bundle) {
        return null;
    }

    @Override
    public Class getQualityInspectionMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(QualityInspectionQueryGoodsController.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }
}
