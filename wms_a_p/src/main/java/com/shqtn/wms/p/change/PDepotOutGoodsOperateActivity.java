package com.shqtn.wms.p.change;

/**
 * Created by android on 2017/10/20.
 */

public class PDepotOutGoodsOperateActivity extends com.shqtn.enter.activity.exit.DepotOutGoodsOperateActivity {

    @Override
    public void initWidget() {

        displayProgressDialog("加载数据中");
        loadIkey();
        ltvName.setText(mOperateGoods.getSkuName());
        ltvSku.setText(mOperateGoods.getSkuCode());
        double quantity = mOperateGoods.getQuantity();
        ltvQty.setText(String.valueOf(quantity));
        mqEtInputQty.setMaxQuantity(quantity);
        mqEtInputQty.setClickable(false);
        mqEtInputQty.setSelected(false);
        mqEtInputQty.setFocusable(false);
        mqEtInputQty.setText(String.valueOf(quantity));
    }
}
