package com.shqtn.enter.info.impl;

import com.shqtn.base.BaseActivity;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.activity.DepotChangeAty;
import com.shqtn.enter.activity.LpnSubmitActivity;
import com.shqtn.enter.activity.RackUpGoodsOperateActivity;
import com.shqtn.enter.activity.TakeDelGoodsOperateActivity;
import com.shqtn.enter.info.IActivityLoad;

/**
 * Created by android on 2017/9/25.
 */

public class ActivityLoadImpl implements IActivityLoad {

    @Override
    public Class getTakeDelManifestListActivity() {
        return ListActivity.class;
    }

    @Override
    public Class getTakeDelGoodsListActivity() {
        return ListActivity.class;
    }

    @Override
    public Class getTakeDelGoodsOperateActivity() {
        return TakeDelGoodsOperateActivity.class;
    }

    @Override
    public Class getRackUpLpnOperateActivity() {
        return LpnSubmitActivity.class;
    }

    @Override
    public Class getRackUpGoodsOperateActivity() {
        return RackUpGoodsOperateActivity.class;
    }

    @Override
    public Class getRackUpGoodsListActivity() {
        return ListActivity.class;
    }

    @Override
    public Class getDepotSelectActivity() {
        return DepotChangeAty.class;
    }
}
