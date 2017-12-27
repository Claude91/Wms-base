package com.shqtn.b;

import com.shqtn.b.enter.EnterUrl;
import com.shqtn.b.info.BFunctionLoadImpl;
import com.shqtn.b.info.BFunctionMainActivityLoadImpl;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.IpChangeUtils;
import com.shqtn.base.utils.MediaPlayUtils;
import com.shqtn.base.utils.VibrateHelper;
import com.shqtn.enter.BaseApp;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.info.impl.ActivityLoadImpl;
import com.shqtn.enter.info.impl.EnterActivityLoadImpl;
import com.shqtn.enter.info.impl.ExitActivityLoadImpl;
import com.shqtn.enter.info.impl.InActivityLoadImpl;
import com.shqtn.enter.info.impl.OtherActivityLoadImpl;

/**
 * 创建时间:2017/12/25
 * 描述:
 *
 * @author ql
 */

public class BaseBApp extends BaseApp implements ApiUrl.OnIpChangeListener {
    private static BaseBApp mInstance;

    public static BaseBApp getInstance() {
        return mInstance;
    }

    @Override
    public void init() {
        mInstance = this;
        InfoLoadUtils.Config loadConfig = new InfoLoadUtils.Config();
        loadConfig.setEnterActivityLoad(new EnterActivityLoadImpl())
                .setExitActivityLoad(new ExitActivityLoadImpl())
                .setInActivityLoad(new InActivityLoadImpl())
                .setFunctionMainLoad(new BFunctionMainActivityLoadImpl())
                .setOtherActivityLoad(new OtherActivityLoadImpl())
                .setFunctionLoad(new BFunctionLoadImpl())
                .setActivityLoad(new ActivityLoadImpl());

        VibrateHelper.getInstance().setErrorVibrate(true);
        MediaPlayUtils.getInstance().setOpenErrorPlay(true);
        String ip = IpChangeUtils.getIp(this);
        String port = IpChangeUtils.getPort(this);

        ApiUrl.reg(this);
        ApiUrl.changeBase(ip, port);
    }

    @Override
    public void onChangeUrl() {
        //每次改变url时调用
        EnterUrl.changeUrl(ApiUrl.BASE_URL);

    }
}
