package com.shqtn.enter.info.impl;

import com.shqtn.base.BaseActivity;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.info.IActivityLoad;

/**
 * Created by android on 2017/9/25.
 */

public class ActivityLoadImpl implements IActivityLoad {

    @Override
    public Class getTakeDelManifestListActivity() {
        return ListActivity.class;
    }

    @Override
    public Class getTakeDelGoodsListActivity() {
        return null;
    }
}
