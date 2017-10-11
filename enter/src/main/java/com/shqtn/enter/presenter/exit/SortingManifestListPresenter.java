package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.SortingGoods;
import com.shqtn.base.bean.exit.SortingManifest;
import com.shqtn.base.bean.params.SortingManifestDetailsParams;
import com.shqtn.base.bean.params.SortingManifestParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * 分拣任务单列表
 * Created by android on 2017/10/11.
 */

public class SortingManifestListPresenter extends AbstractListActivityPresenter {
    private CommonAdapter<SortingManifest> mManifestAdapter;
    private ArrayList<SortingManifest> mManifestList = new ArrayList<>();
    private SortingManifestParams mManifestParams;
    private SortingManifestDetailsParams mManifestDetailsParams;
    private ResultCallback mManifestDetailsCallback;

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
            ArrayList<SortingManifest> list = getRows(response.getRows(), SortingManifest.class);
            int page = mManifestParams.getPage();
            if (page == 1) {
                mManifestList.clear();
            }
            if (list == null || list.size() == 0) {
                ToastUtils.show(getAty().getContext(), "没有更多数据");
                return;
            }
            mManifestList.addAll(list);
            page++;
            mManifestParams.setPage(page);
            mManifestAdapter.update(mManifestList);

        }
    };

    @Override
    public void init() {
        super.init();

        mManifestAdapter = new CommonAdapter<SortingManifest>(getAty().getContext(), null, R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, SortingManifest sortingManifest, int position) {
                holder.setLabelText(R.id.item_manifest_ltv, sortingManifest.getDeliveryNo());
            }
        };
        ListActivityController.View view = getView();
        view.setRefreshing();
        view.setTitle("分拣任务单列表");
        view.setEditTextHint("请输入任务单号");
        view.setListViewModel(PullToRefreshBase.Mode.BOTH);
        view.setScanningType(CodeCallback.TAG_MANIFEST);
        view.setAdapter(mManifestAdapter);
        mManifestParams = new SortingManifestParams();
        mManifestParams.setPage(C.PAGE);
        mManifestParams.setPageSize(C.PAGE_SIZE);
        mManifestParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());

    }


    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog("解码成功，正在查询详情");
        queryManifestDetails(manifest.getDocNo());
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    @Override
    public void onPullUpToRefresh() {
        super.onPullUpToRefresh();
        loadMoreData();
    }

    @Override
    public void clickItem(int position) {
        SortingManifest sortingManifest = mManifestList.get(position - 1);
        toGoodsListActivity(sortingManifest.getDeliveryNo());
    }

    @Override
    public void refresh() {
        mManifestParams.setPage(C.PAGE);
        loadMoreData();
    }

    public void loadMoreData() {
        ModelService.post(ApiUrl.URL_SORTING_GET_MANIFEST_LIST, mManifestParams, mManifestCallback);
    }

    private void queryManifestDetails(String manifest) {
        if (mManifestDetailsParams == null) {
            mManifestDetailsParams = new SortingManifestDetailsParams();
        }
        mManifestDetailsParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        mManifestDetailsParams.setDeliveryNo(manifest);
        if (mManifestDetailsCallback == null) {
            mManifestDetailsCallback = createManifestDetailsCallback();
        }
        ModelService.post(ApiUrl.URL_SORTING_GET_MANIFEST_DETAILS, mManifestDetailsParams, mManifestDetailsCallback);
    }

    private ResultCallback createManifestDetailsCallback() {

        return new ResultCallback() {
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
                ArrayList<SortingGoods> rows = getRows(response.getRows(), SortingGoods.class);
                if (rows != null && rows.size() > 0) {
                    toGoodsListActivity(rows.get(0).getDeliveryNo());
                } else {
                    getView().displayMsgDialog("该任务单下无货品");
                }
            }
        };
    }

    private void toGoodsListActivity(String deliveryNo) {
        Bundle bundle = new Bundle();
        bundle.putString(C.MANIFEST_STR, deliveryNo);
        Class sortingGoodsListActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getSortingGoodsListActivity(bundle);
        getAty().startActivity(sortingGoodsListActivity, bundle);
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
