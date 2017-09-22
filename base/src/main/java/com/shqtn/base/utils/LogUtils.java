package com.shqtn.base.utils;


import android.util.Log;

/**
 * Created by Administrator on 2017-7-10.
 */
public class LogUtils {
    private static final String TAG = "wms_log";
    private static boolean isClose = false;

    public static class Params {
        public static void closeLog() {
            isClose = true;
        }
    }

    public static void i(String msg) {
        i(TAG, msg);

    }

    public static void i(String tag, String msg) {
        if (!isClose)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!isClose)
            Log.e(tag, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

}
