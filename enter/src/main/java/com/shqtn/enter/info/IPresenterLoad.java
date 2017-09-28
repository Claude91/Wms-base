package com.shqtn.enter.info;

/**
 * Created by android on 2017/9/25.
 */

public interface IPresenterLoad {

    Class getTakeDelManifestListPresenter();

    /**
     * 获得货品列表的操作类
     *
     * @return
     */
    Class getTakeDelGoodsListPresenter();

    Class getRackUpGoodsListPresenter();
}
