package com.shqtn.b.enter.ui;

import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.BaseEnterActivity;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.enter.TakeDelGoodsOperateController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.presenter.enter.TakeDeliveryGoodsOperatePresenter;

public class BTakeDeliveryGoodsDetailsActivity extends BaseBActivity implements CodeController.View {

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_btake_delivery_goods_details);
    }

    private SystemEditText setInputCode;
    private LabelTextView ltvOperateManifest;
    private LabelTextView ltvSkuName;
    private LabelTextView ltvPlanQty;
    private LabelTextView ltvUnitName;
    private ViewGroup bottomGroup;
    private EditText etInputBatchNo;
    private TextView tvTakeQty;//用户输入数量
    private TextView tvSubmit;

    private CodeController.Presenter mCodePresenter;


    @Override
    public void initData() {
        super.initData();
        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeCallback(this);
    }

    @Override
    public void bindView() {
        super.bindView();
        setInputCode = (SystemEditText) findViewById(R.id.activity_b_take_delivery_goods_details_set_input_code);
        ltvOperateManifest = (LabelTextView) findViewById(R.id.activity_b_take_delivery_goods_details_ltv_operate_manifest);
        ltvPlanQty = (LabelTextView) findViewById(R.id.activity_b_take_delivery_goods_details_ltv_plan_qty);
        ltvSkuName = (LabelTextView) findViewById(R.id.activity_b_take_delivery_goods_details_ltv_name);
        etInputBatchNo = (EditText) findViewById(R.id.activity_b_take_delivery_goods_details_et_input_batch_no);
        tvTakeQty = (TextView) findViewById(R.id.activity_b_take_delivery_goods_details_tv_take_qty);
        tvSubmit = (TextView) findViewById(R.id.activity_b_take_delivery_goods_details_tv_submit);
        ltvUnitName = (LabelTextView) findViewById(R.id.activity_b_take_delivery_goods_details_ltv_unit_name);

        bottomGroup = (ViewGroup) findViewById(R.id.activity_b_take_delivery_goods_details_bottom_group);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                mCodePresenter.toDecode(content);
            }
        });
        tvSubmit.setOnClickListener(this);
        titleView.setOnToBackClickListener(this);
        tvTakeQty.setOnClickListener(this);
        addBottomSerial();

    }

    private void addBottomSerial() {
        TextView tvSerial = new TextView(this);
        tvSerial.setText("添加序列号");
        tvSerial.setOnClickListener(this);
        tvSerial.setOnClickListener(this);
        tvSerial.setId(R.id.btn_serial);
        tvSerial.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        tvSerial.setLayoutParams(params);
        tvSerial.setTextColor(ContextCompat.getColor(this, R.color.text_white_color));
        tvSerial.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        bottomGroup.addView(tvSerial);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_b_take_delivery_goods_details_tv_submit) {
        } else if (i == R.id.activity_b_take_delivery_goods_details_tv_take_qty) {
        }else if (i == R.id.btn_serial){

        }
    }


    public void setIsInputBatchNo(boolean isInputBatchNo) {
        if (!isInputBatchNo) {
            etInputBatchNo.setFocusable(false);

            etInputBatchNo.setFocusableInTouchMode(false);
        } else {
            etInputBatchNo.setFocusableInTouchMode(true);

            etInputBatchNo.setFocusable(true);

            etInputBatchNo.requestFocus();
        }
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setEditTextHint(String hint) {

    }


}
