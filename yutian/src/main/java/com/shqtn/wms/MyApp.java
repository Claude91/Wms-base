package com.shqtn.wms;

import com.shqtn.base.utils.MediaPlayUtils;
import com.shqtn.base.utils.VibrateHelper;
import com.shqtn.enter.BaseApp;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.impl.ActivityLoadImpl;
import com.shqtn.enter.info.impl.EnterActivityLoadImpl;
import com.shqtn.enter.info.impl.ExitActivityLoadImpl;
import com.shqtn.enter.info.impl.InActivityLoadImpl;
import com.shqtn.enter.info.impl.OtherActivityLoadImpl;
import com.shqtn.wms.info.YTFunctionLoadImpl;
import com.shqtn.wms.info.YTFunctionMainActivityLoadImpl;


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
        loadConfig.setEnterActivityLoad(new EnterActivityLoadImpl())
                .setExitActivityLoad(new ExitActivityLoadImpl())
                .setInActivityLoad(new InActivityLoadImpl())
                .setFunctionMainLoad(new YTFunctionMainActivityLoadImpl())
                .setOtherActivityLoad(new OtherActivityLoadImpl())
                .setFunctionLoad(new YTFunctionLoadImpl())
                .setActivityLoad(new ActivityLoadImpl());

        VibrateHelper.getInstance().setErrorVibrate(true);
        MediaPlayUtils.getInstance().setOpenErrorPlay(true);

    }


}
