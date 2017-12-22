package com.shqtn.enter.activity.enter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.RvCommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.QualityInspectionGoods;
import com.shqtn.base.bean.enter.Reason;
import com.shqtn.base.bean.params.QualityInspectionGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.CashierInputFilter;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.utils.ComputerQualityInspectionStatusQty;

import java.util.ArrayList;
import java.util.List;

import static com.shqtn.enter.R.id.activity_quality_inspection_goods_operate_ltv_std;
import static com.shqtn.enter.R.id.view;

/**
 * 用于质量检测页面，质检的具体操作
 *
 * @author android strive_bug@yeah.net
 */
public class QualityInspectionGoodsOperateActivity extends BaseActivity implements ComputerQualityInspectionStatusQty.OnNumberChangeListener {

    private RecyclerView rv;
    private TitleView titleView;
    private RvCommonAdapter<Reason> adapter;
    private ArrayList<Reason> mReasonList;
    private LabelTextView ltvName, ltvSku, ltvBatchNo, ltvUnit, ltvStd, ltvQty, ltvSrcManifest;
    private View submitF1;
    private TextView tvOkQty;
    private TextView tvBackQtyHint, tvNoOkQtyHint, tvOkQtyHint;
    private EditText etNoOkQty, etBackQty;
    private QualityInspectionGoods mOperateGoods;
    private ComputerQualityInspectionStatusQty mComputerQty;
    private boolean httpDoing;

