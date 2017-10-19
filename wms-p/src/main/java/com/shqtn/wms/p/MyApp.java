package com.shqtn.wms.p;

import com.shqtn.base.BaseApp;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.IpChangeUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.wms.p.impl.EnterActivityLoadImpl;
import com.shqtn.wms.p.impl.ExitActivityLoadImpl;
import com.shqtn.wms.p.impl.FunctionLoadImpl;
import com.shqtn.wms.p.impl.FunctionMainActivityLoadImpl;
import com.shqtn.wms.p.impl.InActivityLoadImpl;


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
        String ip = IpChangeUtils.getIp(this);
        String port = IpChangeUtils.getPort(this);
        String base_ = "/q7wms-popular-rf/api/rf/wms/";
        ApiUrl.changeBase("192.168.0.252", "052", base_);

    }


}
