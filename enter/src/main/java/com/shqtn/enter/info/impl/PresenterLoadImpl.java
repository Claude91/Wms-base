package com.shqtn.enter.info.impl;

import com.shqtn.enter.info.IPresenterLoad;
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
        return null;
    }
}
