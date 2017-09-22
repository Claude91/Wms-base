package com.shqtn.base.utils;

import android.content.Context;



/**
 * Created by Administrator on 2016-11-4.
 */
public class TokenUtils {
    public final static String SHARED_NAME = "shqton.cn.wms";
    public final static String KEY_TOKEN = "token";
    public static void saveToken(Context context,String token ){
        PreferencesUtils.saveString(context,SHARED_NAME,KEY_TOKEN);
    }
    public static String queryToken(Context context){
        return PreferencesUtils.queryString(context,SHARED_NAME,KEY_TOKEN);
    }



}
