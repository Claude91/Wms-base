package com.shqtn.enter;

import com.shqtn.base.BaseActivity;

/**
 * Created by android on 2017/9/22.
 */

public interface IActivityLoad {

    <T extends BaseActivity> Class<T> getGoodsListActivityName();
}
