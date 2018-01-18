package com.shqtn.base.widget.i;

/**
 * 创建时间:2018/1/18
 * 描述:
 *
 * @author ql
 */

public interface IOpenDecodeCamera {

    void openDecodeCamera();

    /**
     * 判断是否可以打开相机
     *
     * @return
     */
    boolean isCanOpenCamera();

    void setOnCameraCallback(OnCameraResultCallback cameraCallback);

}
