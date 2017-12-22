package com.shqtn.base.http;

import com.shqtn.base.R;
import com.shqtn.base.utils.ActivityUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 创建时间:2017/12/6
 * 描述:
 *
 * @author ql
 */

public class ErrorHint {

    public static String getErrorHint(Exception e) {
        String errorHint = null;
        if (e instanceof SocketTimeoutException) {
            errorHint = ActivityUtils.getInstance().getTopAty().getString(R.string.socketTimeOutHint);
        } else if (e instanceof SocketException) {
            errorHint = ActivityUtils.getInstance().getTopAty().getString(R.string.socketException);
        } else if (e instanceof RuntimeException) {
            errorHint = e.getMessage();
        } else {
            errorHint = ActivityUtils.getInstance().getTopAty().getString(R.string.httpLinkFailed);
        }
        return errorHint;
    }
}
