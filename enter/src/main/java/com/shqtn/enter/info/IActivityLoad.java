package com.shqtn.enter.info;

import com.shqtn.base.BaseActivity;

/**
 * Created by android on 2017/9/22.
 */

public interface IActivityLoad {

    /**
     * 收货任务单据号列表
     *
     * @return
     */
    Class getTakeDelManifestListActivity();

    /**
     * 用于收货货品列表显示的Activity
     * <t>只会参入当前任务单据号 存入bundle里面 C.MANIFEST_STR 中获取</t>
     *
     * @return
     */
    Class getTakeDelGoodsListActivity();
}
