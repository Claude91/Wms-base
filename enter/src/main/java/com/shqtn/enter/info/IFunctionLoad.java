package com.shqtn.enter.info;

import com.shqtn.enter.FunctionBean;

import java.util.List;

/**
 * Created by android on 2017/9/25.
 */

public interface IFunctionLoad {

    /**
     * 获得入库功能
     *
     * @return
     */
    List<FunctionBean> getEnterFunction();

    /**
     * 获得出库 功能
     *
     * @return
     */
    List<FunctionBean> getExitFunction();


    /**
     * 获得库内功能
     *
     * @return
     */
    List<FunctionBean> getInDepotFunction();
}
