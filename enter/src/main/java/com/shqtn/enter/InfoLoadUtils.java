package com.shqtn.enter;

import com.shqtn.enter.info.IActivityLoad;
import com.shqtn.enter.info.IFunctionLoad;

/**
 * 必须进行初始化，可以控制每个功能页面的 显示，跳转，
 * 控制首页功能页面，<t>FunctionMainActivityLoad 控制页面进行加载，</t>
 *
 * @author android strive_bug@yeah.net
 *         Created by android on 2017/9/22.
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

    private IActivityLoad iActivityLoad;

    public IActivityLoad.FunctionMainActivityLoad getFunctionMainActivityLoad() {
        return functionMainActivityLoad;
    }

    public IActivityLoad.InActivityLoad getInActivityLoad() {
        if (inActivityLoad == null) {
            throw new NullPointerException("请在App 中初始化 InfoLoadUtils.Config   当前IactivityLoad.InActivityLoad未初始化");
        }
        return inActivityLoad;
    }

    public IActivityLoad.EnterActivityLoad getEnterActivityLoad() {
        if (enterActivityLoad == null) {
            throw new NullPointerException("请在App 中初始化 InfoLoadUtils.Config   当前IactivityLoad.EnterActivityLoad");
        }
        return enterActivityLoad;
    }

    public IActivityLoad.ExitActivityLoad getExitActivityLoad() {
        if (exitActivityLoad == null) {
            throw new NullPointerException("请在App 中初始化 InfoLoadUtils.Config   当前IactivityLoad.ExitActivityLoad");
        }
        return exitActivityLoad;
    }

    public IFunctionLoad getFunctionLoad() {
        if (iFunctionLoad == null) {
            throw new NullPointerException("请在App 中初始化 InfoLoadUtils.Config   当前IFunctionLoad ");
        }
        return iFunctionLoad;
    }

    public IActivityLoad getActivityLoad() {
        if (iActivityLoad == null) {
            throw new NullPointerException("请在App 中初始化 InfoLoadUtils.Config   IActivityLoad ");
        }
        return iActivityLoad;
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

        public Config setActivityLoad(IActivityLoad f) {
            getInstance().iActivityLoad = f;
            return this;
        }

    }
}
