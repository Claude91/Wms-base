package com.shqtn.base.utils;

import java.math.BigDecimal;

/**
 * 质检 中 合格数量，不良品数量，报废数量  之间的改变；
 * Created by Administrator on 2017-2-7.
 */
public class QualityCheckCompute {
    private double total;//总数
    private Result result;

    public QualityCheckCompute() {
        result = new Result();
    }

    public interface OnResultChangeListener {
        void onChangeResult(Result result);
    }

    private OnResultChangeListener mListener;

    public void setOnResultChangeListener(OnResultChangeListener listener) {
        this.mListener = listener;
    }

    public static class Result {
        public double okQty;//合格数量
        public double badQty;//不良品数量
        public double scrapQty;//报废数量
    }

    public static QualityCheckCompute newCompute(double total) {
        QualityCheckCompute compute = new QualityCheckCompute();
        compute.setTotal(total);
        return compute;
    }

    public Result setTotal(double total) {
        this.total = total;
        if (result == null) {
            this.result = new Result();
        }
        result.okQty = total;
        result.scrapQty = 0;
        result.badQty = 0;
        onListener();
        return result;
    }

    public Result getResult() {
        return result;
    }

    public Result setBadQty(double badQty) {
        result.badQty = badQty;
        result.okQty = computeOkQty();
        onListener();
        return result;
    }

    private double computeOkQty() {
        BigDecimal b = new BigDecimal(total - result.badQty - result.scrapQty);
        double f1 = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public Result setScrapQty(double scrapQty) {
        result.scrapQty = scrapQty;
        result.okQty = computeOkQty();
        onListener();
        return result;
    }

    public double getTotal() {
        return total;
    }

    private void onListener() {
        if (mListener != null) {
            mListener.onChangeResult(result);
        }
    }
}
