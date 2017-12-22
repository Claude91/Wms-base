package com.shqtn.b;

import android.view.View;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.controller.CodeController;

/**
 * 创建时间:2017/12/22
 * 描述:
 *
 * @author ql
 */

public abstract class BaseBActivity extends BaseActivity implements CodeController.DecodeCallback {

    @Override
    public void otherInit() {
        super.otherInit();
        View vEditCode = findViewById(R.id.setEditText);
        if (vEditCode != null && vEditCode instanceof SystemEditText) {
            setInputCode = (SystemEditText) vEditCode;
        }
        View vTitle = findViewById(R.id.titleView);
        if (vTitle != null && vTitle instanceof TitleView) {
            titleView = (TitleView) vTitle;
            titleView.setOnToBackClickListener(this);
        }
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
        displayMsgDialog("扫描类型不匹配");
    }
}
