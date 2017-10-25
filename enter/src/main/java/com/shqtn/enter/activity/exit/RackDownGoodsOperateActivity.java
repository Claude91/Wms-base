package com.shqtn.enter.activity.exit;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.RackDownGoods;
import com.shqtn.base.bean.params.RackDownGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;
import com.squareup.okhttp.Request;

public class RackDownGoodsOperateActivity extends BaseActivity implements CodeController.View {

    public SystemEditText setInputCode;
    public TitleView titleView;
    public LabelTextView ltvName, ltvSkuCode, ltvBatchNo, ltvUnit, ltvStd, ltvOverQty, ltvNowQty;
    public EditText etInputQty;
    public TextView tvSubmitF1;
    public double scanningQty;
    public String mOperateManifest;
    public String mOperateRackNo;
    public RackDownGoods mOperateGoods;

    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);

        }

        @Override
        public void decodeOther(AllotBean code) {
            displayMsgDialog("扫描类型不匹配请重新扫描");
        }

    };

    public CodeController.Presenter mCodePresenter = new CodePresenterImpl(this);


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_rack_down_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        scanningQty = getBundle().getDouble(C.SCANNING_GOODS_QTY);
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);
        mOperateRackNo = getBundle().getString(C.RACK_NO);
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);

        mCodePresenter.setDecodeCallback(mDecodeCallback);
        mCodePresenter.setDecodeType(CodeCallback.TAG_GOODS);
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);
        setInputCode = (SystemEditText) findViewById(R.id.rack_down_goods_operate_set_input_code);

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                mCodePresenter.toDecode(content);
            }
        });

        ltvName = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_name);
        ltvSkuCode = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_batchNO);
        ltvUnit = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_unit);
        ltvStd = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_std);
        ltvOverQty = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_over_down_qty);
        ltvNowQty = (LabelTextView) findViewById(R.id.rack_down_goods_operate_ltv_plan_down_qty);

        etInputQty = (EditText) findViewById(R.id.rack_down_goods_operate_et_input_qty);

        tvSubmitF1 = (TextView) findViewById(R.id.rack_down_goods_operate_tv_submit_f1);

        tvSubmitF1.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        double inputQty = scanningQty;
        double canInputMaxQty = NumberUtils.getDouble(mOperateGoods.getQuantity() - mOperateGoods.getPutOffQuantity());
        if (scanningQty <= 0) {
            inputQty = canInputMaxQty;
        }
        etInputQty.setText(String.valueOf(inputQty));

        ltvName.setText(mOperateGoods.getSkuName());
        ltvSkuCode.setText(mOperateGoods.getSkuCode());
        ltvBatchNo.setText(mOperateGoods.getBatchNo());
        ltvUnit.setText(mOperateGoods.getUnitName());
        ltvStd.setText(mOperateGoods.getStd());

        ltvNowQty.setText(String.valueOf(canInputMaxQty));
        ltvOverQty.setText(String.valueOf(mOperateGoods.getPutOffQuantity()));

    }

    @Override
    public void widgetForbid(View v) {
        super.widgetForbid(v);
        int i = v.getId();
        if (i == R.id.rack_down_goods_operate_tv_submit_f1) {
            submitGoods();
        }
    }

    private void submitGoods() {

        if (isSubmit()) {
            displayProgressDialog("提交中");
            double inputQty = getInputQty();
            RackDownGoodsSubmitParams params = new RackDownGoodsSubmitParams();
            params.setAreaCode(mOperateGoods.getAreaCode());
            params.setBatchNo(mOperateGoods.getBatchNo());
            params.setDownQuantity(inputQty);
            params.setDocNo(mOperateManifest);
            params.setLocationCode(mOperateRackNo);
            params.setOwnerId(mOperateGoods.getOwnerId());
            params.setPickingIkey(mOperateGoods.getPickingIkey());
            params.setTargetLocCode(mOperateGoods.getTargetLocCode());
            params.setSkuCode(mOperateGoods.getSkuCode());
            params.setUnitName(mOperateGoods.getUnitName());
            params.setWhCode(DepotUtils.getDepot(this).getWhcode());
            ModelService.post(ApiUrl.URL_RACK_DOWN_QUERY_RACK_PRODUCT_SUBMIT, params, new ResultCallback() {
                @Override
                public void onBefore(Request request) {
                    super.onBefore(request);
                    cancelProgressDialog();
                }

                @Override
                public void onFailed(String msg) {
                    displayMsgDialog(msg);
                }

                @Override
                public void onSuccess(ResultBean response) {
                    ToastUtils.show(RackDownGoodsOperateActivity.this, "提交成功");
                    finish();
                }
            });
        }
    }

    public boolean isSubmit() {
        double inputQty = getInputQty();
        if (inputQty <= 0) {
            displayMsgDialog("请编写正确数量数量");
            return false;
        }
        double planQty = mOperateGoods.getQuantity() - mOperateGoods.getPutOffQuantity();
        planQty = NumberUtils.getDouble(planQty);
        if (planQty < inputQty) {
            displayMsgDialog("超出计划下架数量，请修改数量");
            return false;
        }
        return true;
    }

    private double getInputQty() {
        String inputQty = etInputQty.getText().toString();
        double qty = NumberUtils.getDouble(inputQty);
        return qty;
    }

    @Override
    public void setTitle(String title) {
        titleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        etInputQty.setHint(hint);
    }
}
