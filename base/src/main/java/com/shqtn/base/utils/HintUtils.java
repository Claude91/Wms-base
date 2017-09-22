package com.shqtn.base.utils;

import android.support.annotation.NonNull;

/**
 * Created by android on 2017/7/20.
 */

public class HintUtils {
    /**
     * 当不存在这个货品时
     *
     * @param sku
     * @param batchNo
     * @return
     */
    @NonNull
    public static String getNotHaveGoods(String sku, String batchNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("当前任务单中不包含箱子中的一个货品")
                .append("\r\n\t")
                .append("编号:")
                .append(sku)
                .append("\r\n\t")
                .append("批次号:")
                .append(batchNo);
        return sb.toString();
    }

    /**
     * 数量不满足时的 显现
     *
     * @param planQty
     * @param hasQty
     * @return
     */
    @NonNull
    public static String getQuantityIsNotSatisfied(double planQty, double hasQty, String skuName, String skuCode, String batchNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("当前任务单据中数量不足")
                .append("\r\n\t")
                .append("名称:")
                .append(skuName)
                .append("\r\n\t")
                .append("编号:")
                .append(skuCode)
                .append("\r\n\t")
                .append("批次号:")
                .append(batchNo)
                .append("\r\n\t")
                .append("箱子数量:").append(planQty)
                .append("\r\n\t")
                .append("任务单中数量:").append(hasQty);
        return sb.toString();
    }


}
