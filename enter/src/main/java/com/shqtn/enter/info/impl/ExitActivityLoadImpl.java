package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.exit.DepotOutGoodsOperateActivity;
import com.shqtn.enter.activity.exit.PackingAddLpnOrGoodsOperateActivity;
import com.shqtn.enter.activity.exit.RackDownGoodsOperateActivity;
import com.shqtn.enter.activity.exit.SortingGoodsOperateActivity;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.exit.DepotOutAreaListPresenter;
import com.shqtn.enter.presenter.exit.DepotOutGoodsListPresenter;
import com.shqtn.enter.presenter.exit.DepotOutManifestListPresenter;
import com.shqtn.enter.presenter.exit.PackingLpnListPresenter;
import com.shqtn.enter.presenter.exit.RackDownGoodsListPresenter;
import com.shqtn.enter.presenter.exit.RackDownLpnSubmitPresenter;
import com.shqtn.enter.presenter.exit.RackDownRackListPresenter;
import com.shqtn.enter.presenter.exit.SortingGoodsListPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class ExitActivityLoadImpl implements IActivityLoad.ExitActivityLoad {


    @Override
    public Class getRackDownRackListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(RackDownRackListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getRackDownGoodsListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(RackDownGoodsListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getRackDownGoodsOperateActivity(Bundle bundle) {
        return RackDownGoodsOperateActivity.class;
    }

    @Override
    public Class getRackDownLpnSubmitActivity(Bundle bundle) {
        Bundle presenter = LpnSubmitActivity.createPresenter(RackDownLpnSubmitPresenter.class);
        bundle.putAll(presenter);
        return LpnSubmitActivity.class;
    }

    @Override
    public Class getDepotOutAreaListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(DepotOutAreaListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getDepotOutManifestListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(DepotOutManifestListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getDepotOutGoodsOperateActivity(Bundle bundle) {
        return DepotOutGoodsOperateActivity.class;
    }

    @Override
    public Class getDepotOutGoodsListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(DepotOutGoodsListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getSortingGoodsListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(SortingGoodsListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getSortingGoodsOperateActivity(Bundle bundle) {
        return SortingGoodsOperateActivity.class;
    }

    @Override
    public Class getPackingLpnListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(PackingLpnListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getPackingAddLpnOrGoodsOperateActivity(Bundle bundle) {
        return PackingAddLpnOrGoodsOperateActivity.class;
    }
}
