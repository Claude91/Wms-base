package com.shqtn.base.model.impl;

import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.model.RackUpGoodsOperateModel;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;

import java.lang.invoke.MethodHandle;

/**
 * Created by android on 2017/9/28.
 */

public class RackUpGoodsOperateModelImpl implements RackUpGoodsOperateModel {

    private String failedHint;

    @Override
    public boolean checkSubmit(double submitQty, RackUpGoods rackUpGoods) {
        double canUpQuantity = getCanUpQuantity(rackUpGoods);
        if (submitQty <= 0) {
            failedHint = "提交数量必须大于0";
            return false;
        }

        if (submitQty > canUpQuantity) {
            failedHint = "提交数量不能大于可提交数量" + canUpQuantity;
            return false;
        }
        return true;
    }

    @Override
    public double getCanUpQuantity(RackUpGoods mOperateGoods) {
        double pquantity = mOperateGoods.getPquantity();
        double iinsumquantity = mOperateGoods.getIinsumquantity();
        return NumberUtils.getDouble(pquantity - iinsumquantity);
    }

    @Override
    public boolean isAddQty(RackUpGoods mOperateGoods, CodeGoods goods, double nowInputQty) {
        double pquantity = mOperateGoods.getPquantity();
        double iinsumquantity = mOperateGoods.getIinsumquantity();
        double goodsQty = GoodsUtils.getGoodsQty(goods);
        double aDouble = NumberUtils.getDouble(pquantity - iinsumquantity - nowInputQty - goodsQty);
        if (aDouble < 0) {
            failedHint = "超出可添加数量";
            return false;
        }
        return true;
    }

    @Override
    public boolean isSame(RackUpGoods mOperateGoods, CodeGoods goods) {
        return GoodsUtils.isSame(mOperateGoods, goods);
    }

    @Override
    public boolean isCanSubmit(double operateQty, String targetRack, RackUpGoods mOperateGoods) {
        double canUpQuantity = getCanUpQuantity(mOperateGoods);
        if (operateQty > canUpQuantity) {
            failedHint = "提交数量大于可提交数量，不能提交";
            return false;
        }

        if (operateQty <= 0) {
            failedHint = "请输入正确提交数量";
            return false;
        }
        return true;
    }

    @Override
    public String getFailedHint() {
        return failedHint;
    }
}
