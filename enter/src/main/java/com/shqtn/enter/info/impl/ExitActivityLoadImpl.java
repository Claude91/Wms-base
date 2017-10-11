package com.shqtn.enter.info.impl;

import android.os.Bundle;

import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.exit.DepotOutGoodsOperateActivity;
import com.shqtn.enter.activity.exit.RackDownGoodsOperateActivity;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.presenter.exit.DepotOutAreaListPresenter;
import com.shqtn.enter.presenter.exit.DepotOutGoodsListPresenter;
import com.shqtn.enter.presenter.exit.DepotOutManifestListPresenter;
import com.shqtn.enter.presenter.exit.RackDownGoodsListPresenter;
import com.shqtn.enter.presenter.exit.RackDownLpnSubmitPresenter;
import com.shqtn.enter.presenter.exit.RackDownRackListPresenter;

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
}
