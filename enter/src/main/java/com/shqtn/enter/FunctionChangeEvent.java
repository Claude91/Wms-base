package com.shqtn.enter;

import com.shqtn.enter.frag.FunctionFragment;
import com.shqtn.enter.info.IFunctionLoad;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by android on 2017/9/25.
 */

public class FunctionChangeEvent {
    private static FunctionChangeEvent sFunctionChangeEvent;
    private final EventBus mFunctionEvent = EventBus.builder().build();


    public static FunctionChangeEvent getInstance() {
        if (sFunctionChangeEvent == null) {
            synchronized (FunctionChangeEvent.class) {
                if (sFunctionChangeEvent == null) {
                    sFunctionChangeEvent = new FunctionChangeEvent();
                }
            }
        }
        return sFunctionChangeEvent;
    }

    public void post(IFunctionLoad load) {
        mFunctionEvent.post(load);
    }

    public void unregister(Object o) {
        mFunctionEvent.unregister(o);
    }

    public void register(Object o) {
        mFunctionEvent.register(o);
    }

}
