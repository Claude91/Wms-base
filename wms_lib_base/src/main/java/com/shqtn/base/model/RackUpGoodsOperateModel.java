package com.shqtn.base.model;

import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.info.code.CodeGoods;

/**
 * Created by android on 2017/9/28.
 */

public interface RackUpGoodsOperateModel extends BaseModel{

    /**
     * 检查是否可以上架
     *
     * @param submitQty
     * @param rackUpGoods
     * @return
     */
    boolean checkSubmit(double submitQty, RackUpGoods rackUpGoods);


    /**
     * 获得可以上架的数量
     *
     * @param mOperateGoods
     * @return
     */
    double getCanUpQuantity(RackUpGoods mOperateGoods);

    boolean isAddQty(RackUpGoods mOperateGoods, CodeGoods goods ,double nowInputQty);

    boolean isSame(RackUpGoods mOperateGoods, CodeGoods goods);

    boolean isCanSubmit(double operateQty, String targetRack, RackUpGoods mOperateGoods);

}
