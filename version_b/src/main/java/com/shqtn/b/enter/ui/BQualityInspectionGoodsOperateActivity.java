package com.shqtn.b.enter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.b.R;
import com.shqtn.b.SerialAddActivity;
import com.shqtn.b.enter.params.BQualityInspectionGoodsSubmitParams;
import com.shqtn.b.enter.result.BQualityInspectionGoods;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.RvCommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.SerialNoVo;
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
import com.shqtn.enter.utils.ComputerQualityInspectionStatusQty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BQualityInspectionGoodsOperateActivity extends BaseActivity implements ComputerQualityInspectionStatusQty.OnNumberChangeListener {

    /**
     * 添加 不合格序列号
     */
    private static final int REQUEST_ADD_UNQUALIFIED_SERIAL = 2;
    /**
     * 添加 损坏序列号
     */
    private static final int REQUEST_ADD_BAD_SERIAL = 3;
    private RecyclerView rv;
    private TitleView titleView;
    private RvCommonAdapter<Reason> adapter;
    private ArrayList<Reason> mReasonList;
    private LabelTextView ltvName, ltvSku, ltvBatchNo, ltvUnit, ltvStd, ltvQty, ltvSrcManifest;
    private View submitF1;
    private ViewGroup bottomGroup;

    private TextView tvOkQty;
    private TextView tvBackQtyHint, tvNoOkQtyHint, tvOkQtyHint;
    private EditText etNoOkQty, etBackQty;
    private BQualityInspectionGoods mOperateGoods;
    private ComputerQualityInspectionStatusQty mComputerQty;
    private boolean httpDoing;

    private ArrayList<String> srcSerials;//当前货物的序列号
    private ArrayList<String> badSerials;//损坏的序列号
    private ArrayList<String> unqualifiedSerials;//不合格的序列号

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
        setContentView(R.layout.activity_bquality_inspection_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();

        Bundle bundle = getBundle();
        if (bundle != null) {
            mOperateGoods = bundle.getParcelable(C.OPERATE_GOODS);
        }

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
        double quantity = 0;
        if (mOperateGoods != null) {
            quantity = mOperateGoods.getQuantity();
        }

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

        bottomGroup = (ViewGroup) findViewById(R.id.activity_bquality_inspection_goods_operate_bottom_btn_group);

        ltvName = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_name);
        ltvSku = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_batch_no);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_unit);
        ltvStd = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_std);
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
        if (mOperateGoods != null) {
            ltvName.setText(mOperateGoods.getSkuName());
            ltvSku.setText(mOperateGoods.getSkuCode());
            ltvBatchNo.setText(mOperateGoods.getBatchNo());
            ltvUnit.setText(mOperateGoods.getUnitName());
            ltvStd.setText(mOperateGoods.getStd());
            double quantity = mOperateGoods.getQuantity();
            ltvQty.setText(String.valueOf(quantity));
            ltvSrcManifest.setText(mOperateGoods.getOrderNo());

            tvOkQty.setText(String.valueOf(quantity));
        }

        // displayProgressDialog("加载数据中");
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

        if (isSerial()) {
            // TODO: 2018/1/5 是否是序列号管控，是， 则点击数量进行 录入序列号

            List<SerialNoVo> serialNoList = mOperateGoods.getSerialNoList();
            if (serialNoList == null) {
                return;
            }
            srcSerials = new ArrayList<>();
            for (SerialNoVo serialNoVo : serialNoList) {
                srcSerials.add(serialNoVo.getSerialNo());
            }

            serialOperateView();
        }

    }

    /**
     * 序列号进行的操作
     */
    private void serialOperateView() {
        etBackQty.setClickable(true);
        etBackQty.setOnClickListener(this);
        etBackQty.setFocusable(false);

        etNoOkQty.setClickable(true);
        etNoOkQty.setOnClickListener(this);
        etNoOkQty.setFocusable(false);
    }

    private boolean isSerial() {
        return "Y".equals(mOperateGoods.getSerialFlag());
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_quality_inspection_goods_operate_tv_submit_f1) {
            toSubmit();
        } else {


            if (i == R.id.activity_quality_inspection_goods_operate_et_no_ok_qty) {
                //不合格数量
                int size = getAllSerialSize();
                toAddSerials(size, srcSerials, unqualifiedSerials, badSerials, REQUEST_ADD_UNQUALIFIED_SERIAL);
            } else if (i == R.id.activity_quality_inspection_goods_operate_et_back_qty) {
                int size = getAllSerialSize();
                //需要损坏数量
                toAddSerials(size, srcSerials, badSerials, unqualifiedSerials, REQUEST_ADD_BAD_SERIAL);
            }
        }
    }

    private void toAddSerials(int size, ArrayList<String> srcSerials, ArrayList<String> unqualifiedSerials, ArrayList<String> badSerials, int requestAddUnqualifiedSerial) {
        Bundle bundle = new Bundle();
        SerialAddActivity.put(srcSerials, unqualifiedSerials, size, bundle);
        SerialAddActivity.putNoSerial(badSerials, bundle);
        startActivity(SerialAddActivity.class, bundle, requestAddUnqualifiedSerial);
    }


    private void toSubmit() {
        if (!checkSubmit()) {
            return;
        }
        displayProgressDialog("提交中");
        BQualityInspectionGoodsSubmitParams params = new BQualityInspectionGoodsSubmitParams();

        if (isSerial()) {
            ArrayList<SerialNoVo> goodsSerial = new ArrayList<>();
            List<SerialNoVo> serialNoList = mOperateGoods.getSerialNoList();
            ArrayList<SerialNoVo> bUnqualifiedSerials = null;
            ArrayList<SerialNoVo> dBadSerials = null;

            if (unqualifiedSerials != null) {
                bUnqualifiedSerials = new ArrayList<>();
                for (String unqualifiedSerial : unqualifiedSerials) {
                    SerialNoVo v = new SerialNoVo(unqualifiedSerial);
                    bUnqualifiedSerials.add(v);
                }
            }
            if (badSerials != null) {
                dBadSerials = new ArrayList<>();
                for (String badSerial : badSerials) {
                    dBadSerials.add(new SerialNoVo(badSerial));
                }
            }


            for (SerialNoVo serialNoVo : serialNoList) {
                if (bUnqualifiedSerials != null && bUnqualifiedSerials.contains(serialNoVo)) {
                    //在 合格列表中
                } else if (dBadSerials != null && dBadSerials.contains(serialNoVo)) {
                    //在 损坏的列表中
                } else {
                    //合格的序列号
                    goodsSerial.add(serialNoVo);
                }
            }
            params.setsBList(bUnqualifiedSerials);
            params.setsDList(dBadSerials);
            params.setsIList(goodsSerial);
        }


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
                cancelProgressDialog();
                ToastUtils.show(BQualityInspectionGoodsOperateActivity.this, "提交成功");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_ADD_BAD_SERIAL:
                badSerials = SerialAddActivity.getSerialList(data);
                if (badSerials != null) {
                    int size = badSerials.size();
                    etBackQty.setText(String.valueOf(size));
                }
                break;
            case REQUEST_ADD_UNQUALIFIED_SERIAL:
                unqualifiedSerials = SerialAddActivity.getSerialList(data);
                if (unqualifiedSerials != null) {
                    int size = unqualifiedSerials.size();
                    etNoOkQty.setText(String.valueOf(size));
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

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
        if (isSerial()) {
            int size = getAllSerialSize();
            toAddSerials(size, srcSerials, unqualifiedSerials, badSerials, REQUEST_ADD_UNQUALIFIED_SERIAL);
            return true;
        }
        toFocus(etNoOkQty);
        return true;
    }

    @Override
    public boolean onKeyF4() {
        if (isSerial()) {
            int size = getAllSerialSize();
            //需要损坏数量
            toAddSerials(size, srcSerials, badSerials, unqualifiedSerials, REQUEST_ADD_BAD_SERIAL);
            return true;
        }
        toFocus(etBackQty);
        return true;
    }

    private int getAllSerialSize() {
        int size = 0;
        if (srcSerials != null) {
            size = srcSerials.size();
        }
        return size;
    }
}
