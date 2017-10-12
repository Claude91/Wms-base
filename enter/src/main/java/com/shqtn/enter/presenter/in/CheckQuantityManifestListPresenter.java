package com.shqtn.enter.presenter.in;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.in.CheckQuantityManifest;
import com.shqtn.base.bean.params.CheckQuantityManifestParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.CheckType;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/10/12.
 */

public class CheckQuantityManifestListPresenter extends AbstractListActivityPresenter {
    private CommonAdapter<CheckQuantityManifest> mManifestAdapter;
    private ArrayList<CheckQuantityManifest> mManifestList;
    private CheckQuantityManifestParams mManifestParams = new CheckQuantityManifestParams();
    private ResultCallback mManifestCallback = new ResultCallback() {
        @Override
        public void onAfter() {
            super.onAfter();
            getView().onRefreshComplete();
            getView().cancelProgressDialog();
        }

        @Override
        public void onFailed(String msg) {
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            mManifestList = getRows(response.getRows(), CheckQuantityManifest.class);
            mManifestAdapter.update(mManifestList);

        }
    };

    @Override
    public void init() {
        super.init();

        mManifestAdapter = new CommonAdapter<CheckQuantityManifest>(getAty().getContext(), null, R.layout.item_check_quantity_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, CheckQuantityManifest checkQuantityManifest, int position) {
                holder.setLabelText(R.id.item_check_quantity_manifest_ltv_manifest_no, checkQuantityManifest.getDocNo())
                        .setLabelText(R.id.item_check_quantity_manifest_ltv_check_level, CheckType.getPeriodName(checkQuantityManifest.getPeriod()))
                        .setLabelText(R.id.item_check_quantity_manifest_ltv_check_type, CheckType.getTypeName(checkQuantityManifest.getCheckType()));
            }
        };

        ListActivityController.View view = getView();
        view.setTitle("盘点任务单列表");
        view.setEditTextHint("请输入任务单号编码");
        view.setScanningType(CodeCallback.TAG_MANIFEST);

        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);

        view.setAdapter(mManifestAdapter);
        refresh();
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        if (mManifestList == null || mManifestList.size() == 0) {
            ToastUtils.show(getAty().getContext(), "当前仓库无任务");
            return;
        }

        for (CheckQuantityManifest checkQuantityManifest : mManifestList) {
            if (checkQuantityManifest.getDocNo().equals(manifest.getDocNo())) {
                toManifestCheckOperateActivity(checkQuantityManifest);
                return;
            }
        }

        getView().displayMsgDialog("没有找到扫描的任务单");

    }

    private void toManifestCheckOperateActivity(CheckQuantityManifest checkQuantityManifest) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.MANIFEST_BEAN, checkQuantityManifest);

        Class aty = InfoLoadUtils.getInstance().getInActivityLoad().getCheckQuantityManifestOperateActivity(bundle);
        getAty().startActivity(aty, bundle);
    }

    @Override
    public void clickItem(int position) {
        CheckQuantityManifest checkQuantityManifest = mManifestList.get(position - 1);
        toManifestCheckOperateActivity(checkQuantityManifest);
    }

    @Override
    public void refresh() {
        mManifestParams.setWhCode(getDepotCode());
        ModelService.post(ApiUrl.check_query_manifest, mManifestParams, mManifestCallback);
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
