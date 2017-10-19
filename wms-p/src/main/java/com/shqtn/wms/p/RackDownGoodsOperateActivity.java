package com.shqtn.wms.p;

import android.view.View;

import com.shqtn.base.widget.SystemEditText;

public class RackDownGoodsOperateActivity extends com.shqtn.enter.activity.exit.RackDownGoodsOperateActivity {
    @Override
    public void bindView() {
        super.bindView();
        etInputQty.setClickable(false);
        etInputQty.setSelected(false);
        etInputQty.setFocusable(false);

        setInputCode.setMode(SystemEditText.MODE_HAND);
        setInputCode.setVisibility(View.GONE);
    }
}
