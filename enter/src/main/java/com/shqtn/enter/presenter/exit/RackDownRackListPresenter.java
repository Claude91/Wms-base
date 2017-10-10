package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.RackDownRack;
import com.shqtn.base.bean.params.RackDownManifestDetailsParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 下架 货位信息 列表
 * Created by android on 2017/10/10.
 */

public class RackDownRackListPresenter extends AbstractListActivityPresenter {

    private List<RackDownRack> mRackList = new ArrayList<>();

    private String mOperateManifest;
    private CommonAdapter<RackDownRack> mRackListAdapter;
    private RackDownManifestDetailsParams mManifestDetailsParams = new RackDownManifestDetailsParams();
    private ResultCallback mManifestDetailsCallback = new ResultCallback() {
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
            ArrayList<RackDownRack> list = getRows(response.getData(), RackDownRack.class);
            if (list == null || list.size() == 0) {
                getView().displayMsgDialog("单号：" + mManifestDetailsParams.getPickingNo() + ",无明细，请重新扫描");
                return;
            }
            mRackList = list;
            mRackListAdapter.update(mRackList);

        }
    };

    @Override
    public void init() {
        super.init();

        mOperateManifest = getBundle().getString(C.MANIFEST_STR);

        ListActivityController.View view = getView();
        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_END);
        view.setTitle("下架货位列表");

        view.displayLabel();
        view.setLabelName("下架任务单");
        view.setLabelContent(mOperateManifest);

        view.setScanningType(CodeCallback.TAG_RACK);

        mManifestDetailsParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        mManifestDetailsParams.setPickingNo(mOperateManifest);

        mRackListAdapter = new CommonAdapter<RackDownRack>(getAty().getContext(), null, R.layout.item_rack_details) {
            @Override
            public void setItemContent(ViewHolder holder, RackDownRack rackDownRack, int position) {
                double totalLocNum = rackDownRack.getTotalLocNum();
                holder.setLabelText(R.id.item_rack_details_ltv_rack_name, rackDownRack.getLocationName())
                        .setLabelText(R.id.item_rack_details_ltv_rack_sku, rackDownRack.getLocationCode())
                        .setLabelText(R.id.item_rack_details_ltv_rack_no, rackDownRack.getPosno())
                        .setLabelText(R.id.item_rack_details_ltv_rack_floors, rackDownRack.getFloors())
                        .setLabelText(R.id.item_rack_details_ltv_rack_colums, rackDownRack.getColums())
                        .setLabelText(R.id.item_rack_details_ltv_rack_down_total_qty, String.valueOf(totalLocNum));
            }
        };

        view.setAdapter(mRackListAdapter);

        refresh();
    }

    @Override
    public void clickItem(int position) {
        RackDownRack rackDownRack = mRackList.get(position - 1);
        toGoodsListActivity(rackDownRack);
    }

    private void toGoodsListActivity(RackDownRack rackDownRack) {
        Bundle bundle = getBundle();
        bundle.putString(C.RACK_NO, rackDownRack.getLocationCode());
        Class rackDownGoodsListActivity = InfoLoadUtils.getInstance().getExitActivityLoad().getRackDownGoodsListActivity(bundle);
        getAty().startActivity(rackDownGoodsListActivity, bundle);
    }

    @Override
    public void decodeRack(CodeRack rack) {
        super.decodeRack(rack);
        for (RackDownRack rackDownRack : mRackList) {
            if (rackDownRack.getLocationCode().equals(rack.getRackNo())) {
                toGoodsListActivity(rackDownRack);
                return;
            }
        }

        getView().displayMsgDialog("扫描货位:" + rack.getRackNo() + "不存在当前列表中");
    }

    @Override
    public void refresh() {
        ModelService.post(ApiUrl.URL_RACK_DOWN_QUERY_LOCTION_LIST, mManifestDetailsParams, mManifestDetailsCallback);
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
