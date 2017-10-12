package com.shqtn.enter.activity.exit;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.PackingLpn;
import com.shqtn.base.bean.exit.PackingLpnOrGoods;
import com.shqtn.base.bean.params.PackingSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

import java.util.ArrayList;

public class PackingAddLpnOrGoodsOperateActivity extends BaseActivity implements SystemEditText.OnToTextSearchListener, CodeController.View {
    private TitleView titleView;
    private SystemEditText setInputCode;
    private ListView lvAddLpnOrGoods;
    private TextView tvSubmitF1, tvPackingOkF2;
    private LabelTextView ltvOperateLpn;//正在包装的Lpn

    private PackingLpn mOperateLpn;
    private String mOperateManifest;

    private CommonAdapter<PackingLpnOrGoods> mLpnOrGoodsAdapter;
    private ArrayList<PackingLpnOrGoods> mLpnOrGoodsList = new ArrayList<>();

    private CodeController.Presenter mCodePresenter;
    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeLpn(CodeLpn lpn) {
            super.decodeLpn(lpn);
            for (PackingLpnOrGoods packingLpnOrGoods : mLpnOrGoodsList) {
                if (lpn.getLpnNo().equals(packingLpnOrGoods.getBoxCode())) {
                    displayMsgDialog(lpn.getLpnNo() + "已近存在，不能重复添加");
                    return;
                }
            }
            PackingLpnOrGoods lpn1 = new PackingLpnOrGoods();
            lpn1.setBoxCode(lpn.getLpnNo());
            mLpnOrGoodsList.add(lpn1);
            mLpnOrGoodsAdapter.update(mLpnOrGoodsList);
        }

        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);
            double quantity = goods.getQuantity();
            if (quantity <= 0) {
                quantity = 1;
            }
            cancelProgressDialog();
            for (PackingLpnOrGoods packingLpnOrGoods : mLpnOrGoodsList) {
                if (GoodsUtils.isSame(packingLpnOrGoods, goods)) {
                    quantity = quantity + packingLpnOrGoods.getQuantity();
                    quantity = NumberUtils.getDouble(quantity);
                    packingLpnOrGoods.setQuantity(quantity);
                    mLpnOrGoodsAdapter.update(mLpnOrGoodsList);
                    return;
                }
            }

            PackingLpnOrGoods goods1 = new PackingLpnOrGoods();
            goods1.setBatchNo(goods.getBatchNo());
            goods1.setSkuCode(goods.getSkuCode());
            goods1.setQuantity(quantity);

            mLpnOrGoodsList.add(goods1);
            mLpnOrGoodsAdapter.update(mLpnOrGoodsList);
        }

        @Override
        public void decodeOther(AllotBean code) {
            super.decodeOther(code);
            displayMsgDialog("扫描类型不匹配，请重新扫描");
        }

    };

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_packing_add_lpn_or_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateLpn = getBundle().getParcelable(C.OPERATE_LPN);
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);

        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeCallback(mDecodeCallback);
        mCodePresenter.setDecodeType(CodeCallback.TAG_GOODS, CodeCallback.TAG_LPN);

        mLpnOrGoodsAdapter = createLpnOrGoodsAdapter();

    }

    @NonNull
    private CommonAdapter<PackingLpnOrGoods> createLpnOrGoodsAdapter() {
        return new CommonAdapter<PackingLpnOrGoods>(this, null, R.layout.item_packing_take) {

            private View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    mLpnOrGoodsList.remove(position);
                    mLpnOrGoodsAdapter.update(mLpnOrGoodsList);
                }
            };

            @Override
            public void setItemContent(ViewHolder holder, PackingLpnOrGoods packingLpnOrGoods, int position) {
                String boxCode = packingLpnOrGoods.getBoxCode();
                boolean empty = StringUtils.isEmpty(boxCode);

                View del = holder.getViewById(R.id.item_packing_take_imgBtn_delete);
                del.setTag(position);
                del.setOnClickListener(l);

                if (empty) {
                    //当前是货品
                    holder.getViewById(R.id.item_packing_take_ltv_taking_qty).setVisibility(View.VISIBLE);
                    holder.getViewById(R.id.item_packing_take_ltv_batch_no).setVisibility(View.VISIBLE);
                    double quantity = packingLpnOrGoods.getQuantity();
                    holder.setLabelText(R.id.item_packing_take_ltv_batch_no, packingLpnOrGoods.getBatchNo())
                            .setLabelText(R.id.item_packing_take_ltv_sku_or_lpn, packingLpnOrGoods.getSkuCode())
                            .setLabelText(R.id.item_packing_take_ltv_type, "货品")
                            .setLabelText(R.id.item_packing_take_ltv_taking_qty, String.valueOf(quantity));
                } else {
                    //当前是Lpn
                    holder.getViewById(R.id.item_packing_take_ltv_taking_qty).setVisibility(View.GONE);
                    holder.getViewById(R.id.item_packing_take_ltv_batch_no).setVisibility(View.GONE);

                    holder.setLabelText(R.id.item_packing_take_ltv_sku_or_lpn, packingLpnOrGoods.getBoxCode())
                            .setLabelText(R.id.item_packing_take_ltv_type, "箱子");
                }

            }
        };
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        setInputCode = (SystemEditText) findViewById(R.id.activity_packing_add_lpn_or_goods_operate_set_input_code);
        lvAddLpnOrGoods = (ListView) findViewById(R.id.activity_packing_add_lpn_or_goods_operate_lv_add);
        tvSubmitF1 = (TextView) findViewById(R.id.activity_packing_add_lpn_or_goods_operate_tv_submit_f1);
        tvPackingOkF2 = (TextView) findViewById(R.id.activity_packing_add_lpn_or_goods_operate_tv_packing_ok_f2);
        tvSubmitF1.setOnClickListener(this);
        tvPackingOkF2.setOnClickListener(this);

        ltvOperateLpn = (LabelTextView) findViewById(R.id.activity_packing_add_lpn_or_goods_operate_ltv_out_lpn_no);

        setInputCode.setOnToTextSearchListener(this);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_packing_add_lpn_or_goods_operate_tv_submit_f1) {
            submit(PackingSubmitParams.FLAG_SUBMIT);
        } else if (i == R.id.activity_packing_add_lpn_or_goods_operate_tv_packing_ok_f2) {
            submit(PackingSubmitParams.FLAG_OVER);
        }
    }

    @Override
    public boolean onKeyF1() {
        submit(PackingSubmitParams.FLAG_SUBMIT);
        return true;
    }

    @Override
    public boolean onKeyF2() {
        submit(PackingSubmitParams.FLAG_OVER);
        return true;
    }

    private void submit(@PackingSubmitParams.SubmitFlag String flag) {
        displayProgressDialog("提交中");
        PackingSubmitParams subParams = new PackingSubmitParams();
        subParams.setBoxCode(mOperateLpn.getBoxCode());
        subParams.setFlag(flag);
        subParams.setPackageNo(mOperateLpn.getPackageNo());
        subParams.setPackStatus(mOperateLpn.getPackStatus());
        subParams.setHikey(mOperateLpn.getHikey());
        subParams.setPackSkuVos(mLpnOrGoodsList);

        ModelService.post(ApiUrl.URL_PACKING_SUBMIT, subParams, new ResultCallback() {
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
                cancelProgressDialog();
                ToastUtils.show(PackingAddLpnOrGoodsOperateActivity.this, "提交成功");
                finish();

            }
        });
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ltvOperateLpn.setText(mOperateLpn.getBoxCode());
        lvAddLpnOrGoods.setAdapter(mLpnOrGoodsAdapter);
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
}
