package com.shqtn.enter.info.impl;

import com.shqtn.enter.info.IDecodeCallbackLoad;
import com.shqtn.enter.presenter.TakeDeliveryManifestPresenter;

/**
 * Created by android on 2017/9/25.
 */

public class DecodeCallbackLoadImpl implements IDecodeCallbackLoad {
    @Override
    public Class getTakeDelManifestListDecodeCallback() {
        return TakeDeliveryManifestPresenter.class;
    }

    @Override
    public Class getTakeDelGoodsListDecodeCallback() {
        return null;
    }
}
