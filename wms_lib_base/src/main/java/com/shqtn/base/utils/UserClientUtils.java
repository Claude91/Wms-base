package com.shqtn.base.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.shqtn.base.C;
import com.shqtn.base.bean.LoginUser;
import com.shqtn.base.bean.UserClientBean;


/**
 * Created by android on 2017/7/13.
 */

public class UserClientUtils {
    static {
        sGson = C.sGson;
    }

    private static final String FILE_NAME = "ACCOUNT";
    private static final String KEY = "USER_CLIENT";
    private static final String KEY_LAST_LOGIN_USER = "last_user";
    private static Gson sGson;


    public static void saveLoginUser(Context context, UserClientBean bean) {
        String userJson = C.sGson.toJson(bean);
        PreferencesUtils.saveString(context, FILE_NAME, KEY, userJson);
    }

    public static UserClientBean getLoginUser(Context context) {
        String userJson = PreferencesUtils.queryString(context, FILE_NAME, KEY);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        return C.sGson.fromJson(userJson, UserClientBean.class);
    }

    public static void clearLoginUser(Context context) {
        PreferencesUtils.deleteKey(context, FILE_NAME,KEY);
    }

    public static void saveLastUser(Context context , LoginUser loginUser){
        String loginUserJson = sGson.toJson(loginUser);
        PreferencesUtils.saveString(context,FILE_NAME,KEY_LAST_LOGIN_USER,loginUserJson);
    }

    public static LoginUser getLastUser(Context context){
        String loginUserJson =PreferencesUtils.queryString(context,FILE_NAME,KEY_LAST_LOGIN_USER);
        if (StringUtils.isEmpty(loginUserJson)){
            return null;
        }
        return sGson.fromJson(loginUserJson,LoginUser.class);
    }


    /**
     * 进行对请求回来的token 进行解析，并设置请求头中
     *
     * @param token
     * @return
     */
    public static String decodeToken(String token) {
        token = Base64Utils.getFromBase64(token);
        //登录成功
        String[] str = token.split("&");
        String tokenBase64 = str[str.length - 1];
        token = Base64Utils.getFromBase64(tokenBase64);
        return token;
    }
}
