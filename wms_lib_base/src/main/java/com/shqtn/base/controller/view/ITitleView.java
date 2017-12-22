package com.shqtn.base.controller.view;

import com.shqtn.base.widget.TitleView;

/**
 * Created by android on 2017/10/20.
 */

public interface ITitleView {

    void setTitle(String title);

    void setTitleRightText(String content);

    void setOnTitleRightTextClickListener(TitleView.OnRightTextClickListener l);

    void displayTitleRightView();

    void hideTitleRightView();

}
