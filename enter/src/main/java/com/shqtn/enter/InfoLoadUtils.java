package com.shqtn.enter;

import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IFunctionLoad;

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

    private IActivityLoad.FunctionMainActivityLoad functionMainActivityLoad;
    private IActivityLoad.InActivityLoad inActivityLoad;
    private IActivityLoad.EnterActivityLoad enterActivityLoad;
    private IActivityLoad.ExitActivityLoad exitActivityLoad;


    private IFunctionLoad iFunctionLoad;

    public IActivityLoad.FunctionMainActivityLoad getFunctionMainActivityLoad() {
        return functionMainActivityLoad;
    }

    public IActivityLoad.InActivityLoad getInActivityLoad() {
        return inActivityLoad;
    }

    public IActivityLoad.EnterActivityLoad getEnterActivityLoad() {
        return enterActivityLoad;
    }

    public IActivityLoad.ExitActivityLoad getExitActivityLoad() {
        return exitActivityLoad;
    }

    public IFunctionLoad getFunctionLoad() {
        return iFunctionLoad;
    }


    public static class Config {

        public Config setFunctionMainLoad(IActivityLoad.FunctionMainActivityLoad f) {
            getInstance().functionMainActivityLoad = f;
            return this;
        }

        public Config setEnterActivityLoad(IActivityLoad.EnterActivityLoad f) {
            getInstance().enterActivityLoad = f;
            return this;
        }

        public Config setExitActivityLoad(IActivityLoad.ExitActivityLoad f) {
            getInstance().exitActivityLoad = f;
            return this;
        }

        public Config setInActivityLoad(IActivityLoad.InActivityLoad f) {
            getInstance().inActivityLoad = f;
            return this;
        }

        public Config setFunctionLoad(IFunctionLoad f) {
            getInstance().iFunctionLoad = f;
            return this;
        }

    }
}
