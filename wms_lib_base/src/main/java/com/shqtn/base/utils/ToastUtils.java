package com.shqtn.base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by android on 2017/7/12.
 */

public class ToastUtils {

    private static Toast sToast;

    public static void show(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void show(Context context, int msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void show(Context context, int msg, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, duration);
        } else {
            sToast.setText(msg);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    public static void show(Context context, String msg, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, duration);
        } else {
            sToast.setText(msg);
            sToast.setDuration(duration);
        }
        sToast.show();
    }
}
