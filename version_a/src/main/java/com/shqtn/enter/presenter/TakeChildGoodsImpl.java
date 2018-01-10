package com.shqtn.enter.presenter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shqtn.base.BaseApp;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.LpnCheck;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.params.TakeBoxSubmitParams;
import com.shqtn.base.controller.view.IDialogView;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2018/1/10
 * 描述:
 * 装箱 ，内部装货品
 *
 * @author ql
 */

public class TakeChildGoodsImpl extends AbstractTakeBoxChild<CodeGoods> implements View.OnClickListener, AdapterView.OnItemClickListener {

    private CommonAdapter<CodeGoods> adapter;
    private IDialogView dialogView;

    private int operatePosition = -1;
    private EditQuantityDialog.OnResultListener onResultListener = new EditQuantityDialog.OnResultListener() {
        @Override
        public void clickOk(double quantity) {
            CodeGoods codeGoods = getChilds().get(operatePosition);
            codeGoods.setQuantity(quantity);
            adapter.update(getChilds());
        }

        @Override
        public void clickCancel() {

        }
    };

    public TakeChildGoodsImpl(@NonNull TakeBoxPlan takeBoxGoodsPlan, @NonNull final TakeBoxGoods takeBoxGoods, IDialogView dialogView) {
        super(takeBoxGoodsPlan, takeBoxGoods);
        this.dialogView = dialogView;
        adapter = new CommonAdapter<CodeGoods>(R.layout.item_take_box_child_goods) {
            @Override
            public void setItemContent(ViewHolder holder, CodeGoods codeGoods, int position) {

                View delView = holder.getViewById(R.id.item_take_box_child_goods_iv_del);
                if (codeGoods.isTag()) {
                    delView.setVisibility(View.GONE);
                } else {
                    delView.setVisibility(View.VISIBLE);
                }

                delView.setTag(position);
                delView.setOnClickListener(TakeChildGoodsImpl.this);
                holder.setLabelText(R.id.item_take_box_child_goods_ltv_name, operateGoods.getUnitName())
                        .setLabelText(R.id.item_take_box_child_goods_ltv_qty, String.valueOf(codeGoods.getGoodsQty()))
                        .setLabelText(R.id.item_take_box_child_goods_ltv_batch_no, codeGoods.getBatchNo())
                        .setLabelText(R.id.item_take_box_child_goods_ltv_unit, operateGoods.getUnitName())
                        .setLabelText(R.id.item_take_box_child_goods_ltv_sku, codeGoods.getSkuCode());
            }
        };
    }


    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public int getDecodeType() {
        return CodeCallback.TAG_GOODS;
    }

    @Override
    public String getHintText() {
        return "请扫描货品编码";
    }

    @Override
    public void setListView(ListView lv) {
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean isCanAdd(CodeGoods goods) {
        return true;
    }


    @Override
    public TakeBoxSubmitParams getOverSubmit() {
        return createSubmitParams(TakeBoxSubmitParams.SUBMIT_FLAG_TAKE_OVER);
    }

    @NonNull
    private TakeBoxSubmitParams createSubmitParams(String flag) {
        TakeBoxSubmitParams params = new TakeBoxSubmitParams();
        params.setOverFlag(flag);
        DepotBean depot = DepotUtils.getDepot(BaseApp.getInstance());
        params.setWhCode(depot.getWhcode());
        List<TakeBoxSubmitParams.SubmitSku> list = new ArrayList<>();
        TakeBoxSubmitParams.SubmitSku submitSku = new TakeBoxSubmitParams.SubmitSku();
        submitSku.setSkuCode(operateGoods.getSkuCode());
        submitSku.setBatchNo(operateGoods.getBatchNo());
        list.add(submitSku);
        params.setSkuList(list);
        ArrayList<CodeGoods> childs = getChilds();
        CodeGoods codeGoods = childs.get(0);
        params.setCountNum(codeGoods.getGoodsQty());
        params.setPackLevel(operateGoodsPlan.getPackLevel());
        params.setBatchNo(operateGoods.getBatchNo());
        params.setDocNo(manifest);
        params.setfLpnNo(getBoxNo());
        return params;
    }

    @Override
    public TakeBoxSubmitParams getSubmit() {
        return createSubmitParams(TakeBoxSubmitParams.SUBMIT_FLAG_TAKE_SUBMIT);
    }

    @Override
    public void addChildren(CodeGoods goods) {
        ArrayList<CodeGoods> childs = getChilds();
        for (CodeGoods child : childs) {
            if (GoodsUtils.isSameNoBatchNo(child, goods)) {
                double quantity = child.getQuantity();
                quantity = NumberUtils.getDouble(quantity + goods.getQuantity());
                child.setQuantity(quantity);
                return;
            }
        }
        childs.add(goods);
        adapter.setNewData(childs);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        removeChildren(tag);
        adapter.update(getChilds());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        operatePosition = position;
        if (getChilds().get(position).isTag()) {
            return;
        }
        dialogView.displayEditQty(onResultListener);
    }

    @Override
    public void setChilds(ArrayList chlids) {
        adapter.setNewData(chlids);
        super.setChilds(chlids);
    }

    @Override
    public void addOverChild(LpnCheck lpnStatus) {

    }
}
