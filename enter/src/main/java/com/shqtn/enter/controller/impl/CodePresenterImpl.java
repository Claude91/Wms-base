package com.shqtn.enter.controller.impl;

import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.info.code.mode.CodeModel;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.controller.CodeController;

/**
 * Created by android on 2017/9/21.
 */

public class CodePresenterImpl extends CodeCallback implements CodeController.Presenter {

    private CodeController.View mView;

    private CodeController.DecodeCallback mDecodeCallback;


    private boolean isDecoding;

    public CodePresenterImpl(CodeController.View v) {
        this.mView = v;
    }

    @Override
    public void toDecode(String code) {

        if (StringUtils.isEmpty(code)) {
            return;
        }
        if (isDecoding) {
            return;
        }
        isDecoding = true;
        mView.displayProgressDialog("解码中");
        CodeModel.queryCode(code, this);
    }


    @Override
    public void onGoods(CodeGoods goods) {
        if (mDecodeCallback != null)
            mDecodeCallback.decodeGoods(goods);
    }

    @Override
    public void onLpn(CodeLpn lpn) {
        if (mDecodeCallback != null)
            mDecodeCallback.decodeLpn(lpn);
    }

    @Override
    public void onRack(CodeRack rack) {
        if (mDecodeCallback != null)
            mDecodeCallback.decodeRack(rack);
    }

    @Override
    public void onManifest(CodeManifest manifest) {
        if (mDecodeCallback != null)
            mDecodeCallback.decodeManifest(manifest);
    }

    @Override
    public void onFailed(String msg) {
        mView.displayMsgDialog(msg);
    }

    @Override
    public void onAfter() {
        super.onAfter();
        isDecoding = false;
        mView.cancelProgressDialog();
    }

    @Override
    public void onOtherCode(AllotBean codeBean) {
        if (mDecodeCallback != null)
            mDecodeCallback.decodeOther(codeBean);
    }

    @Override
    public void setDecodeType(int... type) {
        setTags(type);
    }

    @Override
    public void setDecodeCallback(CodeController.DecodeCallback decodeCallback) {
        this.mDecodeCallback = decodeCallback;
    }

    @Override
    public void clickToBack() {
    }
}
