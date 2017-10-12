package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.PackingLpn;
import com.shqtn.base.bean.exit.PackingManifest;
import com.shqtn.base.bean.params.PackingManifestDetailsParams;
import com.shqtn.base.bean.params.PackingManifestParams;
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
 * 包装 任务单列表
 * Created by android on 2017/10/12.
 */

public class PackingManifestListPresenter extends AbstractListActivityPresenter {

    private PackingManifestDetailsParams mManifestDetailsParams;
    private ResultCallback mManifestDetailsCallback;
    private CommonAdapter<PackingManifest> mManifestAdapter;
    private ArrayList<PackingManifest> mManifestList;
    private String mScanningManifest;
    private PackingManifestParams mManifestParams = new PackingManifestParams();
    private ResultCallback mManifestListCallback = new ResultCallback() {
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
            ArrayList<PackingManifest> rows = getRows(response.getRows(), PackingManifest.class);
            if (rows == null || rows.size() == 0) {
                getView().displayMsgDialog("当前仓库无任务单号");
                return;
            }

            mManifestList = rows;
            mManifestAdapter.update(mManifestList);
        }
    };


    @Override
    public void init() {
        super.init();
        mManifestParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());

        mManifestAdapter = new CommonAdapter<PackingManifest>(getAty().getContext(), null, R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, PackingManifest packingManifest, int position) {
                holder.setLabelText(R.id.item_manifest_ltv, packingManifest.getManifest());
            }
        };


        ListActivityController.View view = getView();
        view.setTitle("包装任务单列表");

        view.setScanningType(CodeCallback.TAG_MANIFEST);

        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);


        view.setAdapter(mManifestAdapter);
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        for (PackingManifest packingManifest : mManifestList) {
            if (manifest.getDocNo().equals(packingManifest.getManifest())) {
                queryManifest(manifest.getDocNo());
                return;
            }
        }
        String format = String.format("扫描任务单:%s %n 不在当前列表中", manifest.getDocNo());
        getView().displayMsgDialog(format);
    }


    private void queryManifest(String manifest) {
        getView().displayProgressDialog("加载中");
        if (mManifestDetailsParams == null) {
            mManifestDetailsParams = new PackingManifestDetailsParams();
        }

        mManifestDetailsParams.setPackageNo(manifest);
        mManifestDetailsParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        if (mManifestDetailsCallback == null) {
            mManifestDetailsCallback = createManifestCallback();
        }
        mScanningManifest = manifest;
        ModelService.post(ApiUrl.URL_PACKING_MANIFEST_GET_DETAILS, mManifestDetailsParams, mManifestDetailsCallback);
    }

    private ResultCallback createManifestCallback() {
        return new ResultCallback() {
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
                ArrayList<Object> rows = getRows(response.getData(), PackingLpn.class);
                if (rows == null || rows.size() < 0) {
                    getView().displayMsgDialog("任务单中内容为空");
                    return;
                }
                toManifestDetailsActivity(mScanningManifest);

            }
        };
    }

    @Override
    public void clickItem(int position) {
        PackingManifest packingManifest = mManifestList.get(position - 1);
        toManifestDetailsActivity(packingManifest.getPackageNo());
    }

    private void toManifestDetailsActivity(String packageNo) {
        Bundle bundle = new Bundle();
        bundle.putString(C.MANIFEST_STR, packageNo);

        Class packingGoodsListActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getPackingLpnListActivity(bundle);
        getAty().startActivity(packingGoodsListActivity, bundle);
    }

    @Override
    public void refresh() {
        ModelService.post(ApiUrl.URL_PACKING_DEPOT_OF_MANIFEST, mManifestParams, mManifestListCallback);
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
