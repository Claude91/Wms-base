package com.shqtn.enter.controller;

import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.controller.presenter.BasePresenter;
import com.shqtn.base.controller.view.IBaseView;
import com.shqtn.base.controller.view.IContext;

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

        void onRefreshComplete();

        void setScanningType(int ...type);
    }

    public interface Presenter extends BasePresenter {

        void init();

        /**
         * 点击item
         *
         * @param position
         */
        void clickItem(int position);

        void setAty(IContext context);

        void setBundle(Bundle bundle);

        void setView(View v);

        void onPullDownToRefresh();

        void onPullUpToRefresh();

        void refresh();


    }
}
