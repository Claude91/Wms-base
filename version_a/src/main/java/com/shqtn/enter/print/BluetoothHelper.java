package com.shqtn.enter.print;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

/**
 * 创建时间:2017/12/4
 * 描述:
 *
 * @author ql
 */

public class BluetoothHelper {
    /**
     * 打开蓝牙设备
     *
     * @param context
     * @param bluetoothAdapter
     */
    public static void openBluetooth(Context context, BluetoothAdapter bluetoothAdapter) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(enableBtIntent);
    }
}
