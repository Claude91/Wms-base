package com.shqtn.b.enter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.b.SerialAddActivity;
import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.b.enter.EnterUrl;
import com.shqtn.b.enter.params.TakeDelManifestDetailsParams;
import com.shqtn.b.enter.params.TakeDelSubmitParams;
import com.shqtn.b.enter.result.BTakeDeliveryManifest;
import com.shqtn.base.C;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.model.TakeDeliveryModel;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<String> mAddSerials;
    private ArrayList<String> srcSerials;

    private ArrayList<String> noSerials;//已经添加过得序列号，不能继续添加

    private String mOperateManifest;
    private BTakeDeliveryManifest mOperateManifestBean;
    private boolean canEditQty;//
    private boolean isInputBatchNo;

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
        mCodePresenter.setDecodeType(CodeCallback.TAG_GOODS);

        mTakeDeliveryModel = new TakeDeliveryModel();

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

        loadDetails(mOperateManifest);
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        cancelProgressDialog();
        if (isAddSerial()) {
            displayMsgDialog("序列号管控，请添加序列号");
            return;
        }
        if (!GoodsUtils.isSameNoBatchNo(mOperateManifestBean, goods)) {
            String skuCode = goods.getSkuCode();
            String batchNo = goods.getBatchNo();
            if (skuCode == null) {
                skuCode = "";
            }
            if (batchNo == null) {
                batchNo = "";
            }
            displayMsgDialog(String.format("查询货品不匹配\n货品编码:%s \n批次号:%s", skuCode, batchNo));
        }
        if (isInputBatchNo) {
            String batchNo = goods.getBatchNo();
            if (!StringUtils.isEmpty(batchNo)) {
                etInputBatchNo.setText(batchNo);
            }
        }

        double goodsQty = goods.getGoodsQty();
        if (goodsQty <= 0) {
            goodsQty = 1;
        }
        String totalQtyStr = tvTakeQty.getText().toString();
        double totalQty = NumberUtils.getDouble(totalQtyStr);

        totalQty = NumberUtils.getDouble(totalQty + goodsQty);
        tvTakeQty.setText(String.valueOf(totalQty));

    }

    private void loadDetails(String manifestNo) {
        displayProgressDialog("加载数据中");
        TakeDelManifestDetailsParams params = new TakeDelManifestDetailsParams();
        DepotBean depot = DepotUtils.getDepot(this);
        params.setWhCode(depot.getWhcode());
        params.setAsnNo(manifestNo);
        ModelService.post(EnterUrl.take_del_search_manifest_details, params, new ResultCallback() {
            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                mOperateManifestBean = getData(response.getData(), BTakeDeliveryManifest.class);
                resetGoodsView();
            }
        });
    }

    /**
     * 重置显示货品中的View
     */
    private void resetGoodsView() {
        String NULL_ = "";

        if (mTakeDeliveryModel.isAddBatchNo(mOperateManifestBean.getBatchFlag(), mOperateManifestBean.getBatchNoFlag())) {
            //需要输入BatchNo;
            setIsInputBatchNo(true);
        } else {
            setIsInputBatchNo(false);

        }
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

        double pQty = mOperateManifestBean.getPQty();
        double quantity = mOperateManifestBean.getQuantity();

        String asnNo = mOperateManifestBean.getAsnNo();
        if (StringUtils.isEmpty(asnNo)) {
            asnNo = NULL_;
        }

        String planQty = String.format("%.2f", NumberUtils.getDouble(pQty - quantity));
        ltvSkuName.setText(skuName);
        ltvSkuCode.setText(skuCode);
        etInputBatchNo.setText(batchNo);
        ltvUnitName.setText(unitName);
        ltvPlanQty.setText(planQty);
        ltvOperateManifest.setText(asnNo);
        if (isAddSerial()) {
            // 序列号 数组
            if (!mTakeDeliveryModel.isRfInSerial(mOperateManifestBean.getSerialNoFlag())) {
                List<SerialNoVo> serialNoList = mOperateManifestBean.getSerialNoList();
                if (serialNoList != null) {
                    srcSerials = new ArrayList<>();
                    for (SerialNoVo serialNoVo : serialNoList) {
                        srcSerials.add(serialNoVo.getSerialNo());
                    }
                }
            }

            // 序列号 数组 已经提交过得数组
            //再次添加序列号时 不可添加此数组内字符
            List<SerialNoVo> shSerialNoList = mOperateManifestBean.getShSerialNoList();
            if (shSerialNoList != null) {
                noSerials = new ArrayList<>();
                for (SerialNoVo serialNoVo : shSerialNoList) {
                    noSerials.add(serialNoVo.getSerialNo());
                }
            }

            addBottomSerial();
            canEditQty = false;
        } else {
            canEditQty = true;
        }
    }


    private void displayEditQtyDialog() {
        if (isCanEditQty()) {
            displayEditQty(NumberUtils.getDouble(mOperateManifestBean.getPQty() - mOperateManifestBean.getQuantity()), resultListener);
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
        tvSerial.setText("添加序列号F4");
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
            toSubmit();
        } else if (i == R.id.activity_b_take_delivery_goods_details_tv_take_qty) {
            displayEditQtyDialog();
        } else if (i == R.id.btn_serial) {
            toAddSerialActivity();
        }
    }

    private void toSubmit() {
        if (!isCanSubmit()) {
            return;
        }
        displayProgressDialog("提交中");

        TakeDelSubmitParams submitParams = new TakeDelSubmitParams();
        submitParams.setAsnNo(mOperateManifestBean.getAsnNo());

        submitParams.setWhCode(mOperateManifestBean.getWhCode());
        submitParams.setIkey(mOperateManifestBean.getIkey());
        submitParams.setOrgnIkey(mOperateManifestBean.getOrgnIkey());

        String takeQtyStr = tvTakeQty.getText().toString();
        submitParams.setAccQuantity(NumberUtils.getDouble(takeQtyStr));
        String batchNo = etInputBatchNo.getText().toString();
        submitParams.setBatchNo(batchNo);

        submitParams.setSkuCode(mOperateManifestBean.getSkuCode());
        if (mAddSerials != null) {
            ArrayList<SerialNoVo> l = new ArrayList<>();
            for (String mAddSerial : mAddSerials) {
                l.add(new SerialNoVo(mAddSerial));
            }
            submitParams.setSerialNoList(l);
        }


        ModelService.post(EnterUrl.take_del_submit, submitParams, new ResultCallback() {
            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                toast("提交成功");
                finish();
            }
        });

    }

    private boolean isCanSubmit() {
        String batchNo;
        if (isInputBatchNo) {
            //需要判断当前货品是否需要添加批次号
            batchNo = etInputBatchNo.getText().toString();
            if (StringUtils.isEmpty(batchNo)) {
                displayMsgDialog("请输入批次号");
                return false;
            }
        }

        if (isAddSerial()) {
//           当前是序列号管控，需要添加序列号
            if (mAddSerials == null) {
                displayMsgDialog("请输入序列号");
                return false;
            }
        } else {
            double takeQty = NumberUtils.getDouble(tvTakeQty.getText().toString());
            if (takeQty <= 0) {
                displayMsgDialog("请输入正确收货数量");
                return false;
            }
        }
        return true;
    }

    private void toAddSerialActivity() {
        if (!isAddSerial()) {
            return;
        }
        Bundle bundle = new Bundle();
        SerialAddActivity.put(srcSerials, mAddSerials, NumberUtils.getDouble(mOperateManifestBean.getPQty() - mOperateManifestBean.getQuantity()), bundle);
        SerialAddActivity.putNoSerial(noSerials, bundle);
        startActivity(SerialAddActivity.class, bundle, REQUEST_ADD_SERIAL);
    }


    public void setIsInputBatchNo(boolean isInputBatchNo) {
        this.isInputBatchNo = isInputBatchNo;
        if (!isInputBatchNo) {
            etInputBatchNo.setFocusable(false);
            etInputBatchNo.setClickable(false);
            etInputBatchNo.setFocusableInTouchMode(false);
        } else {
            etInputBatchNo.setFocusableInTouchMode(true);

            etInputBatchNo.setFocusable(true);

            etInputBatchNo.requestFocus();
        }
    }

    @Override
    public boolean onKeyF1() {
        toSubmit();
        return true;
    }

    @Override
    public boolean onKeyF2() {
        displayEditQtyDialog();
        return true;
    }

    @Override
    public boolean onKeyF4() {
        toAddSerialActivity();
        return true;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setEditTextHint(String hint) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_SERIAL && resultCode == Activity.RESULT_OK) {
            mAddSerials = SerialAddActivity.getSerialList(data);
            int qty = 0;
            if (mAddSerials != null) {
                qty = mAddSerials.size();
            }

            tvTakeQty.setText(String.valueOf(qty));
        }
    }
}
