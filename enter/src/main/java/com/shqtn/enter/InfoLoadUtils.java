package com.shqtn.enter;

import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IDecodeCallbackLoad;
import com.shqtn.enter.info.IFunctionLoad;
import com.shqtn.enter.info.IPresenterLoad;

/**
 * Created by android on 2017/9/22.
 */

public class InfoLoadUtils {
    private static InfoLoadUtils sInfoLoad;

    public static InfoLoadUtils getInstance() {
        if (sInfoLoad == null) {
            synchronized (InfoLoadUtils.class) {
                if (sInfoLoad == null) {
                    sInfoLoad = new InfoLoadUtils();
                }
            }
        }
        return sInfoLoad;
    }

    private IActivityLoad iActivityLoad;

    private IPresenterLoad iPresenterLoad;

    private IFunctionLoad iFunctionLoad;

    private IDecodeCallbackLoad iDecodeCallbackLoad;

    public IDecodeCallbackLoad getDecodeCallbackLoad() {
        return iDecodeCallbackLoad;
    }

    public IActivityLoad getActivityLoad() {
        return iActivityLoad;
    }

    public IFunctionLoad getFunctionLoad() {
        return iFunctionLoad;
    }

    public IPresenterLoad getPresenterLoad() {
        return iPresenterLoad;
    }

    public static class Config {

        public Config setActivityLoad(IActivityLoad a) {
            getInstance().iActivityLoad = a;
            return this;
        }

        public Config setPresenterLoad(IPresenterLoad p) {
            getInstance().iPresenterLoad = p;
            return this;
        }

        public Config setFunctionLoad(IFunctionLoad f) {
            getInstance().iFunctionLoad = f;
            return this;
        }

        public Config setDecodeCallbackLoad(IDecodeCallbackLoad d) {
            getInstance().iDecodeCallbackLoad = d;
            return this;
        }

    }
}