    private int selectReasonIndex = -1;
    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int tag = (int) view.getTag(R.id.rv_item_id);
            if (selectReasonIndex != tag) {
                int lastSelectIndex = selectReasonIndex;
                selectReasonIndex = tag;
                adapter.notifyItemChanged(lastSelectIndex);
                adapter.notifyItemChanged(selectReasonIndex);
            }
        }
    };

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_quality_inspection_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();

        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);
        adapter = new RvCommonAdapter<Reason>(this, null, R.layout.item_text_select) {
            @Override
            public void onBindViewHolder(ItemViewHolder holder, List<Reason> data, int position) {
                holder.itemView.setTag(R.id.rv_item_id, position);
                holder.itemView.setOnClickListener(mItemClickListener);

                Reason reason = data.get(position);
                View group = holder.itemView;
                TextView tv = holder.findView(R.id.item_text_select_tv);
                if (selectReasonIndex == position) {
                    group.setSelected(true);
                    tv.setSelected(true);
                } else {
                    group.setSelected(false);
                    tv.setSelected(false);
                }
                holder.setTextView(R.id.item_text_select_tv, reason.getReasonType());
            }
        };
        double quantity = mOperateGoods.getQuantity();
        mComputerQty = new ComputerQualityInspectionStatusQty(quantity);
        mComputerQty.setOnNumberChangeListener(this);

    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        rv = (RecyclerView) findViewById(R.id.activity_quality_inspection_goods_operate_rv);
        rv.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rv.setAdapter(adapter);

        ltvName = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_name);
        ltvSku = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_batch_no);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_unit);
        ltvStd = (LabelTextView) findViewById(activity_quality_inspection_goods_operate_ltv_std);
        ltvQty = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_qty);
        ltvSrcManifest = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_src_manifest);

        tvOkQty = (TextView) findViewById(R.id.activity_quality_inspection_goods_operate_et_ok_qty);

        tvBackQtyHint = (TextView) findViewById(R.id.activity_quality_inspection_goods_operate_tv_back_qty_hint);
        tvNoOkQtyHint = (TextView) findViewById(R.id.activity_quality_inspection_goods_operate_tv_no_ok_qty_hint);
        tvOkQtyHint = (TextView) findViewById(R.id.activity_quality_inspection_goods_operate_tv_qty_error_hint);

        etNoOkQty = (EditText) findViewById(R.id.activity_quality_inspection_goods_operate_et_no_ok_qty);
        etBackQty = (EditText) findViewById(R.id.activity_quality_inspection_goods_operate_et_back_qty);


        etNoOkQty.setFilters(new InputFilter[]{new CashierInputFilter()});
        etBackQty.setFilters(new InputFilter[]{new CashierInputFilter()});


        etNoOkQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.endsWith(".")) {
                    s = s.substring(0, s.length() - 1);
                }
                double inputQty = NumberUtils.getDouble(s);
                mComputerQty.changeNoOkQty(inputQty);

            }
        });
        etBackQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (s.endsWith(".")) {
                    s = s.substring(0, s.length() - 1);
                }
                double inputQty = NumberUtils.getDouble(s);
                mComputerQty.changeBackQty(inputQty);
            }
        });

        submitF1 = findViewById(R.id.activity_quality_inspection_goods_operate_tv_submit_f1);
        submitF1.setOnClickListener(this);

    }

    @Override
    public void onNumberChange(double okQty, double noOkQty, double backQty, double totalQty) {
        tvOkQty.setText(String.valueOf(okQty));
        if (okQty < 0) {
            if (View.VISIBLE != tvOkQtyHint.getVisibility()) {
                tvOkQtyHint.setVisibility(View.VISIBLE);
            }
            tvOkQtyHint.setText("数量溢出，请重新输入数量,总数量额:" + totalQty);
        } else {
            if (View.GONE != tvOkQtyHint.getVisibility()) {
                tvOkQtyHint.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initWidget() {
        super.initWidget();

        ltvName.setText(mOperateGoods.getSkuName());
        ltvSku.setText(mOperateGoods.getSkuCode());
        ltvBatchNo.setText(mOperateGoods.getBatchNo());
        ltvUnit.setText(mOperateGoods.getUnitName());
        ltvStd.setText(mOperateGoods.getStd());
        double quantity = mOperateGoods.getQuantity();
        ltvQty.setText(String.valueOf(quantity));
        ltvSrcManifest.setText(mOperateGoods.getOrderNo());

        tvOkQty.setText(String.valueOf(quantity));

        displayProgressDialog("加载数据中");
        ModelService.post(ApiUrl.URL_QUALITY_CHECKING_QCREASON_LIST, null, new ResultCallback() {
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
                mReasonList = getRows(response.getData(), Reason.class);
                adapter.update(mReasonList);
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_quality_inspection_goods_operate_tv_submit_f1) {
            toSubmit();
        }
    }

    private void toSubmit() {
        if (!checkSubmit()) {
            return;
        }

        QualityInspectionGoodsSubmitParams params = new QualityInspectionGoodsSubmitParams();
        params.setBatchNo(mOperateGoods.getBatchNo());
        params.setIkey(mOperateGoods.getIkey());
        if (selectReasonIndex != -1) {
            Reason reason = mReasonList.get(selectReasonIndex);
            if (reason != null) {
                params.setReasonCode(reason.getReasonCode());//不良品代码
            }
        }
        params.setSkuCode(mOperateGoods.getSkuCode());
        params.setAssOkQty(mOperateGoods.getAssOkQty());
        params.setOkQty(NumberUtils.getDouble(tvOkQty.getText().toString()));
        params.setBadQty(NumberUtils.getDouble(etNoOkQty.getText().toString()));
        params.setScrapQty(NumberUtils.getDouble(etBackQty.getText().toString()));
        params.setTs(mOperateGoods.getTs());

        httpDoing = true;
        ModelService.post(ApiUrl.URL_QUALITY_CHECKING_SUBMIT, params, new ResultCallback() {

            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
                httpDoing = false;
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                ToastUtils.show(QualityInspectionGoodsOperateActivity.this, "提交成功");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private boolean checkSubmit() {
        if (tvBackQtyHint.getVisibility() != View.GONE || tvNoOkQtyHint.getVisibility() != View.GONE) {
            displayMsgDialog("请处理溢出");
            return false;
        }

        String s = etBackQty.getText().toString();
        String s1 = etNoOkQty.getText().toString();
        if ((NumberUtils.getDouble(s) > 0 || NumberUtils.getDouble(s1) > 0) && selectReasonIndex == -1) {
            displayMsgDialog("请选择不良品原因");
            return false;
        }
        if (httpDoing) {
            return false;
        }

        return true;
    }


    @Override
    public boolean onKeyF1() {
        toSubmit();
        return true;
    }


    @Override
    public boolean onKeyF3() {
        toFocus(etNoOkQty);
        return true;
    }

    @Override
    public boolean onKeyF4() {
        toFocus(etBackQty);
        return true;
    }


}
