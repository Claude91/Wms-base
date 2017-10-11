package com.shqtn.enter.activity.exit;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.SortingGoods;
import com.shqtn.base.bean.params.SortingSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.MaxQuantityEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;

import java.util.ArrayList;

public class SortingGoodsOperateActivity extends BaseActivity implements MaxQuantityEditText.OnOverstepListener {

    private TitleView titleView;
    private LabelTextView ltvName, ltvSku, ltvBatchNo, ltvStd, ltvHaveQty, ltvOVerQty, ltvPlanQty;
    private TextView tvSubmitF1;
    private MaxQuantityEditText maxQuantityEditTextInputQty;

    private SortingGoods mOperateGoods;
    private String mOperateManifest;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_sorting_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);
    }

    @Override
    public void bindView() {
        super.bindView();

        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        ltvName = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_name);
        ltvSku = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_batch_no);
        ltvStd = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_std);
        ltvHaveQty = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_depot_have_qty);
        ltvOVerQty = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_over_qty);
        ltvPlanQty = (LabelTextView) findViewById(R.id.activity_sorting_goods_operate_ltv_plan_qty);

        maxQuantityEditTextInputQty = (MaxQuantityEditText) findViewById(R.id.activity_sorting_goods_operate_maxQtyEditText_input_qty);

        tvSubmitF1 = (TextView) findViewById(R.id.activity_sorting_goods_operate_tv_submit);
        tvSubmitF1.setOnClickListener(this);

    }

    @Override
    public void initWidget() {
        super.initWidget();

        ltvName.setText(mOperateGoods.getSkuName());
        ltvSku.setText(mOperateGoods.getSkuCode());
        ltvBatchNo.setText(mOperateGoods.getBatchNo());
        ltvStd.setText(mOperateGoods.getStd());
        double sortingQuantity = mOperateGoods.getSortingQuantity();//累计分拣数量
        double stockQty = mOperateGoods.getStockQty();//在库数量
        double pqty = mOperateGoods.getPqty();//计划数量
        ltvHaveQty.setText(String.valueOf(stockQty));

        ltvOVerQty.setText(String.valueOf(sortingQuantity));
        ltvPlanQty.setText(String.valueOf(pqty));

        double canInputQty = getCanInputQty();
        maxQuantityEditTextInputQty.setMaxQuantity(canInputQty);

        maxQuantityEditTextInputQty.setOnOverStepListener(this);
    }

    public double getCanInputQty() {
        double sortingQuantity = mOperateGoods.getSortingQuantity();//累计分拣数量
        double stockQty = mOperateGoods.getStockQty();//在库数量
        double pqty = mOperateGoods.getPqty();//计划数量

        double v = pqty - sortingQuantity;

        return v > stockQty ? stockQty : v;
    }

    @Override
    public void overstepMaxQuantity(EditText e, double s) {
        e.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
    }

    @Override
    public void normal(EditText e, double d) {
        e.setTextColor(ContextCompat.getColor(this, R.color.text_black_color));
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_sorting_goods_operate_tv_submit) {
            toSubmit();
        }
    }

    private void toSubmit() {
        if (isSubmit()) {
            displayProgressDialog("提交中");
            SortingSubmitParams submitParams = new SortingSubmitParams();
            final ArrayList<SortingGoods> sortingGoodses = new ArrayList<>();
            SortingGoods clone = null;
            try {
                clone = (SortingGoods) mOperateGoods.clone();
                String s = maxQuantityEditTextInputQty.getText().toString();
                double inputQty = NumberUtils.getDouble(s);
                clone.setWritQty(inputQty);
                sortingGoodses.add(clone);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            submitParams.setSortList(sortingGoodses);
            ModelService.post(ApiUrl.URL_SORTING_SUBMIT, submitParams, new ResultCallback() {
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
                    ToastUtils.show(SortingGoodsOperateActivity.this, "提交成功");
                    finish();
                }
            });
        }
    }

    private boolean isSubmit() {

        String s = maxQuantityEditTextInputQty.getText().toString();
        if (StringUtils.isEmpty(s)) {
            displayMsgDialog("请输入数量");
            return false;
        }
        double inputQty = NumberUtils.getDouble(s);
        if (inputQty > mOperateGoods.getStockQty()) {
            displayMsgDialog("超出库存数量,请重新输入数量");
            return false;
        }

        if (inputQty > mOperateGoods.getPqty()) {
            displayMsgDialog("超出计划数量,请重新输入数量");
            return false;
        }

        return true;
    }
}
