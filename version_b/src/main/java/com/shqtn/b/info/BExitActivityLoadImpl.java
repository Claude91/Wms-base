package com.shqtn.b.info;

import android.os.Bundle;

import com.shqtn.b.exit.presenter.BRackDownGoodsListPresenter;
import com.shqtn.b.exit.ui.BRackDownGoodsOperateActivity;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.info.impl.ExitActivityLoadImpl;
import com.shqtn.enter.presenter.exit.RackDownGoodsListPresenter;

/**
 * 创建时间:2018/1/24
 * 描述:
 *
 * @author ql
 */

public class BExitActivityLoadImpl extends ExitActivityLoadImpl {


    @Override
    public Class getRackDownGoodsOperateActivity(Bundle bundle) {
        return BRackDownGoodsOperateActivity.class;
    }

    @Override
    public Class getRackDownGoodsListActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(BRackDownGoodsListPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }
}
