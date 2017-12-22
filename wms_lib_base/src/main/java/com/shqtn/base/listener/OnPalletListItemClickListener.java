package com.shqtn.base.listener;

import android.view.View;

/**
 * Created by Administrator on 2017-2-14.
 */
public interface OnPalletListItemClickListener {
    /**
     *  用于监听 +
     * @param v
     * @param position
     */
    void onClickItemAdd(View v, int position);
    /**
     * 用于监听 -
     */
    void onClickItemMinus(View v, int position);

    /**
     * 用于监听 删除
     * @param v
     * @param position
     */
    void onClickItemDelete(View v, int position);
}
