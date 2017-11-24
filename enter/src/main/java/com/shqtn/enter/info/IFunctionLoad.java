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
    void addEnterFunction(List<FunctionBean> list);

    /**
     * 获得出库 功能
     *
     * @return
     */
    void addExitFunction(List<FunctionBean> list);


    /**
     * 获得库内功能
     *
     * @return
     */
    void addInDepotFunction(List<FunctionBean> list);

    /**
     * 添加其他功能
     *
     * @param list
     */
    void addOtherFunction(List<FunctionBean> list);
}
