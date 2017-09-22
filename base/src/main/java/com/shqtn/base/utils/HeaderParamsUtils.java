package com.shqtn.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;



/**
 * Created by Administrator on 2017-5-9.
 */
public class HeaderParamsUtils {
    public static final String HEADER_FILE_NAME = "hfn";

    public static void save(String key, String value){

    }

    public static boolean save(Context context,Map<String,String> map){
        Set<String> keySet = map.keySet();
        SharedPreferences sp = context.getSharedPreferences(HEADER_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String key:keySet){
            editor.putString(key,map.get(key));
        }
        return editor.commit();
    }
    public static Map<String,String> getHeaderParams(Context context){
        SharedPreferences sp = context.getSharedPreferences(HEADER_FILE_NAME, Context.MODE_PRIVATE);
        Map<String,String> map = (Map<String, String>) sp.getAll();
        return map;
    }
}
