package com.shqtn.enter.controller.impl;

import android.os.Bundle;

import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.base.IManifest;
import com.shqtn.base.controller.view.IContext;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.ListActivityController;

import java.util.List;

/**
 * Created by android on 2017/9/22.
 */

public abstract class ListActivityPresenterImpl implements ListActivityController.Presenter,CodeController.DecodeCallback{

    private IContext mContext;

    private ListActivityController.View mView;

    private Bundle mBundle;

    @Override
    public void setAty(IContext context) {
        this.mContext = context;
    }

    @Override
    public void init() {

    }

    @Override
    public void setView(ListActivityController.View v) {
        this.mView = v;
    }

    public IContext getAty() {
        return mContext;
    }

    public ListActivityController.View getView() {
        return mView;
    }

    @Override
    public void clickToBack() {
        mContext.closeActivity();
    }

    @Override
    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public<B extends IManifest> CommonAdapter<B>  createManifestAdapter(){
        return new CommonAdapter<B>(getAty().getContext(),null, R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, B b, int position) {

            }
        };
    }

    @Override
    public void onPullUpToRefresh() {

    }

    @Override
    public void onPullDownToRefresh() {

    }

    @Override
    public void decodeLpn(CodeLpn lpn) {

    }

    @Override
    public void decodeRack(CodeRack rack) {

    }

    @Override
    public void decodeGoods(CodeGoods goods) {

    }

    @Override
    public void decodeManifest(CodeManifest manifest) {

    }

    @Override
    public void decodeOther(AllotBean code) {

    }
}
