package com.shqtn.enter.even;

/**
 * 创建时间:2017/12/4
 * 描述:
 *
 * @author ql
 */

public class BluetoothAddressEvent {
    private static DepotEvent mInstance;

    public static DepotEvent getInstance() {
        if (mInstance == null) {
            synchronized (DepotEvent.class) {
                if (mInstance == null) {
                    mInstance = new DepotEvent();
                }
            }
        }
        return mInstance;
    }

    public void postMsg(String address) {
        mInstance.post(address);
    }

}
