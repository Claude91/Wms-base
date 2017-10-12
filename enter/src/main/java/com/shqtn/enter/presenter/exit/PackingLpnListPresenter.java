package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.PackingLpn;
import com.shqtn.base.bean.params.PackingManifestDetailsParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.PackingStatusUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * Created by android on 2017/10/12.
 */

public class PackingLpnListPresenter extends AbstractListActivityPresenter {

    private CommonAdapter<PackingLpn> mLpnAdapter;
    private ArrayList<PackingLpn> mLpnList;

    private PackingManifestDetailsParams mManifestParams;
    private ResultCallback mManifestCallback;
    private String mOperateManifest;


    @Override
    public void init() {
        super.init();
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);

        mLpnAdapter = new CommonAdapter<PackingLpn>(getAty().getContext(), null, R.layout.item_packing) {
            @Override
            public void setItemContent(ViewHolder holder, PackingLpn packingLpn, int position) {
                holder.setLabelText(R.id.item_packing_ltv_lpn_no, packingLpn.getBoxCode());
                holder.setText(R.id.item_packing_tv_status, PackingStatusUtils.getStatus(packingLpn.getPackStatus()));
            }
        };

        ListActivityController.View view = getView();
        view.setTitle("包装箱子列表");
        view.displayLabel();
        view.setLabelName("操作任务单号");
        view.setLabelContent(mOperateManifest);
        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);

    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        if (mLpnList == null || mLpnList.size() == 0) {
            getView().displayMsgDialog("当前任务单没有数据");
            return;
        }

        for (PackingLpn packingLpn : mLpnList) {
            if (lpn.getLpnNo().equals(packingLpn.getBoxCode())) {
                toAddLpnOrGoodsActivity(packingLpn);
                return;
            }
        }
        String format = String.format("箱子:%s %n不在当前列表中", lpn.getLpnNo());
        getView().displayMsgDialog(format);
    }

    @Override
    public void clickItem(int position) {
        PackingLpn packingLpn = mLpnList.get(position - 1);
        toAddLpnOrGoodsActivity(packingLpn);
    }

    private void toAddLpnOrGoodsActivity(PackingLpn packingLpn) {
        Bundle bundle = getBundle();
        bundle.putParcelable(C.OPERATE_LPN, packingLpn);

        Class packingAddLpnOrGoodsOperateActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getPackingAddLpnOrGoodsOperateActivity(bundle);
        getAty().startActivity(packingAddLpnOrGoodsOperateActivity, bundle);
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }

    @Override
    public void refresh() {
        queryManifest(mOperateManifest);
    }

    private void queryManifest(String manifest) {
        getView().displayProgressDialog("加载中");
        if (mManifestParams == null) {
            mManifestParams = new PackingManifestDetailsParams();
        }
        mManifestParams.setPackageNo(manifest);
        mManifestParams.setWhCode(getDepotCode());
        if (mManifestCallback == null) {
            mManifestCallback = createManifestCallback();
        }

        ModelService.post(ApiUrl.URL_PACKING_MANIFEST_GET_DETAILS, mManifestParams, mManifestCallback);
    }

    private ResultCallback createManifestCallback() {
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
                mLpnList = getRows(response.getData(), PackingLpn.class);

                if (mLpnList == null || mLpnList.size() < 0) {
                    mLpnAdapter.update(mLpnList);
                    getView().displayMsgDialog("任务单中内容为空");
                    return;
                }
                mLpnAdapter.update(mLpnList);
            }
        };
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
