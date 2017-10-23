package com.shqtn.wms.p.impl;

import com.shqtn.enter.LoginActivity;
import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.impl.ActivityLoad;
import com.shqtn.wms.p.change.PLoginActivity;

/**
 * Created by android on 2017/10/23.
 */

public class PActivityLoad extends ActivityLoad {
    @Override
    public Class getLoginActivity() {
        return PLoginActivity.class;
    }
}
