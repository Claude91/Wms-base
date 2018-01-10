package com.shqtn.b.info;

import android.os.Bundle;

import com.shqtn.b.enter.presenter.BQualityInspectionQueryGoodsController;
import com.shqtn.b.enter.presenter.BTakeBoxManifestQueryPresenter;
import com.shqtn.b.enter.presenter.BTakeDeliveryManifestPresenter;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.info.impl.FunctionMainActivityLoadImpl;

/**
 * 创建时间:2017/12/18
 * 描述:
 *
 * @author ql
 */

public class BFunctionMainActivityLoadImpl extends FunctionMainActivityLoadImpl {

    /**
     * 玉田 修改 收货任务中 任务单号列表功能
     *
     * @param bundle
     * @return
     */
    @Override
    public Class getTakeDelMainActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(BTakeDeliveryManifestPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getQualityInspectionMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(BQualityInspectionQueryGoodsController.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

    @Override
    public Class getTakeBoxMain(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(BTakeBoxManifestQueryPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }
}
