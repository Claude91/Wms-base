package com.shqtn.b.exit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.b.SerialAddActivity;
import com.shqtn.b.enter.ViewInfo;
import com.shqtn.b.exit.params.BRackDownGoodsSubmitParams;
import com.shqtn.b.exit.result.BRackDownGoods;
import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.params.RackDownGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;


public class BRackDownGoodsOperateActivity extends BaseBActivity implements CodeController.View {
    public static final int REQUEST_ADD_SERIAL = 2;

    public SystemEditText setInputCode;
    public TitleView titleView;
    public LabelTextView ltvName, ltvSkuCode, ltvBatchNo, ltvUnit, ltvStd, ltvOverQty, ltvNowQty;
    public TextView tvInputQty;
    public TextView tvSubmitF1;
    public double scanningQty;
    public String mOperateManifest;
    public String mOperateRackNo;
    public BRackDownGoods mOperateGoods;
    public ViewGroup bottomGroup;

    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);

            if (!GoodsUtils.isSame(mOperateGoods, goods)) {
                String batchNo = goods.getBatchNo();
                String skuCode = goods.getSkuCode();
                if (skuCode == null) {
                    skuCode = "未知";
                }
                if (batchNo == null) {
                    batchNo = "无批次号";
                }
                displayMsgDialog(String.format("扫描货品编号：%s,批次号:%s\r\n不匹配，请重新扫描",
                        skuCode, batchNo));
                return;
            }
            if (isSerialOperate()) {
                String serialNo = goods.getSerialNo();
                if (StringUtils.isEmpty(serialNo)) {
                    displayMsgDialog("货品无序列号，请重新扫描");
                    return;
                }

                if (addSerial == null) {
                    addSerial = new ArrayList<>();
                }

                if (srcSerials == null) {
                    displayMsgDialog("当前无序列号可提交");
                    return;
                }

                if (!isHasSerialFromSrc(serialNo)) {
                    displayMsgDialog(String.format("序列号：%s不可添加", serialNo));
                    return;
                }

                if (isAddSerial(serialNo)) {
                    displayMsgDialog(String.format("序列号：%s已经添加过，不可重复添加", serialNo));
                    return;
                }

                addSerial.add(serialNo);

                tvInputQty.setText(String.valueOf(addSerial.size()));
                toast("添加成功");
                return;


            }

            CharSequence text = tvInputQty.getText();
            double nowQty = NumberUtils.getDouble(text.toString());
            double goodsQty = goods.getGoodsQty();
            if (goodsQty <= 0) {
                goodsQty = 1;
            }
            nowQty = nowQty + goodsQty;

            tvInputQty.setText(String.format("%.2f", nowQty));
            toast("添加成功");
        }

        @Override
        public void decodeOther(AllotBean code) {
            displayMsgDialog("扫描类型不匹配请重新扫描");
        }

    };

    private boolean isAddSerial(String serialNo) {
        for (String s : addSerial) {
            if (serialNo.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHasSerialFromSrc(String serialNo) {
        for (String srcSerial : srcSerials) {
            if (serialNo.equals(srcSerial)) {
                return true;
            }
        }
        return false;
    }


    public CodeController.Presenter mCodePresenter = new CodePresenterImpl(this);
    private ArrayList<String> srcSerials;
    private ArrayList<String> addSerial;
    EditQuantityDialog.OnResultListener resultListener = new EditQuantityDialog.OnResultListener() {
        @Override
        public void clickOk(double quantity) {
            tvInputQty.setText(String.valueOf(quantity));
        }

        @Override
        public void clickCancel() {

        }
    };

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_brack_down_goods_operate);
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
        setInputCode = (SystemEditText) findViewById(R.id.b_rack_down_goods_operate_set_input_code);

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                mCodePresenter.toDecode(content);
            }
        });

        ltvName = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_name);
        ltvSkuCode = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_batchNO);
        ltvUnit = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_unit);
        ltvStd = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_std);
        ltvOverQty = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_over_down_qty);
        ltvNowQty = (LabelTextView) findViewById(R.id.b_rack_down_goods_operate_ltv_plan_down_qty);

        tvInputQty = (TextView) findViewById(R.id.b_rack_down_goods_operate_tv_input_qty);
        tvInputQty.setOnClickListener(this);

        tvSubmitF1 = (TextView) findViewById(R.id.b_rack_down_goods_operate_tv_submit_f1);
        bottomGroup = (ViewGroup) findViewById(R.id.activity_bb_rack_down_goods_operate_bottom_group);

        tvSubmitF1.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        double inputQty = scanningQty;
        double canInputMaxQty = mOperateGoods.getQuantity();


        ltvName.setText(mOperateGoods.getSkuName());
        ltvSkuCode.setText(mOperateGoods.getSkuCode());
        ltvBatchNo.setText(mOperateGoods.getBatchNo());
        ltvUnit.setText(mOperateGoods.getUnitName());
        ltvStd.setText(mOperateGoods.getStd());

        ltvNowQty.setText(String.valueOf(canInputMaxQty));
        ltvOverQty.setText(String.valueOf(mOperateGoods.getPutOffQuantity()));


        if (isSerialOperate()) {
            List<SerialNoVo> serials = mOperateGoods.getSerials();
            srcSerials = new ArrayList<>();
            for (SerialNoVo serial : serials) {
                srcSerials.add(serial.getSerialNo());
            }

            View addSerialButton = ViewInfo.createAddSerialButton(this);
            bottomGroup.addView(addSerialButton);
            tvInputQty.setText("0");
        } else {
            if (scanningQty <= 0) {
                inputQty = canInputMaxQty;
            }
            tvInputQty.setText(String.valueOf(inputQty));
        }
    }

    @Override
    public void widgetForbid(View v) {
        super.widgetForbid(v);
        int i = v.getId();
        if (i == R.id.b_rack_down_goods_operate_tv_submit_f1) {
            submitGoods();
        } else if (i == R.id.btn_serial) {
            Bundle bundle = new Bundle();
            double size = 0;
            if (srcSerials != null) {
                size = srcSerials.size();
            }

            SerialAddActivity.put(srcSerials, addSerial, size, bundle);
            startActivity(SerialAddActivity.class, bundle, REQUEST_ADD_SERIAL);
        } else if (i == R.id.b_rack_down_goods_operate_tv_input_qty) {
            if (isSerialOperate()) {
                toast("请添加序列号");
                return;
            }
            displayEditQty(mOperateGoods.getGoodsQty(), resultListener);
        }
    }

    private void submitGoods() {

        if (isSubmit()) {
            displayProgressDialog("提交中");
            double inputQty = getInputQty();
            BRackDownGoodsSubmitParams params = new BRackDownGoodsSubmitParams();
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

            if (isSerialOperate()) {
                if (addSerial != null) {
                    List<SerialNoVo> list = new ArrayList<>();
                    for (String s : addSerial) {
                        list.add(new SerialNoVo(s));
                    }

                    params.setSerials(list);
                }


            }
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
                    ToastUtils.show(BRackDownGoodsOperateActivity.this, "提交成功");
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onKeyF1() {
        submitGoods();
        return true;
    }

    public boolean isSubmit() {
        double inputQty = getInputQty();
        if (inputQty <= 0) {
            displayMsgDialog("请编写正确数量数量");
            return false;
        }
        double planQty = mOperateGoods.getQuantity();
        planQty = NumberUtils.getDouble(planQty);
        if (planQty < inputQty) {
            displayMsgDialog("超出计划下架数量，请修改数量");
            return false;
        }
        return true;
    }

    private double getInputQty() {
        String inputQty = tvInputQty.getText().toString();
        double qty = NumberUtils.getDouble(inputQty);
        return qty;
    }

    @Override
    public void setTitle(String title) {
        titleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        setInputCode.setHintText(hint);
    }

    public boolean isSerialOperate() {
        return mOperateGoods.isSerialOperate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_ADD_SERIAL:
                addSerial = SerialAddActivity.getSerialList(data);
                double qty = 0d;
                if (addSerial != null) {
                    qty = addSerial.size();
                }

                tvInputQty.setText(String.valueOf(qty));
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
