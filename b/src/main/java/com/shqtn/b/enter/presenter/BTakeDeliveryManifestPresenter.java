package com.shqtn.b.enter.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.b.R;
import com.shqtn.b.enter.result.BTakeDeliveryManifest;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeDeliveryGoods;
import com.shqtn.base.bean.enter.TakeDeliveryManifest;
import com.shqtn.base.bean.params.TakeDelManifestListParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.enter.InfoLoadUtils;
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

    private List<TakeDeliveryManifest> mManifestList = new ArrayList<>();

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
            ArrayList<TakeDeliveryManifest> rows = getRows(response.getRows(), TakeDeliveryManifest.class);
            int page = mManifestParams.getPage();
            if (page == C.PAGE) {
                mManifestList.clear();
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.not_manifest));
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
            mManifestAdapter.update(mManifestList);
            page++;
            mManifestParams.setPage(page);
        }
    };


    @Override
    public void init() {
        ListActivityController.View view = getView();

        view.setTitle(getAty().getString(com.shqtn.enter.R.string.take_delivery));
        mManifestAdapter = new CommonAdapter<BTakeDeliveryManifest>(R.layout.item_b_take_delivery_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, BTakeDeliveryManifest item, int position) {

            }
        };
        view.setAdapter(mManifestAdapter);
        view.setEditTextHint(getAty().getString(com.shqtn.enter.R.string.please_input_manifest));
        view.setListViewModel(PullToRefreshBase.Mode.BOTH);
        view.setScanningType(CodeCallback.TAG_MANIFEST);


        mManifestParams = new TakeDelManifestListParams();
        mManifestParams.setPage(C.PAGE);
        mManifestParams.setPageSize(C.PAGE_SIZE);
        DepotBean depot = DepotUtils.getDepot(getAty().getContext());

        mManifestAdapter.update(mManifestList);

        if (depot == null) {
            NormalInitView.notSelectDepot(getView());
            return;
        }
        mManifestParams.setWhCode(depot.getWhcode());
        refresh();
    }

    private void onLoadMoreData() {
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_DEPOT_MANIFEST_LIST, mManifestParams, mManifestCallback);
    }

    @Override
    public void clickItem(int position) {
        TakeDeliveryManifest takeDeliveryManifest = mManifestList.get(position - 1);
        toGoodsDetailsActivity(takeDeliveryManifest.getManifest());
    }

    /**
     * 跳转到货品详情页面
     *
     * @param manifest
     */
    private void toGoodsDetailsActivity(String manifest) {
        Bundle bundle = new Bundle();
        bundle.putString(C.MANIFEST_STR, manifest);
        Class<BaseActivity> goodsListActivityName = InfoLoadUtils.getInstance().getEnterActivityLoad().getTakeDelGoodsListActivity(bundle);

        getAty().startActivity(goodsListActivityName, bundle);
    }

    @Override
    public void decodeManifest(final CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog(getAty().getString(com.shqtn.enter.R.string.matching));
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_GOODS_LIST + manifest.getDocNo(), null, new ResultCallback() {
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
                ArrayList<TakeDeliveryGoods> arrayList = getRows(response.getData(), TakeDeliveryGoods.class);
                if (arrayList == null || arrayList.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.noGoodsOfManifest));
                } else {
                    toGoodsDetailsActivity(manifest.getDocNo());
                }
            }
        });
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
        super.onActivityResult(requestCode, resultCode, data);
    }
}
