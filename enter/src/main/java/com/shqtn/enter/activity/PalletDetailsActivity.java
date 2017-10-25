package com.shqtn.enter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.in.PalletLpn;
import com.shqtn.base.bean.params.PalletHaveCodeInLpnDetailsParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.LpnStatusUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;

public class PalletDetailsActivity extends BaseActivity {
    TitleView titleView;
    ListView lv;
    LabelTextView ltvLpnNO;


    private CommonAdapter<PalletLpn.LpnNoDetailsBean> mLpnAdapter;

    private String mOperatePalletNo;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_pallet_details);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        mOperatePalletNo = bundle.getString(C.OPERATE_LPN);
        mLpnAdapter = new CommonAdapter<PalletLpn.LpnNoDetailsBean>(this, null, R.layout.item_pallet_lpn_or_goods) {
            @Override
            public void setItemContent(ViewHolder holder, PalletLpn.LpnNoDetailsBean lpnNoDetailsBean, int position) {
                String lpn = lpnNoDetailsBean.getPackCode();
                View lpnGroup = holder.getViewById(R.id.item_pallet_lpn_or_goods_ll_lpn_group);
                View goodsGroup = holder.getViewById(R.id.item_pallet_lpn_or_goods_ll_goods_group);

                View imgBtnDelete = holder.getViewById(R.id.item_pallet_lpn_or_goods_imgBtn_delete);
                imgBtnDelete.setVisibility(View.GONE);

                if (StringUtils.isEmpty(lpn)) {
                    //货品
                    lpnGroup.setVisibility(View.GONE);
                    goodsGroup.setVisibility(View.VISIBLE);

                    double qty = lpnNoDetailsBean.getSkuQty();
                    holder.setLabelText(R.id.item_pallet_lpn_or_goods_goods_ltv_batch, lpnNoDetailsBean.getBatchNo())
                            .setLabelText(R.id.item_pallet_lpn_or_goods_goods_ltv_sku, lpnNoDetailsBean.getSkuCode())
                            .setLabelText(R.id.item_pallet_lpn_or_goods_goods_ltv_qty, String.valueOf(qty));
                } else {
                    //箱子
                    lpnGroup.setVisibility(View.VISIBLE);
                    goodsGroup.setVisibility(View.GONE);

                    holder.setLabelText(R.id.item_pallet_lpn_or_goods_lpn_ltv_no, lpnNoDetailsBean.getPackCode())
                            .setText(R.id.item_pallet_lpn_or_goods_lpn_tv_status, LpnStatusUtils.getStatus(lpnNoDetailsBean.getPackStatus()));
                }
            }
        };
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        lv = (ListView) findViewById(R.id.pallet_details_lv);
        ltvLpnNO = (LabelTextView) findViewById(R.id.pallet_details_ltv_lpn_no);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        titleView.setOnToBackClickListener(this);
        String whcode = DepotUtils.getDepot(this).getWhcode();
        titleView.setRightText(whcode);

        lv.setAdapter(mLpnAdapter);

        PalletHaveCodeInLpnDetailsParams lpnDetailsParams = new PalletHaveCodeInLpnDetailsParams();
        lpnDetailsParams.setWhCode(whcode);
        lpnDetailsParams.setPalletNo(mOperatePalletNo);
        ModelService.post(ApiUrl.URL_PALLET_QUERY_CODE, lpnDetailsParams,new ResultCallback() {
            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                PalletLpn lpn = getData(response.getData(), PalletLpn.class);
                mLpnAdapter.update(lpn.getPackSkus());
            }
        });

        ltvLpnNO.setText(mOperatePalletNo);
    }
}
