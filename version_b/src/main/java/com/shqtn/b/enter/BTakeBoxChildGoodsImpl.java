package com.shqtn.b.enter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shqtn.b.enter.params.BTakeBoxSubmitParams;
import com.shqtn.b.enter.params.Stockserial;
import com.shqtn.base.BaseApp;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.LpnCheck;
import com.shqtn.base.bean.LpnStatus;
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

public class BTakeBoxChildGoodsImpl extends AbstractBTakeBoxChild<CodeGoods, BTakeBoxSubmitParams> implements View.OnClickListener, AdapterView.OnItemClickListener {

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

    public BTakeBoxChildGoodsImpl(@NonNull TakeBoxPlan takeBoxGoodsPlan, @NonNull final TakeBoxGoods takeBoxGoods, IDialogView dialogView) {
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
                delView.setOnClickListener(BTakeBoxChildGoodsImpl.this);
                holder.setLabelText(R.id.item_take_box_child_goods_ltv_name, operateGoods.getSkuName())
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
        int addMaxQty = getAddMaxQty();
        double nowAddQty = getNowAddQty();
        if (addMaxQty <= nowAddQty) {
            dialogView.toast("已经达到装箱最大数量不可添加");
            return false;
        }
        if (isAddSerial()) {
            String serialNo = goods.getSerialNo();
            if (StringUtils.isEmpty(serialNo)) {
                dialogView.toast("该货品编码中不包含序列号，请重新扫描");
                return false;
            }
        }
        return true;
    }


    /**
     * 是否是序列号管理
     *
     * @return
     */
    @Override
    public boolean isAddSerialButton() {
        return isAddSerial();
    }

    @Override
    public boolean isAddSerial() {
        return "Y".equals(operateGoods.getSerialFlag());
    }

    @Override
    public BTakeBoxSubmitParams getOverSubmit() {
        return createSubmitParams(TakeBoxSubmitParams.SUBMIT_FLAG_TAKE_OVER);
    }

    @Nullable
    private BTakeBoxSubmitParams createSubmitParams(String flag) {


        int conversionRate = getAddMaxQty();
        double submitQty = 0.0d;

        ArrayList<CodeGoods> childs1 = getChilds();
        double totalQty = 0.0d;
        boolean isHasTakeOverQty = false;
        for (CodeGoods codeGoods : childs1) {
            double quantity = codeGoods.getQuantity();
            totalQty = totalQty + quantity;
            if (totalQty > conversionRate) {
                dialogView.displayMsgDialog(String.format("提交数量总量大于当前包装箱子最大数量(最大包装数量=%d)", conversionRate));
                return null;
            }
            if (!codeGoods.isTag()) {
                submitQty = submitQty + codeGoods.getQuantity();
            } else {
                isHasTakeOverQty = true;
            }
        }

        BTakeBoxSubmitParams params = new BTakeBoxSubmitParams();
        if (isAddSerial()) {
            boolean isNowAddSerial = (!(childsSerials == null || childsSerials.size() == 0)) ;
          /*  if (!isHasTakeOverQty) {
                if (!isNowAddSerial) {
                    dialogView.displayMsgDialog("请添加序列号");
                    return null;
                }
            }*/
            if (isNowAddSerial) {
                List<Stockserial> serialList = new ArrayList<>(childsSerials.size());
                for (String serial : childsSerials) {
                    Stockserial e = new Stockserial();
                    e.setSkuCode(operateGoods.getSkuCode());
                    e.setBatchNo(operateGoods.getBatchNo());
                    e.setPackNo(getBoxNo());
                    e.setUdf01(manifest);
                    e.setSerialNo(serial);
                    serialList.add(e);
                }
                params.setSerialList(serialList);
            }

        }


        params.setOverFlag(flag);
        DepotBean depot = DepotUtils.getDepot(BaseApp.getInstance());
        params.setWhCode(depot.getWhcode());
        params.setSkuCode(operateGoods.getSkuCode());
        params.setCountNum(submitQty);
        params.setPackLevel(operateGoodsPlan.getPackLevel());
        params.setBatchNo(operateGoods.getBatchNo());
        params.setDocNo(manifest);
        params.setfLpnNo(getBoxNo());
        params.setIkey(mOpearetManifest.getIkey());
        params.setIkey(operateGoods.getIkey());
        return params;
    }

    private int getAddMaxQty() {
        return operateGoodsPlan.getConversionRate();
    }

    @Override
    public BTakeBoxSubmitParams getSubmit() {
        return createSubmitParams(TakeBoxSubmitParams.SUBMIT_FLAG_TAKE_SUBMIT);
    }

