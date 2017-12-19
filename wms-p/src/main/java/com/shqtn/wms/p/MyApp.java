package com.shqtn.wms.p;

import com.shqtn.base.BaseApp;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.IpChangeUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.wms.p.impl.PActivityLoad;
import com.shqtn.wms.p.impl.PEnterActivityLoadImpl;
import com.shqtn.wms.p.impl.PExitActivityLoadImpl;
import com.shqtn.wms.p.impl.PFunctionLoadImpl;
import com.shqtn.wms.p.impl.PFunctionMainActivityLoadImpl;
import com.shqtn.wms.p.impl.PInActivityLoadImpl;


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
                .setEnterActivityLoad(new PEnterActivityLoadImpl())
                .setExitActivityLoad(new PExitActivityLoadImpl())
                .setInActivityLoad(new PInActivityLoadImpl())
                .setFunctionMainLoad(new PFunctionMainActivityLoadImpl())
                .setFunctionLoad(new PFunctionLoadImpl())
                .setActivityLoad(new PActivityLoad());
        initUrl();

    }

    private void initUrl() {
        String ip = IpChangeUtils.getIp(this);
        String port = IpChangeUtils.getPort(this);
        String base_ = "/q7wms-express-rf/api/rf/wms/";
        ApiUrl.changeBase(ip,port, base_);
    }


}
