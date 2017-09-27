package com.shqtn.enter.controller;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.controller.presenter.BasePresenter;
import com.shqtn.base.controller.view.IBaseView;
import com.shqtn.base.controller.view.IAty;

/**
 * Created by android on 2017/9/21.
 */

public interface ListActivityController {

    public interface View extends IBaseView {

        void setAdapter(CommonAdapter adapter);

        /**
         * 设置上下拉加载模式
         *
         * @param mode
         */
        void setListViewModel(PullToRefreshBase.Mode mode);

        void setLabelName(String labelName);

        void setLabelContent(String content);

        /**
         * 显示 一个文本框，在输入框下方
         */
        void displayLabel();

        /**
         * 隐藏文本框
         */
        void hideLabel();


        void onRefreshComplete();

        void setScanningType(int... type);
    }

    public interface Presenter extends BasePresenter {

        /**
         * 点击item
         *
         * @param position
         */
        void clickItem(int position);

        void setAty(IAty context);

        void setView(View v);

        void onPullDownToRefresh();

        void onPullUpToRefresh();

        void refresh();


        boolean isOpenStartRefreshing();
    }
}
