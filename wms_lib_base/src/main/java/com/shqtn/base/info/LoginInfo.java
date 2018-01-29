package com.shqtn.base.info;

import android.content.Context;

import java.util.Map;

import com.shqtn.base.bean.UserClientBean;
import com.shqtn.base.http.OkHttpUtils;
import com.shqtn.base.utils.UserClientUtils;

/**
 * Created by android on 2017/7/11.
 */

public class LoginInfo {
    /**
     * 登录之后设置请求头
     *
     * @param applicationContext
     * @param time
     * @param token
     */
    public static void initLoginAfterHeaderParams(Context applicationContext, long time, String token) {
        AppHeaderParamsInfo.InitParams.init(applicationContext, time, token);
        Map<String, String> headerParams = AppHeaderParamsInfo.getInstance().getHeaderParams();
        OkHttpUtils.addHeaderParams(headerParams);
    }

    /**
     * 未登录的状态设置请求头
     *
     * @param applicationContext
     * @param time
     */
    public void initUnLoginHeaderParams(Context applicationContext, long time) {
        AppHeaderParamsInfo.InitParams.init(applicationContext, time);
        Map<String, String> headerMap = AppHeaderParamsInfo.getInstance().getHeaderParams();
        OkHttpUtils.addHeaderParams(headerMap);
    }

    public void saveUser(Context context, UserClientBean user) {
        UserClientUtils.saveLoginUser(context, user);
    }
}
