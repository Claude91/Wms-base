package com.shqtn.b.enter.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.b.R;
import com.shqtn.b.enter.EnterUrl;
import com.shqtn.b.enter.params.TakeDelManifestDetailsParams;
import com.shqtn.b.enter.params.TakeDelSearchManifestByGoodsParams;
import com.shqtn.b.enter.result.BTakeDeliveryManifest;
import com.shqtn.b.enter.ui.BTakeDeliveryGoodsDetailsActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.params.TakeDelManifestListParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;
import com.shqtn.enter.utils.NormalInitView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/12/22
 * 描述:版本更新，收货页面中，显示任务单据号，并显示货品明细，一个任务单中只有一个货品明细
 * 点击任务单号，进入货品明细操作
 *
 * @author ql
 */

public class BTakeDeliveryManifestPresenter extends AbstractListActivityPresenter {
    private CommonAdapter mManifestAdapter;

    private TakeDelManifestListParams mManifestParams = new TakeDelManifestListParams();

    private List<BTakeDeliveryManifest> mManifestList = new ArrayList<>();

    private List<BTakeDeliveryManifest> mSearchManifestList;

    private TakeDelManifestDetailsParams mTakeDelManifestDetailsParams = new TakeDelManifestDetailsParams();
    private TakeDelSearchManifestByGoodsParams mTakeDelSearchManifestByGoodsParams = new TakeDelSearchManifestByGoodsParams();


