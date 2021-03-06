package com.shqtn.enter.activity.exit;

import android.support.v4.content.ContextCompat;
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
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.MaxQuantityEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;

import java.util.ArrayList;

/**
 * 描述: 修改 提交时，数量 quantity字段 更改为quantityOut 字段
 * 与 陈伟 对应， 修改时间为 2018/1/3
 *
 * @date 2018/1/3
 */
public class DepotOutGoodsOperateActivity extends BaseActivity implements MaxQuantityEditText.OnOverstepListener {

    public TitleView titleView;
    public LabelTextView ltvName, ltvSku, ltvBatchNo, ltvQty;
    public TextView tvSubmitF1;
    public MaxQuantityEditText mqEtInputQty;


    public String mOperateAreaCode;
    public DepotOutManifest mOperateManifest;
    public DepotOutGoods mOperateGoods;
    public double scanningGoodsQty;
    public ArrayList<SerialNo> mSerialNoList;

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
        ltvBatchNo.setText(mOperateGoods.getBatchNo());
        double quantity = getCanSubmitMaxQty();
        mqEtInputQty.setMaxQuantity(quantity);
        if (scanningGoodsQty <= 0) {
            ltvQty.setText(String.valueOf(quantity));
        } else {
            ltvQty.setText(String.valueOf(scanningGoodsQty));
        }

    }

    public void loadIkey() {
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
    public boolean onKeyF1() {
        submit();
        return true;
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
            if (qty > getCanSubmitMaxQty()) {
                displayMsgDialog("输入数量大于剩余数量");
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 当前操作可提交的最大数量
     *
     * @return
     */
    private double getCanSubmitMaxQty() {
        return NumberUtils.getDouble(mOperateGoods.getQuantity() - mOperateGoods.getDeliveryQty());
    }

    private void submit() {

        if (!isSubmit()) {
            return;
        }
        DepotOutGoodsSubmitParams params = new DepotOutGoodsSubmitParams();

        params.setIkeyList(mSerialNoList);
        String qtyStr = mqEtInputQty.getText().toString();
        double qty = NumberUtils.getDouble(qtyStr);
        params.setOutQuantity(qty);

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
