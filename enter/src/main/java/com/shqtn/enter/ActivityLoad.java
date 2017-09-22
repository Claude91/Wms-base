package com.shqtn.enter;

import android.app.Activity;

/**
 * Created by android on 2017/9/22.
 */

public class ActivityLoad {
    private static ActivityLoad sActivityLoad;
    public static ActivityLoad getInstance() {
        if (sActivityLoad == null){
            synchronized (ActivityLoad.class){
                if (sActivityLoad == null){
                    sActivityLoad = new ActivityLoad();
                }
            }
        }
        return sActivityLoad;
    }
    private IActivityLoad iActivityLoad;

    public IActivityLoad getActivityLoad(){
        return iActivityLoad;
    }

}
