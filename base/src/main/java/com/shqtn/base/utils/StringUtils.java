package com.shqtn.base.utils;

import android.support.annotation.NonNull;

/**
 * Created by android on 2017/7/11.
 */

public class StringUtils {

    public static boolean isEmpty(String str){
        if (str == null || str.isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean isEmpty(@NonNull String ... str){
        if (str == null){
            return true;
        }
        for (String s : str) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
}
