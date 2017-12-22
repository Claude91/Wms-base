package com.shqtn.enter;


import com.shqtn.enter.info.ImageSizeInfo;

/**
 * 创建时间:2017/12/4
 * 描述:
 *
 * @author ql
 */

public abstract class BaseApp extends com.shqtn.base.BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageSizeInfo.initPrintImageSize(this);
    }
}
