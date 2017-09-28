package com.shqtn.enter.info.impl;

import com.shqtn.enter.info.IPresenterLoad;
import com.shqtn.enter.presenter.RackUpGoodsListPresenter;
import com.shqtn.enter.presenter.TakeDeliveryGoodsPresenter;
import com.shqtn.enter.presenter.TakeDeliveryManifestPresenter;

/**
 * Created by android on 2017/9/25.
 */

public class PresenterLoadImpl implements IPresenterLoad {

    @Override
    public Class getTakeDelManifestListPresenter() {
        return TakeDeliveryManifestPresenter.class;
    }

    @Override
    public Class getTakeDelGoodsListPresenter() {
        return TakeDeliveryGoodsPresenter.class;
    }

    @Override
    public Class getRackUpGoodsListPresenter() {
        return RackUpGoodsListPresenter.class;
    }
}
