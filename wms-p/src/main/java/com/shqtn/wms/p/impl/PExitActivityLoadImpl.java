package com.shqtn.wms.p.impl;

import android.os.Bundle;

import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.exit.PackingAddLpnOrGoodsOperateActivity;
import com.shqtn.enter.activity.exit.SortingGoodsOperateActivity;
import com.shqtn.enter.presenter.exit.DepotOutAreaListPresenter;
import com.shqtn.enter.presenter.exit.DepotOutGoodsListPresenter;
import com.shqtn.enter.presenter.exit.DepotOutManifestListPresenter;
import com.shqtn.enter.presenter.exit.PackingLpnListPresenter;
import com.shqtn.enter.presenter.exit.RackDownGoodsListPresenter;
import com.shqtn.enter.presenter.exit.RackDownLpnSubmitPresenter;
import com.shqtn.enter.presenter.exit.RackDownRackListPresenter;
import com.shqtn.enter.presenter.exit.SortingGoodsListPresenter;
import com.shqtn.wms.p.change.PDepotOutGoodsOperateActivity;
import com.shqtn.wms.p.change.PRackDownGoodsOperateActivity;

/**
 * Created by android on 2017/9/30.
 */

public class PExitActivityLoadImpl extends com.shqtn.enter.info.impl.ExitActivityLoadImpl {


    @Override
    public Class getDepotOutGoodsOperateActivity(Bundle bundle) {
        return PDepotOutGoodsOperateActivity.class;
    }

    @Override
    public Class getRackDownGoodsOperateActivity(Bundle bundle) {
        return PRackDownGoodsOperateActivity.class;
    }
}
