package com.shqtn.enter.info.impl;

import com.shqtn.enter.HomeActivity;
import com.shqtn.enter.LoginActivity;
import com.shqtn.enter.info.IActivityLoad;

/**
 * Created by android on 2017/10/23.
 */

public class ActivityLoadImpl implements IActivityLoad {
    @Override
    public Class getLoginActivity() {
        return LoginActivity.class;
    }

    @Override
    public Class getHomeActivity() {
        return HomeActivity.class;
    }



}
