package com.shqtn.wms.p.change;

import android.view.KeyEvent;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.controller.view.ITitleView;
import com.shqtn.base.listener.OnClickDeleteListener;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.bean.ItemGoods;
import com.shqtn.enter.presenter.in.GoodsAdjustAddMoveGoodsPresenter;

/**
 * Created by android on 2017/10/20.
 */

public class PGoodsAdjustAddMoveGoodsPresenter extends GoodsAdjustAddMoveGoodsPresenter {

    @Override
    public void init() {
        mOperateRackNo = getBundle().getString(C.RACK_NO);

        mGoodsParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        mGoodsParams.setLocCode(mOperateRackNo);
        getView().displayLabel();
        getView().setLabelName("操作货位");
        getView().setLabelContent(mOperateRackNo);


        mGoodsAdapter = new CommonAdapter<ItemGoods>(getAty().getContext(), null, com.shqtn.enter.R.layout.item_goods_adjust) {
            @Override
            public void setItemContent(ViewHolder holder, ItemGoods itemGoods, int position) {
                holder.setLabelText(com.shqtn.enter.R.id.item_goods_adjust_name, itemGoods.getSkuName())
                        .setLabelText(com.shqtn.enter.R.id.item_goods_adjust_sku, itemGoods.getSkuCode())
                        .setLabelText(com.shqtn.enter.R.id.item_goods_adjust_batch_no, itemGoods.getBatchNo())
                        .setLabelText(com.shqtn.enter.R.id.item_goods_adjust_unit, itemGoods.getUnitName())
                        .setLabelText(com.shqtn.enter.R.id.item_goods_adjust_qty, String.valueOf(itemGoods.getAdjQty()));
            }
        };
        getView().setAdapter(mGoodsAdapter);

        getView().setEditTextHint("货品或托盘");

        getBottomView().displayBottomGroup();
        getBottomView().setRightText("下一步(F4)");
        getBottomView().setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到下一个页面
                if (mAddMoveGoodsList.size() == 0) {
                    getView().displayMsgDialog("请添加要移动的货品");
                    return;
                }
                toInputTargetRackActivity();
            }
        });

        getBottomView().setLeftText("清空内容");
        getBottomView().setLeftTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddMoveGoodsList.clear();
                mGoodsAdapter.update(mAddMoveGoodsList);
            }
        });
        getView().onRefreshComplete();
        getView().setListViewModel(PullToRefreshBase.Mode.DISABLED);

        getView().displayEditQtyDelBtn(new OnClickDeleteListener() {
            @Override
            public void clickDelete() {
                mAddMoveGoodsList.remove(mOperateQtyGoods);
                mGoodsAdapter.update(mAddMoveGoodsList);
            }
        });

    }

}
