package com.shqtn.base.utils;

import android.support.annotation.NonNull;

/**
 * Created by android on 2017/7/11.
 */

public class StringUtils {

    public static boolean isEmpty(CharSequence str){
        if (str == null || str.length() == 0){
            return true;
        }
        return false;
    }
    public static boolean isEmpty(@NonNull CharSequence ... str){
        if (str == null){
            return true;
        }
        for (CharSequence s : str) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
}
