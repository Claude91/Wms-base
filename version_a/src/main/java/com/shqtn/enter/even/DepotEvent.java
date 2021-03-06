package com.shqtn.enter.even;

import com.shqtn.base.bean.DepotBean;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by android on 2017/10/23.
 */

public class DepotEvent extends EventBus {
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
        post(address);
    }

}
