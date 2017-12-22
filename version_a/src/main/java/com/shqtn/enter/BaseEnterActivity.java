package com.shqtn.enter;

import android.view.View;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;

/**
 * 创建时间:2017/11/24
 * 描述:
 *
 * @author ql
 */

public abstract class BaseEnterActivity extends BaseActivity {

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
}