    private ResultCallback mManifestCallback = new ResultCallback() {

        @Override
        public void onAfter() {
            super.onAfter();
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            ArrayList<BTakeDeliveryManifest> rows = getRows(response.getData(), BTakeDeliveryManifest.class);
            int page = mManifestParams.getPage();
            if (page == C.PAGE) {
                mManifestList.clear();
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.not_manifest));
                    mSearchManifestList = mManifestList;
                    mManifestAdapter.update(mManifestList);
                    return;
                }
                mManifestList.addAll(rows);
            } else {
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.not_more_manifest));
                } else {
                    mManifestList.addAll(rows);
                }
            }
            mSearchManifestList = mManifestList;
            mManifestAdapter.update(mSearchManifestList);
            page++;
            mManifestParams.setPage(page);
        }
    };
    private ResultCallback searchManifestDetailsCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            super.onAfter();
            getView().cancelProgressDialog();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            BTakeDeliveryManifest bTakeDeliveryManifest = getData(response.getData(), BTakeDeliveryManifest.class);
            if (bTakeDeliveryManifest == null) {
                getView().displayMsgDialog(String.format("当前仓库未查询到任务单号:%s", mTakeDelManifestDetailsParams.getAsnNo()));
                return;
            }
            toGoodsDetailsActivity(mTakeDelManifestDetailsParams.getAsnNo());
        }
    };
    ResultCallback searchManifestByGoodsCallback = new ResultCallback() {
        @Override
        public void onFailed(String msg) {
            getView().cancelProgressDialog();
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            getView().cancelProgressDialog();
            mSearchManifestList = getRows(response.getData(), BTakeDeliveryManifest.class);

            mManifestAdapter.update(mSearchManifestList);
            getView().setListViewModel(PullToRefreshBase.Mode.DISABLED);

            getView().toast("查询成功");

        }
    };

    @Override
    public void init() {
        ListActivityController.View view = getView();

        view.setTitle(getAty().getString(com.shqtn.enter.R.string.take_delivery));
        mManifestAdapter = new CommonAdapter<BTakeDeliveryManifest>(R.layout.item_b_take_delivery_manifest) {
            public static final String NULL_ = "";

            @Override
            public void setItemContent(ViewHolder holder, BTakeDeliveryManifest item, int position) {
                String asnNo = item.getAsnNo();

                Double pQty = item.getPQty();
                if (pQty == null) {
                    pQty = 0d;
                }
                String pQtyStr = String.format("%.2f", pQty);
                String batchNo = item.getBatchNo();
                if (StringUtils.isEmpty(batchNo)) {
                    batchNo = NULL_;
                }
                String skuCode = item.getSkuCode();
                if (StringUtils.isEmpty(skuCode)) {
                    skuCode = NULL_;
                }
                String skuName = item.getSkuName();
                if (StringUtils.isEmpty(skuName)) {
                    skuName = NULL_;
                }
                String erpPorder = item.getErpPorder();
                if (StringUtils.isEmpty(erpPorder)) {
                    erpPorder = NULL_;
                }
                holder.setLabelText(R.id.item_b_take_delivery_manifest_ltv_manifest, asnNo)
                        .setLabelText(R.id.item_b_take_delivery_manifest_ltv_erp, erpPorder)
                        .setLabelText(R.id.item_b_take_delivery_manifest_ltv_name, skuName)
                        .setLabelText(R.id.item_b_take_delivery_manifest_ltv_sku, skuCode)
                        .setLabelText(R.id.item_b_take_delivery_manifest_ltv_batch_no, batchNo)
                        .setLabelText(R.id.item_b_take_delivery_manifest_ltv_qty, pQtyStr);
            }
        };
        view.setAdapter(mManifestAdapter);
        view.setEditTextHint("请输入任务单号或货品编码");
        view.setListViewModel(PullToRefreshBase.Mode.BOTH);
        view.setScanningType(CodeCallback.TAG_MANIFEST, CodeCallback.TAG_GOODS);

        view.displayBtnClear();

        mManifestParams = new TakeDelManifestListParams();
        mManifestParams.setPage(C.PAGE);
        mManifestParams.setPageSize(C.PAGE_SIZE);
        DepotBean depot = DepotUtils.getDepot(getAty().getContext());
        mManifestAdapter.update(mManifestList);

        if (depot == null) {
            NormalInitView.notSelectDepot(getView());
            return;
        }
        String whcode = depot.getWhcode();
        mManifestParams.setWhCode(whcode);
        mTakeDelManifestDetailsParams.setWhCode(whcode);
        mTakeDelSearchManifestByGoodsParams.setWhCode(whcode);
        refresh();
    }

    @Override
    public void clickClearSelect() {
        if (mSearchManifestList == mManifestList) {
            return;
        }
        mSearchManifestList = mManifestList;
        mManifestAdapter.update(mSearchManifestList);
        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);
    }


    private void onLoadMoreData() {
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_DEPOT_MANIFEST_LIST, mManifestParams, mManifestCallback);
    }

    @Override
    public void clickItem(int position) {
        BTakeDeliveryManifest takeDeliveryManifest = mSearchManifestList.get(position - 1);
        toGoodsDetailsActivity(takeDeliveryManifest.getAsnNo());
    }

    /**
     * 跳转到货品详情页面
     *
     * @param manifest
     */
    private void toGoodsDetailsActivity(String manifest) {
        Bundle bundle = new Bundle();
        bundle.putString(C.MANIFEST_STR, manifest);

        getAty().startActivity(BTakeDeliveryGoodsDetailsActivity.class, bundle);
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog(getAty().getString(com.shqtn.enter.R.string.matching));
        mTakeDelManifestDetailsParams.setAsnNo(manifest.getDocNo());
        ModelService.post(EnterUrl.take_del_search_manifest_details, mTakeDelManifestDetailsParams, searchManifestDetailsCallback);
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        getView().displayProgressDialog(getAty().getString(com.shqtn.enter.R.string.matching));


        mTakeDelSearchManifestByGoodsParams.setBatchNo(goods.getBatchNo());
        mTakeDelSearchManifestByGoodsParams.setSkuCode(goods.getSkuCode());


        ModelService.post(EnterUrl.take_del_search_manifest_by_goods, mTakeDelSearchManifestByGoodsParams, searchManifestByGoodsCallback);


    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        mManifestParams.setPage(C.PAGE);
        onLoadMoreData();
    }

    @Override
    public void onPullUpToRefresh() {
        super.onPullUpToRefresh();
        onLoadMoreData();
    }

    @Override
    public void refresh() {
        if (mManifestParams.getWhCode() == null) {
            return;
        }
        mManifestList.clear();
        onPullDownToRefresh();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
