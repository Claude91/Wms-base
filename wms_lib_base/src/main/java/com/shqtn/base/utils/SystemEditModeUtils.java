package com.shqtn.base.utils;

import android.content.Context;

/**
 * Created by android on 2017/9/20.
 */

public class SystemEditModeUtils {

    private static final String FILE_NAME = "system_mode";

    private static final String MODE_KEY  ="mode_key";

    public static void saveMode(Context context,int values){
        PreferencesUtils.saveInt(context,FILE_NAME, MODE_KEY,values);
    }

    public static int getMode(Context context,int normalValue){
        return PreferencesUtils.queryInt(context,FILE_NAME,MODE_KEY,normalValue);
    }
}
