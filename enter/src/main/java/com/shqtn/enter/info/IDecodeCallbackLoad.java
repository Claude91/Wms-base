package com.shqtn.enter.info;

/**
 * Created by android on 2017/9/25.
 */

public interface IDecodeCallbackLoad {
    /**
     * 收货页面 任务单据解码
     *
     * @return
     */
    Class getTakeDelManifestListDecodeCallback();


    /**
     * 收货页面，货品列表 解码的的回调
     *
     * @return
     */
    Class getTakeDelGoodsListDecodeCallback();
}
