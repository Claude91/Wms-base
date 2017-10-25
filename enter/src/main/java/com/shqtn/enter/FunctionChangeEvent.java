package com.shqtn.enter;

import com.shqtn.enter.info.IFunctionLoad;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by android on 2017/9/25.
 */

public class FunctionChangeEvent extends EventBus{
    private static FunctionChangeEvent sFunctionChangeEvent;

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

    public void post(IFunctionLoad event) {
        super.post(event);
    }
}
