package com.shqtn.enter.presenter.enter;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
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
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;
import com.shqtn.enter.utils.NormalInitView;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货任务单据列表
 * Created by android on 2017/9/22.
 */

public class TakeDeliveryManifestPresenter extends AbstractListActivityPresenter {

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
                    getView().displayMsgDialog(getAty().getString(R.string.not_manifest));
                    return;
                }
                mManifestList.addAll(rows);
            } else {
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(R.string.not_more_manifest));
                } else {
                    mManifestList.addAll(rows);
                }
            }
            page++;
            mManifestParams.setPage(page);
        }
    };


    @Override
    public void init() {
        ListActivityController.View view = getView();

        view.setTitle(getAty().getString(R.string.take_delivery));
        mManifestAdapter = createManifestAdapter();
        view.setAdapter(mManifestAdapter);
        view.setEditTextHint(getAty().getString(R.string.please_input_manifest));
        view.setListViewModel(PullToRefreshBase.Mode.BOTH);
        view.setScanningType(CodeCallback.TAG_MANIFEST);


        mManifestParams = new TakeDelManifestListParams();
        mManifestParams.setPage(C.PAGE);
        mManifestParams.setPageSize(C.PAGE_SIZE);
        DepotBean depot = DepotUtils.getDepot(getAty().getContext());

        for (int i = 0; i < 20; i++) {
            TakeDeliveryManifest m = new TakeDeliveryManifest();

            // mManifestList.add(m);
        }
        mManifestAdapter.update(mManifestList);

        if (depot == null) {
            NormalInitView.notSelectDepot(getView());
            return;
        }
        mManifestParams.setWhCode(depot.getWhcode());
    }

    private void onLoadMoreData() {
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_DEPOT_MANIFEST_LIST, mManifestParams, mManifestCallback);
    }

    @Override
    public void clickItem(int position) {
        TakeDeliveryManifest takeDeliveryManifest = mManifestList.get(position - 1);
        toGoodsListActivity(takeDeliveryManifest.getManifest());
    }

    private void toGoodsListActivity(String manifest) {
        Bundle bundle = new Bundle();
        bundle.putString(C.MANIFEST_STR, manifest);
        Class<BaseActivity> goodsListActivityName = InfoLoadUtils.getInstance().getEnterActivityLoad().getTakeDelGoodsListActivity(bundle);

        getAty().startActivity(goodsListActivityName, bundle);
    }

    @Override
    public void decodeManifest(final CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog(getAty().getString(R.string.matching));
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
                ArrayList<TakeDeliveryGoods> arrayList = getData(response.getData(), TakeDeliveryGoods.class);
                if (arrayList == null || arrayList.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(R.string.noGoodsOfManifest));
                } else {
                    toGoodsListActivity(manifest.getDocNo());
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
}
