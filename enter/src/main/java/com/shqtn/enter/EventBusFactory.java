package com.shqtn.enter;

import com.shqtn.enter.even.DepotEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by android on 2017/9/25.
 */

public class EventBusFactory {


    public static EventBus getDepotEventBus() {
        return DepotEvent.getInstance();
    }

}
