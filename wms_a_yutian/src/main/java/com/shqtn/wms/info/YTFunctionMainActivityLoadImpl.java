package com.shqtn.wms.info;

import android.os.Bundle;

import com.shqtn.enter.ListActivity;
import com.shqtn.enter.info.impl.FunctionMainActivityLoadImpl;
import com.shqtn.enter.presenter.exit.DepotOutAreaListPresenter;
import com.shqtn.enter.presenter.exit.RackDownManifestListPresenter;
import com.shqtn.wms.presenter.enter.YTTakeDeliveryManifestPresenter;
import com.shqtn.wms.presenter.exit.YTDepotOutManifestPresenter;
import com.shqtn.wms.presenter.exit.YTRackDownManifestPresenter;

/**
 * 创建时间:2017/12/18
 * 描述:
 *
 * @author ql
 */

public class YTFunctionMainActivityLoadImpl extends FunctionMainActivityLoadImpl {

    /**
     * 玉田 修改 收货任务中 任务单号列表功能
     *
     * @param bundle
     * @return
     */
    @Override
    public Class getTakeDelMainActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(YTTakeDeliveryManifestPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getRackDownMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(YTRackDownManifestPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }
}
