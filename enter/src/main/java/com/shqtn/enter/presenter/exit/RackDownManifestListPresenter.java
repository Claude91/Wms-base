package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.RackDownManifest;
import com.shqtn.base.bean.exit.RackDownRack;
import com.shqtn.base.bean.params.RackDownManifestDetailsParams;
import com.shqtn.base.bean.params.RackDownManifestParams;
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
import java.util.List;

/**
 * 下架中 任务单据列表
 * Created by android on 2017/10/10.
 */

public class RackDownManifestListPresenter extends AbstractListActivityPresenter {

    private RackDownManifestParams mManifestParams;

    private List<RackDownManifest> mManifestList = new ArrayList<>();
    private CommonAdapter<RackDownManifest> mManifestAdapter;
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

            int page = mManifestParams.getPage();
            if (page == C.PAGE) {
                mManifestList.clear();
            }
            ArrayList<RackDownManifest> list = getRows(response.getData(), RackDownManifest.class);
            page++;
            mManifestParams.setPage(page);
            if (list == null || list.size() == 0) {
                ToastUtils.show(getAty().getContext(), "没有更多数据");
                return;
            }


            mManifestList.addAll(list);

            mManifestAdapter.update(mManifestList);
        }
    };
    private RackDownManifestDetailsParams mManifestDetailsParams;
    private ResultCallback mManifestDetailsCallback;

    @Override
    public void init() {
        super.init();
        ListActivityController.View view = getView();

        view.setTitle("下架任务单列表");

        view.setScanningType(CodeCallback.TAG_MANIFEST);

        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);

        mManifestParams = new RackDownManifestParams();
        mManifestParams.setPage(C.PAGE);
        mManifestParams.setPageSize(C.PAGE_SIZE);
        String whcode = DepotUtils.getDepot(getAty().getContext()).getWhcode();
        mManifestParams.setWhCode(whcode);

        mManifestDetailsParams = new RackDownManifestDetailsParams();
        mManifestDetailsParams.setWhCode(whcode);


        mManifestAdapter = new CommonAdapter<RackDownManifest>(getAty().getContext(), null, R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, RackDownManifest rackDownManifest, int position) {
                holder.setLabelText(R.id.item_manifest_ltv, rackDownManifest.getDocNo());
            }
        };
        view.setAdapter(mManifestAdapter);

    }

    @Override
    public void clickItem(int position) {
        RackDownManifest rackDownManifest = mManifestList.get(position - 1);
        toRackDownAreaSelect(rackDownManifest.getDocNo());
    }

    private void toRackDownAreaSelect(String rackDownManifest) {
        mManifestDetailsParams.setPickingNo(rackDownManifest);
        if (mManifestDetailsCallback == null) {
            mManifestDetailsCallback = new ResultCallback() {
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
                    ArrayList<RackDownRack> list = getRows(response.getData(), RackDownRack.class);
                    if (list == null || list.size() == 0) {
                        getView().displayMsgDialog("单号：" + mManifestDetailsParams.getPickingNo() + ",无明细，请重新扫描");
                        return;
                    }
                    toRackListActivity(mManifestDetailsParams.getPickingNo());
                }
            };
        }
        ModelService.post(ApiUrl.URL_RACK_DOWN_QUERY_LOCTION_LIST, mManifestDetailsParams, mManifestDetailsCallback);
    }

    private void toRackListActivity(String manifestNo) {
        Bundle bundle = new Bundle();
        bundle.putString(C.MANIFEST_STR, manifestNo);
        Class rackDownRackListActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getRackDownRackListActivity(bundle);
        getAty().startActivity(rackDownRackListActivity);
    }

    @Override
    public void refresh() {
        mManifestParams.setPage(C.PAGE);
        loadMoreData();
    }

    private void loadMoreData() {
        ModelService.post(ApiUrl.URL_RACK_DOWN_QUERY_DOCNO_LIST, mManifestParams, mManifestCallback);
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog("解码成功，查询明细中");
        toRackDownAreaSelect(manifest.getDocNo());
    }

    @Override
    public void onPullDownToRefresh() {
        refresh();
    }

    @Override
    public void onPullUpToRefresh() {
        loadMoreData();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
