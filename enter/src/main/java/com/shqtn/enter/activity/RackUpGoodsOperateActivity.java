package com.shqtn.enter.activity;

import android.os.Parcelable;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;

public class RackUpGoodsOperateActivity extends BaseActivity {

    private TitleView titleView;
    private SystemEditText setInputCode;
    private LabelTextView ltvRecommendRack;
    private LabelTextView ltvTargetRack;
    private LabelTextView ltvGoodsName;
    private LabelTextView ltvGoodsSku;
    private LabelTextView ltvBatchNo;
    private LabelTextView ltvStd;
    private LabelTextView ltvUnit;
    private LabelTextView ltvPlanQty;
    private LabelTextView ltvCanRackQty;
    private LabelTextView ltvOperateQty;

    private RackUpGoods mOperateGoods;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_rack_up_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        setInputCode = (SystemEditText) findViewById(R.id.activity_rack_up_goods_operate_set_input_code);
        ltvTargetRack = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_target_rack);
        ltvGoodsName = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_name);
        ltvGoodsSku = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_batch_no);
        ltvStd = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_std);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_unit);
        ltvPlanQty = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_plan_qty);
        ltvCanRackQty = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_can_up_qty);
        ltvOperateQty = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_operate_qty);
        ltvRecommendRack = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_recommend_rack);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        titleView.setOnToBackClickListener(this);
        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {

            }
        });
        ltvRecommendRack.setText(mOperateGoods.getPoscode());
        ltvTargetRack.setText(mOperateGoods.getPoscode());
        double pquantity = mOperateGoods.getPquantity();
        ltvPlanQty.setText(String.valueOf(pquantity));
        double iinsumquantity = mOperateGoods.getIinsumquantity();
        double canUpQty = NumberUtils.getDouble(pquantity - iinsumquantity);
        ltvCanRackQty.setText(String.valueOf(canUpQty));
        ltvGoodsName.setText(mOperateGoods.getSkuName());
        ltvGoodsSku.setText(mOperateGoods.getInvcode());
        ltvBatchNo.setText(mOperateGoods.getBatchno());
        ltvStd.setText(mOperateGoods.getStd());
        ltvUnit.setText(mOperateGoods.getUnitName());
    }
}
