package com.shqtn.enter.activity.in;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.base.IGoods;
import com.shqtn.base.bean.in.PalletCheckLpn;
import com.shqtn.base.bean.params.PalletHaveCodeInSubmitParams;
import com.shqtn.base.bean.params.PalletLpnCheckParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.LpnStatusUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ql
 */
public class PMHaveCodeAddGoodsOrLpnActivity extends BaseActivity implements CodeController.View {

    TitleView titleView;
    SystemEditText setInputCode;
    LabelTextView ltvOperateLpn;
    ListView lvAdd;
    TextView tvSubmitF1;

    private List<ItemLpnOrGoods> mPalletAddGoodsOrLpnList = new ArrayList<>();
    private String mOperateLpn;
    private CommonAdapter<ItemLpnOrGoods> mLpnOrGoodsAdapter;

    private CodeController.DecodeCallback mDecodeCallback;
    private CodeController.Presenter mCodePrestener;


    private PalletLpnCheckParams mLpnCheckParams;
    private ResultCallback mLpnCheckCallback;

    /**
     * item 中点击删除
     */
    private View.OnClickListener mItemDelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mPalletAddGoodsOrLpnList.remove(position);
            mLpnOrGoodsAdapter.update(mPalletAddGoodsOrLpnList);
        }
    };

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_pmhave_code_add_goods_or_lpn);
    }


    @Override
    public void initData() {
        super.initData();
        mOperateLpn = getBundle().getString(C.OPERATE_LPN);

        mDecodeCallback = new DecodeCallbackImpl() {
            @Override
            public void decodeLpn(CodeLpn lpn) {
                super.decodeLpn(lpn);
                displayProgressDialog("解码成功，正在查询箱子详细信息");
                checkLpnNo(lpn.getLpnNo());
            }

            @Override
            public void decodeGoods(CodeGoods goods) {
                super.decodeGoods(goods);

                cancelProgressDialog();
                double quantity = goods.getQuantity();
                if (quantity <= 0) {
                    quantity = 1;
                }
                for (ItemLpnOrGoods itemLpnOrGoods : mPalletAddGoodsOrLpnList) {
                    if (GoodsUtils.isSame(itemLpnOrGoods, goods)) {

                        double qty = itemLpnOrGoods.qty;
                        itemLpnOrGoods.qty = NumberUtils.getDouble(qty + quantity);

                        mLpnOrGoodsAdapter.update(mPalletAddGoodsOrLpnList);
                        return;
                    }
                }
                ItemLpnOrGoods itemGoods = new ItemLpnOrGoods();
                itemGoods.skuCode = goods.getSkuCode();
                itemGoods.batchNo = goods.getBatchNo();
                itemGoods.ownerId = goods.getOwnerCode();
                itemGoods.qty = quantity;
                mPalletAddGoodsOrLpnList.add(itemGoods);
                mLpnOrGoodsAdapter.update(mPalletAddGoodsOrLpnList);
            }

            @Override
            public void decodeOther(AllotBean code) {
                super.decodeOther(code);
                displayMsgDialog("扫描任务单据号不匹配，请重新扫描");
            }

        };

        mCodePrestener = new CodePresenterImpl(this);
        mCodePrestener.setDecodeCallback(mDecodeCallback);
        mCodePrestener.setDecodeType(CodeCallback.TAG_GOODS, CodeCallback.TAG_LPN);
    }

    private void checkLpnNo(String lpnNo) {
        if (mLpnCheckParams == null) {
            mLpnCheckParams = new PalletLpnCheckParams();
        }
        mLpnCheckParams.setPalletNo(lpnNo);

        if (mLpnCheckCallback == null) {
            mLpnCheckCallback = new ResultCallback() {
                @Override
                public void onFailed(String msg) {
                    cancelProgressDialog();
                    displayMsgDialog(msg);
                }

                @Override
                public void onSuccess(ResultBean response) {
                    PalletCheckLpn data = getData(response.getData(), PalletCheckLpn.class);
                    cancelProgressDialog();
                    for (ItemLpnOrGoods itemLpnOrGoods : mPalletAddGoodsOrLpnList) {
                        if (data.getPalletNo().equals(itemLpnOrGoods.lpn)) {
                            displayMsgDialog("箱子:" + data.getPalletNo() + "已经添加不能重复添加");
                            return;
                        }
                    }
                    ItemLpnOrGoods lpn = new ItemLpnOrGoods();
                    lpn.lpn = data.getPalletNo();
                    lpn.status = data.getStatus();
                    mPalletAddGoodsOrLpnList.add(lpn);
                    mLpnOrGoodsAdapter.update(mPalletAddGoodsOrLpnList);
                }
            };
        }

        ModelService.post(ApiUrl.URL_PM_PALLET_NO_CHECK, mLpnCheckParams, mLpnCheckCallback);
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);
        titleView.setOnRightTextClickListener(new TitleView.OnRightTextClickListener() {
            @Override
            public void onOnClick() {
                toLpnDetailsActivity();
            }
        });

        setInputCode = (SystemEditText) findViewById(R.id.activity_pmhave_code_add_goods_or_lpn_set_input_code);
        lvAdd = (ListView) findViewById(R.id.activity_pmhave_code_add_goods_or_lpn_lv);

        ltvOperateLpn = (LabelTextView) findViewById(R.id.activity_pmhave_code_add_goods_or_lpn_ltv_operate_lpn);


        tvSubmitF1 = (TextView) findViewById(R.id.activity_pmhave_code_add_goods_or_lpn_tv_submit_f1);
        tvSubmitF1.setOnClickListener(this);
    }

    /**
     * 查看 托盘明细
     */
    private void toLpnDetailsActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(C.OPERATE_LPN, mOperateLpn);
        Class palletDetailsActivity = InfoLoadUtils.getInstance().getInActivityLoad().getPalletDetailsActivity(bundle);
        startActivity(palletDetailsActivity, bundle);
    }


    @Override
    public void initWidget() {
        super.initWidget();
        ltvOperateLpn.setText(mOperateLpn);

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                mCodePrestener.toDecode(content);
            }
        });

        mLpnOrGoodsAdapter = new CommonAdapter<ItemLpnOrGoods>(this, null, R.layout.item_pallet_lpn_or_goods) {
            @Override
            public void setItemContent(ViewHolder holder, ItemLpnOrGoods itemLpnOrGoods, int position) {
                String lpn = itemLpnOrGoods.lpn;
                View lpnGroup = holder.getViewById(R.id.item_pallet_lpn_or_goods_ll_lpn_group);
                View goodsGroup = holder.getViewById(R.id.item_pallet_lpn_or_goods_ll_goods_group);

                View imgBtnDelete = holder.getViewById(R.id.item_pallet_lpn_or_goods_imgBtn_delete);
                imgBtnDelete.setTag(position);
                imgBtnDelete.setOnClickListener(mItemDelClickListener);

                if (StringUtils.isEmpty(lpn)) {
                    //货品
                    lpnGroup.setVisibility(View.GONE);
                    goodsGroup.setVisibility(View.VISIBLE);

                    double qty = itemLpnOrGoods.qty;
                    holder.setLabelText(R.id.item_pallet_lpn_or_goods_goods_ltv_batch, itemLpnOrGoods.batchNo)
                            .setLabelText(R.id.item_pallet_lpn_or_goods_goods_ltv_sku, itemLpnOrGoods.skuCode)
                            .setLabelText(R.id.item_pallet_lpn_or_goods_goods_ltv_qty, String.valueOf(qty));
                } else {
                    //箱子
                    //货品
                    lpnGroup.setVisibility(View.VISIBLE);
                    goodsGroup.setVisibility(View.GONE);

                    holder.setLabelText(R.id.item_pallet_lpn_or_goods_lpn_ltv_no, itemLpnOrGoods.lpn)
                            .setText(R.id.item_pallet_lpn_or_goods_lpn_tv_status, LpnStatusUtils.getStatus(itemLpnOrGoods.status));
                }

            }
        };
        lvAdd.setAdapter(mLpnOrGoodsAdapter);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_pmhave_code_add_goods_or_lpn_tv_submit_f1) {
            toSubmit();
        }
    }

    public boolean isSubmit() {
        if (mPalletAddGoodsOrLpnList.size() == 0) {
            displayMsgDialog("请添加货品或箱子");
            return false;
        }
        return true;
    }

    private void toSubmit() {
        if (isSubmit()) {
            displayProgressDialog("提交中");
            PalletHaveCodeInSubmitParams submitParams = new PalletHaveCodeInSubmitParams();
            submitParams.setWhCode(DepotUtils.getDepot(this).getWhcode());
            submitParams.setPalletNo(mOperateLpn);
            ArrayList<PalletHaveCodeInSubmitParams.DataBean> dataList = new ArrayList<>();
            for (ItemLpnOrGoods itemLpnOrGoods : mPalletAddGoodsOrLpnList) {
                PalletHaveCodeInSubmitParams.DataBean d = new PalletHaveCodeInSubmitParams.DataBean();
                if (StringUtils.isEmpty(itemLpnOrGoods.lpn)) {
                    d.setBatchNo(itemLpnOrGoods.batchNo);
                    d.setIquantity(itemLpnOrGoods.qty);
                    d.setInvcode(itemLpnOrGoods.skuCode);
                    d.setOwnerId(itemLpnOrGoods.ownerId);
                } else {
                    d.setLpnNo(itemLpnOrGoods.lpn);
                }
                dataList.add(d);
            }
            submitParams.setList(dataList);
            ModelService.post(ApiUrl.URL_PM_PALLET_IN_HAVE_CODE_SUBMIT, submitParams, new ResultCallback() {
                @Override
                public void onFailed(String msg) {
                    cancelProgressDialog();
                    displayMsgDialog(msg);
                }

                @Override
                public void onSuccess(ResultBean response) {
                    ToastUtils.show(PMHaveCodeAddGoodsOrLpnActivity.this, "提交成功");
                    cancelProgressDialog();
                    finish();
                }
            });

        }
    }

    @Override
    public boolean onKeyF1() {
        toSubmit();
        return true;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setEditTextHint(String hint) {

    }

    private class ItemLpnOrGoods extends IGoods {
        String batchNo;
        String skuCode;
        double qty;
        String lpn;
        String status;
        String ownerId;

        @Override
        public String getGoodsBatchNo() {
            return batchNo;
        }

        @Override
        public String getGoodsSku() {
            return skuCode;
        }
    }
}
