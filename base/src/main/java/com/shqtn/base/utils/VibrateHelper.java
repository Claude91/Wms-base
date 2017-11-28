package com.shqtn.base.utils;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * @author ql
 *         创建时间:2017/11/28
 *         描述:
 */

public class VibrateHelper {

    private static VibrateHelper mInstance;

    public static final int NORMAL_TIME = 300;

    private long vTime = NORMAL_TIME;

    private Vibrator vibrator;

    public static VibrateHelper getInstance() {
        if (mInstance == null) {
            synchronized (VibrateHelper.class) {
                if (mInstance == null) {
                    mInstance = new VibrateHelper();
                }
            }
        }
        return mInstance;
    }

    public VibrateHelper(){

    }

    public void init(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void vibrate() {
        vibrator.vibrate(vTime);
    }

    public void setTime(long time) {
        vTime = time;
    }
}
