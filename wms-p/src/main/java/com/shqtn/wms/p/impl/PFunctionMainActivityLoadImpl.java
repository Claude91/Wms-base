package com.shqtn.wms.p.impl;

import android.os.Bundle;

import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.DepotChangeAty;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.enter.QualityInspectionQueryGoodsController;
import com.shqtn.enter.presenter.enter.RackUpGoodsListPresenter;
import com.shqtn.enter.presenter.enter.TakeBoxManifestQueryPresenter;
import com.shqtn.enter.presenter.enter.TakeDeliveryManifestPresenter;
import com.shqtn.enter.presenter.exit.DepotOutAreaListPresenter;
import com.shqtn.enter.presenter.exit.PackingManifestListPresenter;
import com.shqtn.enter.presenter.exit.PackingOutOperatePresenter;
import com.shqtn.enter.presenter.exit.RackDownManifestListPresenter;
import com.shqtn.enter.presenter.exit.SortingManifestListPresenter;
import com.shqtn.enter.presenter.in.CheckQuantityManifestListPresenter;
import com.shqtn.enter.presenter.in.GoodsAdjustRackPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class PFunctionMainActivityLoadImpl extends com.shqtn.enter.info.impl.FunctionMainActivityLoadImpl{
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
        Bundle listActivityBundle = ListActivity.createListActivityBundle(CheckQuantityManifestListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getEnterPalletMain(Bundle bundle) {
        return null;
    }

    @Override
    public Class getTakeBoxMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(TakeBoxManifestQueryPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getRackDownMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(RackDownManifestListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getSortingMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(SortingManifestListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getPackingMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(PackingManifestListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getPackingOutMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(PackingOutOperatePresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getDepotOutMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(DepotOutAreaListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getQualityInspectionMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(QualityInspectionQueryGoodsController.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }
}