    @Override
    public void addChildren(CodeGoods goods) {
        if (isAddSerialButton()) {
            String serialNo = goods.getSerialNo();
            if (StringUtils.isEmpty(serialNo)) {
                this.dialogView.displayMsgDialog("序列号管控，扫描货品不包含序列号，请重新扫描");
                return;
            }
            if (childsSerials == null) {
                childsSerials = new ArrayList<>();
            }
            if (childsSerials.contains(serialNo)) {
                dialogView.displayMsgDialog(String.format("不能重复添加序列号:%s", serialNo));
                return;
            }
            childsSerials.add(serialNo);
            changeSerials(childsSerials);
            return;
        }


        ArrayList<CodeGoods> childs = getChilds();
        for (CodeGoods child : childs) {
            if (child.isTag()) {
                continue;
            }
            if (GoodsUtils.isSameNoBatchNo(child, goods)) {
                double quantity = child.getQuantity();
                quantity = NumberUtils.getDouble(quantity + goods.getQuantity());
                child.setQuantity(quantity);
                return;
            }
        }
        childs.add(goods);
        adapter.setNewData(childs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        removeChildren(tag);
        adapter.update(getChilds());
    }

    @Override
    public void removeChildren(int position) {


        getChilds().remove(position);
        if (isAddSerial()) {
            ArrayList<String> addSerial = getAddSerial();
            if (addSerial != null) {
                addSerial.clear();
            }
        }

    }

    @Override
    public void removeChildren(CodeGoods goods) {
        getChilds().remove(goods);
        if (isAddSerial()) {
            String serialNo = goods.getSerialNo();
            if (StringUtils.isEmpty(serialNo)) {
                return;
            }
            ArrayList<String> addSerial = getAddSerial();
            for (String s : addSerial) {
                if (serialNo.equals(s)) {
                    addSerial.remove(s);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        operatePosition = position;
        if (getChilds().get(position).isTag()) {
            return;
        }
        if (isAddSerial()) {
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
    public void setChildsSerials(ArrayList<String> childsSerials) {
        super.setChildsSerials(childsSerials);
        changeSerials(childsSerials);

    }

    @Override
    public void addOverChild(LpnCheck lpnCheck) {
        ArrayList<LpnStatus> packSkuList = lpnCheck.getPackSkuList();
        if (packSkuList == null) {
            return;
        }
        getChilds().clear();

        for (LpnStatus lpnStatus : packSkuList) {
            if (StringUtils.isEmpty(lpnStatus.getSkuCode())) {
                return;
            }

            CodeGoods codeGoods = new CodeGoods();
            codeGoods.setBatchNo(lpnStatus.getBatchNo());
            codeGoods.setSkuCode(lpnStatus.getSkuCode());
            codeGoods.setQuantity(lpnStatus.getSkuQty());
            codeGoods.setTag(true);
            getChilds().add(codeGoods);
        }
        adapter.setNewData(getChilds());
        adapter.notifyDataSetChanged();

    }

    private void changeSerials(ArrayList<String> childsSerials) {
        if (!isAddSerial()) {
            return;
        }
        ArrayList<CodeGoods> childs = getChilds();
        for (CodeGoods child : childs) {
            if (!child.isTag()) {
                childs.remove(child);
            }
        }

        if (childsSerials == null || childsSerials.size() == 0) {
            for (CodeGoods child : childs) {
                if (!child.isTag()) {
                    childs.remove(child);
                    break;
                }
            }
            adapter.setNewData(childs);
            adapter.notifyDataSetChanged();
            return;
        }
        CodeGoods goods = null;
        if (childs.size() == 0) {
            goods = new CodeGoods();
            goods.setSkuCode(operateGoods.getSkuCode());
            goods.setBatchNo(operateGoods.getBatchNo());
        } else {
            for (CodeGoods child : childs) {
                if (!child.isTag()) {
                    goods = child;
                }
            }
        }
        if (goods == null) {
            goods = new CodeGoods();
            goods.setBatchNo(operateGoods.getBatchNo());
            goods.setSkuName(operateGoods.getSkuName());
            goods.setSkuCode(operateGoods.getSkuCode());
        }
        goods.setQuantity(childsSerials.size());
        childs.add(goods);
        adapter.setNewData(childs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public double getAddSerialSize() {
        ArrayList<CodeGoods> childs = getChilds();
        double takeOverQty = 0; //提交过得  数量;

        for (CodeGoods child : childs) {
            if (child.isTag()) {
                double quantity = child.getQuantity();
                takeOverQty = takeOverQty + quantity;
            }
        }
        double addSerialSize = getAddMaxQty() - takeOverQty;
        return NumberUtils.getDouble(addSerialSize);
    }

    public double getNowAddQty() {
        ArrayList<CodeGoods> childs = getChilds();
        double nowAddQty = 0.0d;
        for (CodeGoods child : childs) {
            nowAddQty = nowAddQty + child.getQuantity();
        }
        return nowAddQty;
    }
}
