package com.shqtn.wms.p.impl;

import com.shqtn.enter.info.impl.ActivityLoadImpl;
import com.shqtn.wms.p.change.PLoginActivity;

/**
 * Created by android on 2017/10/23.
 */

public class PActivityLoad extends ActivityLoadImpl {
    @Override
    public Class getLoginActivity() {
        return PLoginActivity.class;
    }
}
