package com.shqtn.b.enter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.b.enter.result.BTakeBoxManifest;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.params.TakeBoxPlanParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.InfoLoadUtils;

import java.util.ArrayList;

/**
 * 装箱操作，货品选择包装列表
 */
public class BTakeBoxGoodsSelectActivity extends BaseBActivity {
    private static final int REQUEST_OPERATE_CODE = 0X2;
    private TitleView titleView;
    private LabelTextView ltvName, ltvSku, ltvBatchNo, ltvQty, ltvStd, ltvUnit;
    private ListView lv;

    private TakeBoxGoods mOperateGoods;
    private String mOperateManifest;
    private CommonAdapter<TakeBoxPlan> mPlanAdapter;
    private ArrayList<TakeBoxPlan> mPlanList;

    private BTakeBoxManifest mOperateManifestBean;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_btake_box_goods_select);
    }


    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        mOperateManifestBean = bundle.getParcelable(C.MANIFEST_BEAN);
        mOperateGoods = bundle.getParcelable(C.OPERATE_GOODS);
        mOperateManifest = bundle.getString(C.MANIFEST_STR);
        mPlanAdapter = new CommonAdapter<TakeBoxPlan>(this, null, com.shqtn.enter.R.layout.item_take_box) {
            @Override
            public void setItemContent(ViewHolder holder, TakeBoxPlan takeBoxPlan, int position) {
                int index = position + 1;
                int qty = takeBoxPlan.getQty();
                holder.setText(R.id.item_take_box_tv_index, String.valueOf(index))
                        .setLabelText(R.id.item_take_box_ltv_name, takeBoxPlan.getUnitName())
                        .setLabelText(R.id.item_take_box_ltv_over_qty, String.valueOf(qty))
                        .setLabelText(R.id.item_take_box_ltv_taking_qty, String.valueOf(takeBoxPlan.getInBoxingQty()))
                        .setLabelText(R.id.item_take_box_ltv_unfinished_qty, String.valueOf(takeBoxPlan.getUnBoxedQty()));
            }
        };
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        ltvName = (LabelTextView) findViewById(R.id.take_box_plan_ltv_name);
        ltvSku = (LabelTextView) findViewById(R.id.take_box_plan_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.take_box_plan_ltv_batchNo);
        ltvQty = (LabelTextView) findViewById(R.id.take_box_plan_ltv_qty);
        ltvStd = (LabelTextView) findViewById(R.id.take_box_plan_ltv_std);
        ltvUnit = (LabelTextView) findViewById(R.id.take_box_plan_ltv_unit);

        lv = (ListView) findViewById(R.id.activity_take_box_goods_take_select_lv);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        ltvName.setText(mOperateGoods.getSkuName());
        ltvSku.setText(mOperateGoods.getSkuCode());
        ltvBatchNo.setText(mOperateGoods.getBatchNo());
        ltvStd.setText(mOperateGoods.getStd());
        ltvUnit.setText(mOperateGoods.getUnitName());
        double quantity = mOperateGoods.getQuantity();
        ltvQty.setText(String.valueOf(quantity));

        lv.setAdapter(mPlanAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**
                 * 服务端 陈伟 描述 计划装箱 是已经排序过得，
                 *  服务端 陈伟 要求 ，计划装箱 进行操作时，上一层必须全部包装完成；
                 *  即  包装等级 level 3  那么包装等级 为 level 2  必须全部包装完成,才可进行 level 3 的操作;
                 */

                TakeBoxPlan takeBoxPlan = mPlanList.get(i);
                if (!isCanOperate(takeBoxPlan)) {

                    TakeBoxPlan lastLevel = getLastLevel(takeBoxPlan);
                    String hint;
                    String nowUnitName = takeBoxPlan.getUnitName();
                    if (nowUnitName == null) {
                        nowUnitName = "未知名称";
                    }
                    if (lastLevel != null) {
                        String lastUnitName = lastLevel.getUnitName();
                        if (lastUnitName == null) {
                            lastUnitName = "未知名称";
                        }
                        hint = String.format("包装形式:%s \r\n 未全部装箱，不可进行包装:%s", lastUnitName, nowUnitName);
                    } else {
                        hint = String.format("获得上级包装形式失败,当前包装形式：%s，请联系管理员", nowUnitName);
                    }

                    displayMsgDialog(hint);
                    return;
                }
                toTakeOperateActivity(takeBoxPlan);
            }
        });

    }

    /**
     * 用于判断是否可以进行操作 当前等级
     * 条件  上衣等级包装，必须全部包装完成
     * <p>
     * 服务端 陈伟 告诉我 最低包装等级为 2
     *
     * @return
     */
    private boolean isCanOperate(TakeBoxPlan plan) {

        String packLevel = plan.getPackLevel();
        int level = replaceLevel(packLevel);

        if (level == 2) {
            return true;
        }

        int lastL = level - 1;

        for (TakeBoxPlan takeBoxPlan : mPlanList) {
            int lastLevel = replaceLevel(takeBoxPlan.getPackLevel());
            //判断上一级别 是否 全部装箱完成；
            if (lastL == lastLevel && isPlanTakeBoxOperateOver(takeBoxPlan)) {
                return true;
            }
        }

        return false;
    }

    private TakeBoxPlan getLastLevel(TakeBoxPlan plan) {
        String packLevel = plan.getPackLevel();
        int level = replaceLevel(packLevel);

        if (level == 2) {
            return null;
        }

        int lastL = level - 1;

        for (TakeBoxPlan takeBoxPlan : mPlanList) {
            int lastLevel = replaceLevel(takeBoxPlan.getPackLevel());
            //判断上一级别 是否 全部装箱完成；
            if (lastL == lastLevel) {
                return takeBoxPlan;
            }
        }

        return null;
    }

    /**
     * 判断当前计划是否操作完成
     * 装箱中数量 = 0 ，未装箱数量 = 0；
     *
     * @return
     */
    private boolean isPlanTakeBoxOperateOver(TakeBoxPlan plan) {
        return (plan.getInBoxingQty() == 0) && (plan.getUnBoxedQty() == 0);
    }

    private int replaceLevel(String level) {
        int l;
        try {
            l = Integer.parseInt(level);
        } catch (Exception e) {
            l = -1;
        }
        return l;
    }

    private void toTakeOperateActivity(TakeBoxPlan takeBoxPlan) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.TAKE_BOX_PLAN, takeBoxPlan);
        bundle.putParcelable(C.OPERATE_GOODS, mOperateGoods);
        bundle.putString(C.MANIFEST_STR, mOperateManifest);
        bundle.putParcelable(C.MANIFEST_BEAN, mOperateManifestBean);

        startActivity(BTakeBoxGoodsOperateActivity.class, bundle, REQUEST_OPERATE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_OPERATE_CODE == requestCode) {
            initPlanTakeBoxListData(DepotUtils.getDepot(this));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPlanTakeBoxListData(DepotUtils.getDepot(this));
    }

    /**
     * 初始化当前货品计划装箱类型列表
     *
     * @param depotBean
     */
    private void initPlanTakeBoxListData(DepotBean depotBean) {
        displayProgressDialog("加载数据中");
        TakeBoxPlanParams params = new TakeBoxPlanParams();
        params.setSkuCode(mOperateGoods.getSkuCode());
        params.setBatchNo(mOperateGoods.getBatchNo());
        params.setWhCode(depotBean.getWhcode());
        params.setDocNo(mOperateManifest);
        params.setIkey(mOperateManifestBean.getIkey());
        ModelService.post(ApiUrl.URL_TAKE_BOX_QUERY_PACK_LIST, params, new ResultCallback() {
            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                mPlanList = getRows(response.getData(), TakeBoxPlan.class);
                mPlanAdapter.update(mPlanList);
                cancelProgressDialog();
            }
        });
    }
}
