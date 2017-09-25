package com.shqtn.enter.presenter;

import com.shqtn.enter.R;
import com.shqtn.enter.controller.impl.ListActivityPresenterImpl;

/**
 * Created by android on 2017/9/25.
 */

public class TakeDeliveryGoodsPresenter extends ListActivityPresenterImpl {

    @Override
    public void init() {
        super.init();
        getView().setTitle(getAty().getString(R.string.take_delivery));

    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
