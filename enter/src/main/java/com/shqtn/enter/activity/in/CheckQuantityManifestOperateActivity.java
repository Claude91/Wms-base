package com.shqtn.enter.activity.in;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.PackingManifest;
import com.shqtn.base.bean.in.CheckQuantityGoodsDetails;
import com.shqtn.base.bean.in.CheckQuantityManifest;
import com.shqtn.base.bean.params.CheckQuantityGoodsDetailsParams;
import com.shqtn.base.bean.params.CheckQuantityGoodsSubmitParams;
import com.shqtn.base.bean.params.CheckQuantityLpnSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.CheckType;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

import java.util.ArrayList;

public class CheckQuantityManifestOperateActivity extends BaseActivity implements SystemEditText.OnToTextSearchListener, CodeController.View {

    private LabelTextView ltvRack, ltvSrc, ltvType, ltvManifest;
    private TitleView titleView;
    private SystemEditText setInputCode;
    private ListView lvCheckGoods;
    private TextView tvSubmitF1;

    private CheckQuantityGoodsDetailsParams mGoodsDetailsParams;
    private CheckQuantityLpnSubmitParams mLpnSubmitParams;

    private ResultCallback mLpnSubmitCallback;
    private ResultCallback mGoodsDetailsCallback;

    private double mScanningGoodsQty;
    private CommonAdapter<CheckQuantityGoodsDetails> mGoodsAdapter;

