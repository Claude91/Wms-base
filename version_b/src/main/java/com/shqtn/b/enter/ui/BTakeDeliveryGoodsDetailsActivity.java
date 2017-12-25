package com.shqtn.b.enter.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.b.AddSerialActivity;
import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.b.enter.result.BTakeDeliveryManifest;
import com.shqtn.base.C;
import com.shqtn.base.model.TakeDeliveryModel;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;

import java.util.ArrayList;

public class BTakeDeliveryGoodsDetailsActivity extends BaseBActivity implements CodeController.View {

    public static final int REQUEST_ADD_SERIAL = 2;//添加序列号页面

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_btake_delivery_goods_details);
    }

    private SystemEditText setInputCode;
    private LabelTextView ltvOperateManifest;
    private LabelTextView ltvSkuName;
    private LabelTextView ltvSkuCode;
    private LabelTextView ltvPlanQty;
    private LabelTextView ltvUnitName;
    private ViewGroup bottomGroup;
    private EditText etInputBatchNo;
    private TextView tvTakeQty;//用户输入数量
    private TextView tvSubmit;

    private CodeController.Presenter mCodePresenter;
    private ArrayList<String> addSerials;

    private String mOperateManifest;
    private BTakeDeliveryManifest mOperateManifestBean;
    private boolean canEditQty;//

    private TakeDeliveryModel mTakeDeliveryModel;

    private EditQuantityDialog.OnResultListener resultListener = new EditQuantityDialog.OnResultListener() {
        @Override
        public void clickOk(double quantity) {
            tvTakeQty.setText(String.valueOf(quantity));
        }

        @Override
        public void clickCancel() {

        }
    };


    @Override
    public void initData() {
        super.initData();
        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeCallback(this);

        mOperateManifest = getBundle().getString(C.MANIFEST_STR);

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
        ltvSkuCode = (LabelTextView) findViewById(R.id.activity_b_take_delivery_goods_details_ltv_sku);
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

    private void loadDetails(String manifestNo) {

    }

    /**
     * 重置显示货品中的View
     */
    private void resetGoodsView() {
        String NULL_ = "";

        if (isAddSerial()) {
            addBottomSerial();
            canEditQty = false;
        }
        if (mTakeDeliveryModel.isAddBatchNo(mOperateManifestBean.getBatchFlag(), mOperateManifestBean.getBatchNoFlag())) {

        } else {

        }
        setIsInputBatchNo(false);
        String skuCode = mOperateManifestBean.getSkuCode();
        if (StringUtils.isEmpty(skuCode)) {
            skuCode = NULL_;
        }
        String skuName = mOperateManifestBean.getSkuName();
        if (StringUtils.isEmpty(skuName)) {
            skuName = NULL_;
        }
        String batchNo = mOperateManifestBean.getBatchNo();
        if (StringUtils.isEmpty(batchNo)) {
            batchNo = NULL_;
        }
        String unitName = mOperateManifestBean.getUnitName();
        if (StringUtils.isEmpty(unitName)) {
            unitName = NULL_;
        }

        Double pQty = mOperateManifestBean.getPQty();
        if (pQty == null) {
            pQty = 0D;
        }
        String planQty = String.format("%.2f", pQty);
        ltvSkuName.setText(skuName);
        ltvSkuName.setText(skuCode);
        etInputBatchNo.setText(batchNo);
        ltvUnitName.setText(unitName);
        ltvPlanQty.setText(planQty);

        if (isAddSerial()) {
            addBottomSerial();
            canEditQty = false;
        }
    }


    private void displayEditQtyDialog() {
        if (isCanEditQty()) {
            displayEditQty(mOperateManifestBean.getPQty(), resultListener);
        } else {
            displayMsgDialog("当前货品为序列号管控，请添加序列号");
        }
    }

    private boolean isCanEditQty() {
        //如果是序列号管控不可手动编辑数量
        return canEditQty;
    }

    /**
     * 用于判断是否添加序列号
     *
     * @return
     */
    private boolean isAddSerial() {
        return "Y".equals(mOperateManifestBean.getSerialFlag());
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
            displayEditQtyDialog();
        } else if (i == R.id.btn_serial) {
            toAddSerialActivity();
        }
    }

    private void toAddSerialActivity() {
        Bundle bundle = new Bundle();
        AddSerialActivity.put(addSerials, bundle);
        startActivity(AddSerialActivity.class, REQUEST_ADD_SERIAL);
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
    public boolean onKeyF2() {
        displayEditQtyDialog();
        return true;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setEditTextHint(String hint) {

    }


}
