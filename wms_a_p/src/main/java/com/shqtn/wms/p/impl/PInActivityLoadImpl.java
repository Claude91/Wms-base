package com.shqtn.wms.p.impl;

import android.os.Bundle;

import com.shqtn.enter.ListActivity;
import com.shqtn.wms.p.change.PGoodsAdjustAddMoveGoodsPresenter;

/**
 * Created by android on 2017/9/30.
 */

public class PInActivityLoadImpl extends com.shqtn.enter.info.impl.InActivityLoadImpl {

    @Override
    public Class getGoodsAdjustAddMoveGoodsActivity(Bundle bundle) {
        Bundle listActivityBundle = ListActivity.createListActivityBundle(PGoodsAdjustAddMoveGoodsPresenter.class);
        bundle.putAll(listActivityBundle);
        return ListActivity.class;
    }

}
