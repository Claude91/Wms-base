package com.shqtn.enter.controller.impl;

import android.os.Bundle;
import android.os.Parcelable;

import com.shqtn.base.C;
import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.controller.view.IAty;
import com.shqtn.enter.controller.LpnSubmitController;

/**
 * Created by android on 2017/9/28.
 */

public abstract class AbstractLpnSubmitPresenterImpl extends LpnSubmitController.AbstractPresenter {

    private LpnSubmitController.View mView;
    private Bundle mBundle;
    private IAty mAty;


    @Override
    public void setView(LpnSubmitController.View view) {
        this.mView = view;
    }

    @Override
    public void setAty(IAty aty) {
        mAty = aty;
    }

    public IAty getAty() {
        return mAty;
    }

    public LpnSubmitController.View getView() {
        return mView;
    }

    @Override
    public void submit() {

    }

    public Bundle getBundle() {
        return mBundle;
    }

    public Parcelable getOperateLpnBean() {
        return mBundle.getParcelable(C.OPERATE_LPN);
    }

    @Override
    public void init(Bundle bundle) {
        this.mBundle = bundle;
        init();
    }

    public abstract void init();


    @Override
    public void clickToBack() {

    }
}
