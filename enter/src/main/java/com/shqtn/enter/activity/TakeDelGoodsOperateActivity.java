package com.shqtn.enter.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.enter.TakeDelGoodsOperateController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.presenter.TakeDeliveryGoodsOperatePresenter;

public class TakeDelGoodsOperateActivity extends BaseActivity implements TakeDelGoodsOperateController.View, CodeController.View {

    private TitleView titleView;
    private SystemEditText setInputCode;
    private LabelTextView ltvOperateManifest;
    private LabelTextView ltvSkuName;
    private LabelTextView ltvPlanQty;
    private LabelTextView ltvUnitName;
    private EditText etInputBatchNo;
    private TextView tvTakeQty;//用户输入数量
    private TextView tvSubmit;

    private TakeDelGoodsOperateController.Presenter mPresenter;
    private CodeController.Presenter mCodePresenter;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_take_del_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mCodePresenter = new CodePresenterImpl(this);
        mPresenter = new TakeDeliveryGoodsOperatePresenter(this, this);
        if (mPresenter instanceof CodeController.DecodeCallback) {
            mCodePresenter.setDecodeCallback((CodeController.DecodeCallback) mPresenter);
        }
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        setInputCode = (SystemEditText) findViewById(R.id.take_del_goods_operate_set_input_code);
        ltvOperateManifest = (LabelTextView) findViewById(R.id.take_del_goods_operate_ltv_operate_manifest);
        ltvPlanQty = (LabelTextView) findViewById(R.id.take_del_goods_operate_ltv_plan_qty);
        ltvSkuName = (LabelTextView) findViewById(R.id.take_del_goods_operate_ltv_name);
        etInputBatchNo = (EditText) findViewById(R.id.take_del_goods_operate_et_input_batch_no);
        tvTakeQty = (TextView) findViewById(R.id.take_del_goods_operate_tv_take_qty);
        tvSubmit = (TextView) findViewById(R.id.take_del_goods_operate_tv_submit);
        ltvUnitName = (LabelTextView) findViewById(R.id.take_del_goods_operate_ltv_unit_name);
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

        mPresenter.init(getBundle());

    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.take_del_goods_operate_tv_submit) {
            mPresenter.submit();
        } else if (i == R.id.take_del_goods_operate_tv_take_qty) {
            mPresenter.clickToEditQty();
        }
    }

    @Override
    public void setOperateManifest(String manifest) {
        ltvOperateManifest.setText(manifest);
    }

    @Override
    public void setGoodsName(String name) {
        ltvSkuName.setText(name);
    }

    @Override
    public void setIsInputBatchNo(boolean isInputBatchNo) {
        if (isInputBatchNo) {
            etInputBatchNo.setFocusable(false);

            etInputBatchNo.setFocusableInTouchMode(false);
        } else {
            etInputBatchNo.setFocusableInTouchMode(true);

            etInputBatchNo.setFocusable(true);

            etInputBatchNo.requestFocus();
        }
    }

    @Override
    public void clickBack() {
        mPresenter.clickToBack();
    }

    @Override
    public void setInputBatchNo(String s) {
        etInputBatchNo.setText(s);
    }

    @Override
    public void setTakeQty(String takeQty) {
        tvTakeQty.setText(takeQty);
    }

    @Override
    public void setPlanQty(String planQty) {
        ltvPlanQty.setText(planQty);
    }

    @Override
    public String getTakeQty() {
        return tvTakeQty.getText().toString();
    }

    @Override
    public void setInputBatchNoHint(String hint) {
        etInputBatchNo.setHint(hint);
    }

    @Override
    public void setUnit(String unitName) {
        ltvUnitName.setText(unitName);
    }

    @Override
    public String getEtInputBatchNo() {
        return etInputBatchNo.getText().toString();
    }

    @Override
    public void setDecodeType(int... type) {
        mCodePresenter.setDecodeType(type);
    }

    @Override
    public void setTitle(String title) {
        titleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        setInputCode.setHintText(hint);
    }
}
