package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.DepotOutManifest;
import com.shqtn.base.bean.params.DepotOutManifestListParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/10/11.
 */

public class DepotOutManifestListPresenter extends AbstractListActivityPresenter {
    private ArrayList<DepotOutManifest> mManifestList = new ArrayList<>();
    private String areaCode;//区域编码
    private DepotOutManifestListParams mManifestListParams;
    private CommonAdapter<DepotOutManifest> mManifestAdapter;
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
            //{"rs":true,"total":3,"rows":[{"deliveryNo":"FH20170726023"},{"deliveryNo":"FH20170726024"},{"deliveryNo":"FH20170726025"}]}
            List<DepotOutManifest> r = getRows(response.getRows(), DepotOutManifest.class);
            int page = mManifestListParams.getPage();
            if (page == C.PAGE) {
                mManifestList.clear();
            }
            if (r == null || r.size() == 0) {
                ToastUtils.show(getAty().getContext(), "没有更多数据");
                return;
            }
            mManifestList.addAll(r);
            mManifestAdapter.update(mManifestList);


            page++;
            mManifestListParams.setPage(page);

        }
    };

    @Override
    public void init() {
        super.init();
        areaCode = getBundle().getString(C.AREA_CODE);

        mManifestAdapter = new CommonAdapter<DepotOutManifest>(getAty().getContext(), null, R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, DepotOutManifest depotOutManifest, int position) {
                holder.setLabelText(R.id.item_manifest_ltv, depotOutManifest.getDeliveryNo());
            }
        };

        ListActivityController.View view = getView();
        view.setAdapter(mManifestAdapter);

        view.setTitle("出库任务单列表");
        view.setEditTextHint("请输入任务单号");

        view.setScanningType(CodeCallback.TAG_MANIFEST);

        view.setListViewModel(PullToRefreshBase.Mode.BOTH);

        mManifestListParams.setAreaCode(areaCode);
        mManifestListParams.setPage(C.PAGE);
        mManifestListParams.setPageSize(C.PAGE_SIZE);
        mManifestListParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());


        refresh();

    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        if (manifest == null || StringUtils.isEmpty(manifest.getDocNo())) {
            getView().displayMsgDialog("当前任务单内容不正确，请填写正确的任务单号");
            return;
        }
        for (DepotOutManifest depotOutManifest : mManifestList) {
            if (manifest.getDocNo().equals(depotOutManifest.getDeliveryNo())) {
                toGoodsListActivity(depotOutManifest);
                return;
            }
        }

        getView().displayMsgDialog("该单号不在当前列表中");
    }

    private void toGoodsListActivity(DepotOutManifest depotOutManifest) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.MANIFEST_BEAN, depotOutManifest);
        Class activity = InfoLoadUtils.getInstance().getExitActivityLoad().getDepotOutGoodsListActivity(bundle);
        getAty().startActivity(activity, bundle);
    }

    @Override
    public void clickItem(int position) {
        DepotOutManifest depotOutManifest = mManifestList.get(position - 1);
        toGoodsListActivity(depotOutManifest);
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

    private void loadMoreData() {
        ModelService.post(ApiUrl.URL_DEPOT_OUT_MANIFEST_LIST, mManifestListParams, mManifestCallback);
    }

    @Override
    public void refresh() {
        mManifestListParams.setPage(C.PAGE);
        loadMoreData();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
