package com.shqtn.enter.print.preferences;

import android.content.Context;

import com.shqtn.base.utils.PreferencesUtils;

/**
 * 创建时间:2017/12/4
 * 描述:
 *
 * @author ql
 */

public class BluetoothAddressPreferences {
    public static final String FILE_NAME = "BLUETOOTH_ADDRESS";
    public static final String KEY_ADDRESS = "address";

    public static void save(Context context, String address) {
        PreferencesUtils.saveString(context, FILE_NAME, KEY_ADDRESS, address);
    }

    public static String getAddress(Context context) {
        return PreferencesUtils.queryString(context, FILE_NAME, KEY_ADDRESS);
    }
}
