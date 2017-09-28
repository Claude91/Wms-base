package com.shqtn.wms;

import com.shqtn.base.BaseApp;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.impl.ActivityLoadImpl;
import com.shqtn.enter.info.impl.FunctionLoadImpl;
import com.shqtn.enter.info.impl.PresenterLoadImpl;


/**
 * Created by Administrator on 2016-11-3.
 */
public class MyApp extends BaseApp {
    private static MyApp mMyApp = null;

    public static MyApp getInstance() {
        if (mMyApp == null) {
            synchronized (MyApp.class) {
                if (mMyApp == null) {
                    mMyApp = new MyApp();
                }
            }
        }
        return mMyApp;
    }

    @Override
    public void init() {
        mMyApp = this;
        InfoLoadUtils.Config loadConfig = new InfoLoadUtils.Config();
        loadConfig.setActivityLoad(new ActivityLoadImpl())
                .setPresenterLoad(new PresenterLoadImpl())
                .setFunctionLoad(new FunctionLoadImpl());

    }


}
