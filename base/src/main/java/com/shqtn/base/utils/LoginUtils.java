package com.shqtn.base.utils;

import android.content.Context;

/**
 * Created by android on 2017/7/11.
 */

public class LoginUtils {

    private static final String FILE_NAME = "login_file";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_TOKEN = "TOKEN";
    private static final String KEY_TIME = "time";

    public static boolean isLogin(Context context){
        boolean isLogin = PreferencesUtils.queryBoolean(context,FILE_NAME,KEY_IS_LOGIN,false);
        return  isLogin;
    }

    public static void setLogin(Context context, boolean isLogin){
        PreferencesUtils.saveBoolean(context,FILE_NAME,KEY_IS_LOGIN,isLogin);
    }
    public static void clearAll(Context context){
        PreferencesUtils.deleteFile(context, FILE_NAME);
    }


}
