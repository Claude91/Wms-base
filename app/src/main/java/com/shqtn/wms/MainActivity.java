package com.shqtn.wms;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.bean.UserClientBean;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.LoginInfo;
import com.shqtn.base.utils.IpChangeUtils;
import com.shqtn.base.utils.LoginUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.UserClientUtils;
import com.shqtn.enter.HomeActivity;
import com.shqtn.enter.LoginActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void setRootView() {

    }

    @Override
    public void initData() {
        super.initData();
        boolean isLogin = LoginUtils.isLogin(getApplicationContext());
        LoginInfo loginInfo = new LoginInfo();
        String ip = IpChangeUtils.getIp(this);
        String post = IpChangeUtils.getPort(this);
        if (!(StringUtils.isEmpty(ip) || StringUtils.isEmpty(post))) {
            ApiUrl.changeBase(ip, post);
        }
        if (isLogin) {
            UserClientBean user = UserClientUtils.getLoginUser(getApplicationContext());
            loginInfo.initLoginAfterHeaderParams(getApplicationContext(), user.getTs(), user.getToken());
            toHomeAty();
        } else {
            //非登录状态
            toLoginAty();
        }
    }


    private void toLoginAty() {
        startActivity(LoginActivity.class);
        finish();
    }

    private void toHomeAty() {
        // Bundle listActivityBundle = ListActivity.createListActivityBundle(TakeDeliveryManifestPresenter.class, TakeDeliveryManifestPresenter.class);
        startActivity(HomeActivity.class);
        finish();
    }
}
