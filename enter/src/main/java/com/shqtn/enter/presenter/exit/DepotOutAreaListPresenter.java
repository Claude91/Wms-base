package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.DepotOutArea;
import com.shqtn.base.bean.exit.RackDownManifest;
import com.shqtn.base.bean.params.DepotOutAreaListParams;
import com.shqtn.base.bean.params.DepotOutManifestListParams;
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

import java.util.ArrayList;

/**
 * Created by android on 2017/10/10.
 */

public class DepotOutAreaListPresenter extends AbstractListActivityPresenter {

    private CommonAdapter<DepotOutArea> mAreaAdapter;
    private DepotOutAreaListParams mAreaListParams = new DepotOutAreaListParams();
    private ArrayList<DepotOutArea> mAreaList = new ArrayList<>();

    private DepotOutManifestListParams mManifestListParams = new DepotOutManifestListParams();
    private ResultCallback mManifestListCallback;

    private ResultCallback mAreaListCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            super.onAfter();
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
            getView().onRefreshComplete();
        }

        @Override
        public void onSuccess(ResultBean response) {
            //{"rs":true,"total":3,"rows":[{"areaCode":"202","areaName":"内销收货区"},{"areaCode":"201","areaName":"内销存货区"},{"areaCode":"200","areaName":"内销拣货区"}]}
            ArrayList<DepotOutArea> list = getRows(response.getRows(), DepotOutArea.class);
            if (mAreaListParams.getPage() == C.PAGE) {
                mAreaList.clear();
                if (list != null) {
                    mAreaList.addAll(list);
                }
                mAreaAdapter.update(mAreaList);
                return;
            }
            if (list == null || list.size() <= 0) {
                getView().displayMsgDialog("没有更多数据");
            } else {
                int page = mAreaListParams.getPage();
                page++;
                mAreaListParams.setPage(page);

                mAreaList.addAll(list);
                mAreaAdapter.update(mAreaList);
            }
        }
    };

    @Override
    public void init() {
        super.init();
        mAreaAdapter = new CommonAdapter<DepotOutArea>(getAty().getContext(), null, R.layout.item_area) {
            @Override
            public void setItemContent(ViewHolder holder, DepotOutArea depotOutArea, int position) {
                holder.setLabelText(R.id.item_area_ltv_area_code, depotOutArea.getAreaCode());
                holder.setLabelText(R.id.item_area_ltv_area_name, depotOutArea.getAreaName());
            }
        };
        ListActivityController.View view = getView();
        view.setAdapter(mAreaAdapter);
        view.setListViewModel(PullToRefreshBase.Mode.BOTH);

        view.setScanningType(CodeCallback.TAG_MANIFEST);

        view.setTitle("出库区域选择");
        view.setEditTextHint("请填写区域编码");

        view.hideLabel();

        mAreaListParams.setPageSize(C.PAGE_SIZE);
        mAreaListParams.setPage(C.PAGE);
        DepotBean depot = DepotUtils.getDepot(getAty().getContext());
        if (depot != null) {
            mAreaListParams.setWhcode(depot.getWhcode());
        }


    }

    @Override
    public void onPullUpToRefresh() {
        super.onPullUpToRefresh();
        loadMoreData();
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    private void loadMoreData() {
        ModelService.post(ApiUrl.URL_DEPOT_OUT_AREA_LIST, mAreaListParams, mAreaListCallback);
    }

    @Override
    public void clickItem(int position) {
        DepotOutArea depotOutArea = mAreaList.get(position);
        toManifestActivity(depotOutArea.getAreaCode());
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        //扫描区域
        getView().displayProgressDialog("查询详情中");
        mManifestListParams.setAreaCode(manifest.getDocNo());
        if (mManifestListCallback == null) {
            mManifestListCallback = new ResultCallback() {
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
                    ArrayList<RackDownManifest> rows = getRows(response.getRows(), RackDownManifest.class);
                    if (rows == null || rows.size() == 0) {
                        getView().displayMsgDialog("当前区域无任务");
                        return;
                    }

                    toManifestActivity(mManifestListParams.getAreaCode());
                }
            };
        }
        ModelService.post(ApiUrl.URL_DEPOT_OUT_MANIFEST_LIST, mManifestListParams, mManifestListCallback);
    }

    private void toManifestActivity(String areaCode) {
        Bundle bundle = new Bundle();
        bundle.putString(C.AREA_CODE, areaCode);
        Class depotOutAreaListActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getDepotOutManifestListActivity(bundle);

        getAty().startActivity(depotOutAreaListActivity, bundle);
    }

    @Override
    public void refresh() {
        mAreaListParams.setPage(C.PAGE);
        loadMoreData();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
