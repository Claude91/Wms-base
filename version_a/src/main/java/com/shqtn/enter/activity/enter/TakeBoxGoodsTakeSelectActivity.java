package com.shqtn.enter.activity.enter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shqtn.base.BaseActivity;
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
import com.shqtn.enter.R;

import java.util.ArrayList;

public class TakeBoxGoodsTakeSelectActivity extends BaseActivity {

    private static final int REQUEST_OPERATE_CODE = 0X2;
    private TitleView titleView;
    private LabelTextView ltvName, ltvSku, ltvBatchNo, ltvQty, ltvStd, ltvUnit;
    private ListView lv;

    private TakeBoxGoods mOperateGoods;
    private String mOperateManifest;
    private CommonAdapter<TakeBoxPlan> mPlanAdapter;
    private ArrayList<TakeBoxPlan> mPlanList;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_take_box_goods_take_select);
    }


    @Override
    public void initData() {
        super.initData();
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);
        mPlanAdapter = new CommonAdapter<TakeBoxPlan>(this, null, R.layout.item_take_box) {
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
                TakeBoxPlan takeBoxPlan = mPlanList.get(i);
                toTakeOperateActivity(takeBoxPlan);
            }
        });


    }

    private void toTakeOperateActivity(TakeBoxPlan takeBoxPlan) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.TAKE_BOX_PLAN, takeBoxPlan);
        bundle.putParcelable(C.OPERATE_GOODS,mOperateGoods);
        Class takeBoxTakeOperateActivity = InfoLoadUtils.getInstance().getEnterActivityLoad().getTakeBoxTakeOperateActivity(bundle);
        startActivity(takeBoxTakeOperateActivity, bundle, REQUEST_OPERATE_CODE);
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
