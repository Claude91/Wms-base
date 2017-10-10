package com.shqtn.wms;

import com.shqtn.base.BaseApp;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.impl.EnterActivityLoadImpl;
import com.shqtn.enter.info.impl.ExitActivityLoadImpl;
import com.shqtn.enter.info.impl.FunctionLoadImpl;
import com.shqtn.enter.info.impl.FunctionMainActivityLoadImpl;
import com.shqtn.enter.info.impl.InActivityLoadImpl;


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
        loadConfig
                .setEnterActivityLoad(new EnterActivityLoadImpl())
                .setExitActivityLoad(new ExitActivityLoadImpl())
                .setInActivityLoad(new InActivityLoadImpl())
                .setFunctionMainLoad(new FunctionMainActivityLoadImpl())
                .setFunctionLoad(new FunctionLoadImpl());

    }


}
