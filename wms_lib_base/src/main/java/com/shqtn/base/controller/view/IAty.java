package com.shqtn.base.controller.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by android on 2017/9/22.
 */

public interface IAty {
    /**
     * 启动
     *
     * @param clazz
     */
    void startActivity(Class clazz);

    /**
     * 传值
     *
     * @param clazz
     * @param bundle
     */
    void startActivity(Class clazz, Bundle bundle);

    /**
     * 带返回值
     *
     * @param clazz
     * @param requestCode
     */
    void startActivity(Class clazz, int requestCode);

    /**
     * 传值，又能返回值
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    void startActivity(Class clazz, Bundle bundle, int requestCode);

    /**
     * 传值 返回值，启动模式
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     * @param flags
     */
    void startActivity(Class clazz, Bundle bundle, int requestCode, int flags);

    /**
     * 关闭当前 页面
     */
    void closeActivity();

    /**
     * 获得上下文对象
     *
     * @return
     */
    Context getContext();

    String getString(int id);

    void setResult(int resultCode);

    void setResult(int result, Intent intent);
}
