package com.shqtn.enter.utils;

/**
 * Created by android on 2017/10/30.
 */

public class ComputerQualityInspectionStatusQty {

    private double totalQty;//总数量
    private double okQty;//合格数量
    private double backQty;//报废数量
    private double noOkQty;//不合格数量

    private OnNumberChangeListener mListener;

    public void setOnNumberChangeListener(OnNumberChangeListener l) {
        this.mListener = l;
    }

    /**
     * 初始化设置总数量，并赋值给合格数量
     *
     * @param totalQty
     */
    public ComputerQualityInspectionStatusQty(double totalQty) {
        this.totalQty = totalQty;
        okQty = totalQty;
    }

    public void changeBackQty(double backQty) {
        this.backQty = backQty;
        computer();
    }

    public void changeNoOkQty(double noOkQty) {
        this.noOkQty = noOkQty;
        computer();
    }

    private void computer() {
        okQty = totalQty - noOkQty - backQty;
        mListener.onNumberChange(okQty, noOkQty, backQty,totalQty);
    }

    public interface OnNumberChangeListener {
        /**
         * 每次数量改变 调用
         *
         * @param qty
         * @param okQty   合格数量
         * @param noOkQty 不合格数量
         * @param backQty 损坏数量
         */
        void onNumberChange( double okQty, double noOkQty, double backQty,double totalQty);
    }
}
