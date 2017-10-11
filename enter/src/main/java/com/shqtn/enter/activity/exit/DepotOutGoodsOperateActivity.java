package com.shqtn.enter.activity.exit;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.DepotOutGoods;
import com.shqtn.base.bean.exit.DepotOutManifest;
import com.shqtn.base.bean.exit.SerialNo;
import com.shqtn.base.bean.params.DepotOutGoodsIkeyListParams;
import com.shqtn.base.bean.params.DepotOutGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.MaxQuantityEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;

import java.util.ArrayList;

public class DepotOutGoodsOperateActivity extends BaseActivity implements MaxQuantityEditText.OnOverstepListener {

    private TitleView titleView;
    private LabelTextView ltvName, ltvSku, ltvBatchNo, ltvQty;
    private TextView tvSubmitF1;
    private MaxQuantityEditText mqEtInputQty;


    private String mOperateAreaCode;
    private DepotOutManifest mOperateManifest;
    private DepotOutGoods mOperateGoods;
    private double scanningGoodsQty;
    private ArrayList<SerialNo> mSerialNoList;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_depot_out_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateAreaCode = getBundle().getString(C.AREA_CODE);
        mOperateManifest = getBundle().getParcelable(C.MANIFEST_BEAN);
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);
        scanningGoodsQty = getBundle().getDouble(C.SCANNING_GOODS_QTY);
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        ltvName = (LabelTextView) findViewById(R.id.activity_depot_out_goods_operate_ltv_name);
        ltvSku = (LabelTextView) findViewById(R.id.activity_depot_out_goods_operate_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_depot_out_goods_operate_ltv_batch_no);
        ltvQty = (LabelTextView) findViewById(R.id.activity_depot_out_goods_operate_ltv_qty);

        tvSubmitF1 = (TextView) findViewById(R.id.activity_depot_out_goods_operate_tv_submit_f1);
        mqEtInputQty = (MaxQuantityEditText) findViewById(R.id.activity_depot_out_goods_operate_maxQtyEditText_input_qty);

        titleView.setOnToBackClickListener(this);

        tvSubmitF1.setOnClickListener(this);
        mqEtInputQty.setOnOverStepListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        displayProgressDialog("加载数据中");
        loadIkey();
        ltvName.setText(mOperateGoods.getSkuName());
        ltvSku.setText(mOperateGoods.getSkuCode());
        double quantity = mOperateGoods.getQuantity();
        mqEtInputQty.setMaxQuantity(quantity);
        if (scanningGoodsQty <= 0) {
            ltvQty.setText(String.valueOf(quantity));
        } else {
            ltvQty.setText(String.valueOf(scanningGoodsQty));
        }

    }

    private void loadIkey() {
        DepotOutGoodsIkeyListParams ikeyParams = new DepotOutGoodsIkeyListParams();
        ikeyParams.setDeliveryNo(mOperateManifest.getDeliveryNo());
        ikeyParams.setWhCode(DepotUtils.getDepot(this).getWhcode());
        ikeyParams.setAreaCode(mOperateAreaCode);
        ikeyParams.setBatchNo(mOperateGoods.getGoodsBatchNo());
        ikeyParams.setSkuCode(mOperateGoods.getGoodsSku());

        ModelService.post(ApiUrl.URL_DEPOT_OUT_IKEY_LIST, ikeyParams, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                mSerialNoList = getRows(response.getData(), SerialNo.class);
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_depot_out_goods_operate_tv_submit_f1) {
            submit();
        }
    }

    @Override
    public void overstepMaxQuantity(EditText e, double s) {
        //超出限制后
        e.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
    }

    @Override
    public void normal(EditText e, double d) {
        //输入的数量
        e.setTextColor(ContextCompat.getColor(this, R.color.text_black_color));
    }


    private boolean isSubmit() {

        try {
            double qty = NumberUtils.getDouble(mqEtInputQty.getText().toString());
            if (qty <= 0) {
                displayMsgDialog("请输入正确的数量");
                return false;
            }
            if (qty > mOperateGoods.getQuantity()) {
                displayMsgDialog("输入数量大于剩余数量");
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void submit() {

        if (!isSubmit()) {
            return;
        }
        DepotOutGoodsSubmitParams params = new DepotOutGoodsSubmitParams();

        params.setIkeyList(mSerialNoList);
        String qtyStr = mqEtInputQty.getText().toString();
        double qty = NumberUtils.getDouble(qtyStr);
        params.setQuantity(qty);

        ModelService.post(ApiUrl.URL_DEPOT_OUT_SUBMIT, params, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                ToastUtils.show(DepotOutGoodsOperateActivity.this, "提交成功");
                finish();
            }
        });
    }

}
