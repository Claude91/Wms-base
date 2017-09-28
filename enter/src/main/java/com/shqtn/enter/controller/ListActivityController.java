package com.shqtn.enter.controller;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.controller.presenter.BasePresenter;
import com.shqtn.base.controller.view.IBaseView;
import com.shqtn.base.controller.view.IAty;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

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

        void setRefreshing();

        void displayBtnClear();

        void hideBtnClear();

        void setScanningType(int... type);
    }

    public abstract class Presenter extends DecodeCallbackImpl implements BasePresenter {

        /**
         * 点击item
         *
         * @param position
         */
        public abstract void clickItem(int position);

        public abstract void setAty(IAty context);

        public abstract void setView(View v);

        public abstract void onPullDownToRefresh();

        public abstract void onPullUpToRefresh();

        public abstract void refresh();


        public abstract boolean isOpenStartRefreshing();

        /**
         * 点击清空选择
         */
        public abstract void clickClearSelect();
    }
}