    private ArrayList<CheckQuantityGoodsDetails> mGoodsDetailsList = new ArrayList<>();

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int tag = (int) view.getTag(R.id.rv_item_id);
            mGoodsDetailsList.remove(tag);
            mGoodsAdapter.update(mGoodsDetailsList);
        }
    };

    private CodeController.Presenter mCodePresenter;
    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeLpn(CodeLpn lpn) {
            displayProgressDialog(String.format("正在提交箱子:%s", lpn.getLpnNo()));
            if (mLpnSubmitParams == null) {
                mLpnSubmitParams = new CheckQuantityLpnSubmitParams();
            }
            mLpnSubmitParams.setDocNo(mOperateManifest.getDocNo());
            mLpnSubmitParams.setLpnNo(lpn.getLpnNo());
            mLpnSubmitParams.setTaskIkey(mOperateManifest.getHikey());

            if (mLpnSubmitCallback == null) {
                mLpnSubmitCallback = createLpnSubmitCallback();
            }

            ModelService.post(ApiUrl.check_lpn_submit, mLpnSubmitParams, mLpnSubmitCallback);
        }

        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);
            if (goods.getGoodsQty() <= 0) {
                mScanningGoodsQty = 1;
            } else {
                mScanningGoodsQty = goods.getGoodsQty();
            }

            if (mGoodsDetailsParams == null) {
                mGoodsDetailsParams = new CheckQuantityGoodsDetailsParams();
            }
            if (mGoodsDetailsCallback == null) {
                mGoodsDetailsCallback = createGoodsDetailsCallback();
            }

            mGoodsDetailsParams.setWhCode(DepotUtils.getDepot(getContext()).getWhcode());
            mGoodsDetailsParams.setBatchNo(goods.getBatchNo());
            mGoodsDetailsParams.setSkuCode(goods.getSkuCode());
            mGoodsDetailsParams.setDocNo(mOperateManifest.getDocNo());
            mGoodsDetailsParams.setLocCode(ltvRack.getText().toString());
            ModelService.post(ApiUrl.check_query_check_goods, mGoodsDetailsParams, mGoodsDetailsCallback);
        }

        @Override
        public void decodeRack(CodeRack rack) {
            super.decodeRack(rack);
            ltvRack.setText(rack.getRackNo());
        }

        @Override
        public void decodeOther(AllotBean code) {
            super.decodeOther(code);
            displayMsgDialog("扫描编码不匹配，请重新扫描");
        }
    };

    private CheckQuantityManifest mOperateManifest;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_check_quantity_manifest_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateManifest = getBundle().getParcelable(C.MANIFEST_BEAN);
        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeType(CodeCallback.TAG_GOODS, CodeCallback.TAG_RACK, CodeCallback.TAG_LPN);
        mCodePresenter.setDecodeCallback(mDecodeCallback);
        mGoodsAdapter = new CommonAdapter<CheckQuantityGoodsDetails>(this, null, R.layout.item_goods_check_quantity) {
            @Override
            public void setItemContent(ViewHolder holder, CheckQuantityGoodsDetails checkQuantityGoodsDetails, int position) {
                double addQty = checkQuantityGoodsDetails.getAddQty();
                View viewDelete = holder.getViewById(R.id.item_goods_check_quantity_imgBtn_delete);
                viewDelete.setTag(R.id.rv_item_id, position);
                viewDelete.setOnClickListener(mItemClickListener);

                holder.setLabelText(R.id.item_goods_check_quantity_ltv_sku, checkQuantityGoodsDetails.getSkuCode())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_name, checkQuantityGoodsDetails.getSkuName())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_batch, checkQuantityGoodsDetails.getBatchNo())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_class_no, checkQuantityGoodsDetails.getSkuClassCode())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_std, checkQuantityGoodsDetails.getStd())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_unit, checkQuantityGoodsDetails.getUnitName())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_qty, String.valueOf(addQty))
                        .setLabelText(R.id.item_goods_check_quantity_ltv_goods_owner, checkQuantityGoodsDetails.getOwnerNome())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_rack_name, checkQuantityGoodsDetails.getLocName())
                        .setLabelText(R.id.item_goods_check_quantity_ltv_rack_code, checkQuantityGoodsDetails.getLocCode());
            }
        };
    }

    @Override
    public void bindView() {
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        setInputCode = (SystemEditText) findViewById(R.id.activity_check_quantity_manifest_operate_set_input_code);
        setInputCode.setOnToTextSearchListener(this);

        lvCheckGoods = (ListView) findViewById(R.id.activity_check_quantity_manifest_operate_lv);

        tvSubmitF1 = (TextView) findViewById(R.id.activity_check_quantity_manifest_operate_tv_submit_f1);
        tvSubmitF1.setOnClickListener(this);

        ltvRack = (LabelTextView) findViewById(R.id.activity_check_quantity_manifest_operate_ltv_rack);
        ltvType = (LabelTextView) findViewById(R.id.activity_check_quantity_manifest_operate_ltv_type);
        ltvSrc = (LabelTextView) findViewById(R.id.activity_quality_inspection_goods_operate_ltv_src_manifest);

        ltvManifest = (LabelTextView) findViewById(R.id.activity_check_quantity_manifest_operate_ltv_manifest);

    }

    @Override
    public void initWidget() {
        super.initWidget();
        ltvManifest.setText(mOperateManifest.getDocNo());
        ltvSrc.setText(CheckType.getPeriodName(mOperateManifest.getPeriod()));
        ltvType.setText(CheckType.getTypeName(mOperateManifest.getCheckType()));
        ltvManifest.setText(mOperateManifest.getDocNo());

        lvCheckGoods.setAdapter(mGoodsAdapter);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_check_quantity_manifest_operate_tv_submit_f1) {
            checkSubmit();
        }
    }

    @Override
    public boolean onKeyF1() {
        checkSubmit();
        return true;
    }

    @Override
    public void onSearchText(String content) {
        mCodePresenter.toDecode(content);
    }

    @Override
    public void setTitle(String title) {
        titleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        setInputCode.setHintText(hint);
    }

    private void checkSubmit() {
        if (!isCheckSubmit()) return;

        displayProgressDialog("提交中 ");
        CheckQuantityGoodsSubmitParams submitParams = new CheckQuantityGoodsSubmitParams();
        ArrayList<CheckQuantityGoodsSubmitParams.Params> paramsList = new ArrayList<>();
        for (CheckQuantityGoodsDetails goodsDetails : mGoodsDetailsList) {
            CheckQuantityGoodsSubmitParams.Params p = new CheckQuantityGoodsSubmitParams.Params();
            p.setLocCode(ltvRack.getText().toString());
            p.setDocNo(mOperateManifest.getDocNo());
            p.setAccQty(goodsDetails.getAccQty());
            p.setBatchNo(goodsDetails.getBatchNo());
            p.setDocDate(mOperateManifest.getDocDate());
            p.setHikey(mOperateManifest.getHikey());
            p.setIkey(goodsDetails.getIkey());
            p.setWhCode(DepotUtils.getDepot(this).getWhcode());
            p.setOwnerCode(goodsDetails.getOwnerCode());
            p.setSkuCode(goodsDetails.getSkuCode());
            p.setSkuClassCode(goodsDetails.getSkuClassCode());
            p.setActQty(goodsDetails.getAddQty());
            paramsList.add(p);
        }
        submitParams.setCheckings(paramsList);

        ModelService.post(ApiUrl.check_submit, submitParams, new ResultCallback() {
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
                ToastUtils.show(getContext(), "提交成功");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    public boolean isCheckSubmit() {

        if (mGoodsDetailsList == null || mGoodsDetailsList.size() <= 0) {
            displayMsgDialog("请添加货品");
            return false;
        }

        return true;
    }

    /**
     * 创建lpn 的提交回调
     *
     * @return
     */
    private ResultCallback createLpnSubmitCallback() {
        return new ResultCallback() {
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
                ToastUtils.show(CheckQuantityManifestOperateActivity.this, "箱子提交成功");
            }
        };
    }

    /**
     * 创建 货品详情的回调
     *
     * @return
     */
    private ResultCallback createGoodsDetailsCallback() {
        return new ResultCallback() {
            @Override
            public void onAfter() {
                cancelProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                ArrayList<CheckQuantityGoodsDetails> rows = getRows(response.getRows(), CheckQuantityGoodsDetails.class);
                cancelProgressDialog();
                if (rows == null || rows.size() <= 0) {
                    displayMsgDialog("获取详情失败，请重洗扫描");
                    return;
                }
                CheckQuantityGoodsDetails g1 = rows.get(0);
                for (CheckQuantityGoodsDetails g2 : mGoodsDetailsList) {
                    if (GoodsUtils.isSame(g1, g2) && g1.getLocCode().equals(g2.getLocCode())) {
                        double addQty = g2.getAddQty();
                        addQty = addQty + mScanningGoodsQty;
                        addQty = NumberUtils.getDouble(addQty);
                        g2.setAddQty(addQty);
                        mGoodsAdapter.update(mGoodsDetailsList);
                        return;
                    }
                }
                g1.setAddQty(mScanningGoodsQty);
                mGoodsDetailsList.add(rows.get(0));
                mGoodsAdapter.update(mGoodsDetailsList);
            }
        };
    }

}
