package com.shqtn.wms;

import com.shqtn.base.BaseApp;


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
    }



}
