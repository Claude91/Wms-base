package com.shqtn.b.enter.presenter;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.b.enter.EnterUrl;
import com.shqtn.b.enter.params.BTakeBoxQueryManifestParams;
import com.shqtn.b.enter.result.BTakeBoxManifest;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;

/**
 * 装箱任务单列表页面
 * 描述：
 * 制作接口人：陈伟
 * 2018-1-19 ，查询任务单列表中 获得任务单IKEY，并在最后提交接口中添加 ikey，
 * Created by android on 2017/10/12.
 */

public class BTakeBoxManifestQueryPresenter extends AbstractListActivityPresenter {

    private CommonAdapter<BTakeBoxManifest> mGoodsAdapter;

    private ResultCallback mCallback = new ResultCallback() {

        @Override
        public void onFailed(String msg) {
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
            getView().displayMsgDialog(msg);
        }

        @Override
        public void onSuccess(ResultBean response) {
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
            mManifestList = getRows(response.getData(), BTakeBoxManifest.class);
            mGoodsAdapter.update(mManifestList);
        }
    };
    private ArrayList<BTakeBoxManifest> mManifestList;
    private String mManifest;

    @Override
    public void init() {
        super.init();

        mGoodsAdapter = createManifestAdapter();

        ListActivityController.View view = getView();
        view.setTitle("装箱任务单详情查询");
        view.setEditTextHint("请输入任务单或货品");

        view.setScanningType(CodeCallback.TAG_MANIFEST);

        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);
        view.setAdapter(mGoodsAdapter);

        refresh();
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        mManifest = manifest.getDocNo();
        for (BTakeBoxManifest bTakeBoxManifest : mManifestList) {
            if (bTakeBoxManifest.getManifest().equals(mManifest)) {
                toGoodsListFromManifest(bTakeBoxManifest);
                return;
            }
        }
        if (mManifest == null) {
            mManifest = "未知任务单";
        }
        getView().displayMsgDialog(String.format("未查询到任务单号:%s", mManifest));
    }

    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        refresh();
    }


    @Override
    public void clickItem(int position) {
        BTakeBoxManifest takeBoxManifest = mManifestList.get(position - 1);
        toGoodsListFromManifest(takeBoxManifest);
    }

    /**
     * 跳转到任务单详情，货品列表
     *
     * @param takeBoxManifest
     */
    private void toGoodsListFromManifest(BTakeBoxManifest takeBoxManifest) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.MANIFEST_BEAN, takeBoxManifest);
        Bundle listActivityBundle = ListActivity.createListActivityBundle(BTakeBoxGoodsListFromManifestPresenter.class);
        bundle.putAll(listActivityBundle);
        getAty().startActivity(ListActivity.class, bundle);
    }

    @Override
    public void refresh() {
        DepotBean depot = DepotUtils.getDepot(getAty().getContext());
        ModelService.post(String.format("%s/%s", EnterUrl.take_box_manifest, depot.getWhcode()), null, mCallback);
    }


    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
