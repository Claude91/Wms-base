package com.shqtn.wms.presenter.exit;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.DepotOutManifest;
import com.shqtn.base.bean.exit.RackDownManifest;
import com.shqtn.base.bean.exit.RackDownRack;
import com.shqtn.base.bean.params.RackDownManifestDetailsParams;
import com.shqtn.base.bean.params.RackDownManifestParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;
import com.shqtn.wms.info.YTExitApiUrl;
import com.shqtn.wms.presenter.exit.params.YTDepotOutManifestByGoodsParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/12/28
 * 描述:
 * 下架修改， 可以通过货品插叙 任务单号
 *
 * @author ql
 */

public class YTRackDownManifestPresenter extends AbstractListActivityPresenter {
    public static final int SHOW_TAG_BY_GOODS = 2;
    public static final int SHOW_TAG_NORMAL = 1;
    private YTDepotOutManifestByGoodsParams mByGoodsParams = new YTDepotOutManifestByGoodsParams();

    private RackDownManifestParams mManifestParams;

    private List<RackDownManifest> mManifestList = new ArrayList<>();
    private List<RackDownManifest> mManifestByGoodsList = new ArrayList<>();
    private List<RackDownManifest> mSearchManifestList;
    private CommonAdapter<RackDownManifest> mManifestAdapter;

    private int showTag = SHOW_TAG_NORMAL;

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
            } else {
                mManifestList.addAll(list);
            }

            showTag = SHOW_TAG_NORMAL;
            mSearchManifestList = mManifestList;

            mManifestAdapter.update(mSearchManifestList);
        }
    };
    private ResultCallback byGoodsCallback = new ResultCallback() {
        @Override
        public void onFailed(String msg) {
            getView().cancelProgressDialog();
            getView().displayMsgDialog(msg);
            getView().onRefreshComplete();
        }

        @Override
        public void onSuccess(ResultBean response) {
            getView().cancelProgressDialog();
            getView().onRefreshComplete();
            List<RackDownManifest> list = getRows(response.getData(), RackDownManifest.class);
            if (mByGoodsParams.getPage() == C.PAGE) {
                mManifestByGoodsList.clear();
            }
            if (list != null) {
                mManifestByGoodsList.addAll(list);
                if (list.size() < mByGoodsParams.getPageSize()) {
                    getView().toast("已经加载全部");
                }
            } else {
                getView().toast("已经显示全部数据");
            }

            statusByGoods();
            showTag = SHOW_TAG_BY_GOODS;
            mSearchManifestList = mManifestByGoodsList;
            mManifestAdapter.update(mSearchManifestList);
        }
    };
    private RackDownManifestDetailsParams mManifestDetailsParams;
    private ResultCallback mManifestDetailsCallback;

    @Override
    public void init() {
        super.init();
        ListActivityController.View view = getView();

        view.setTitle("下架任务单列表");

        view.setScanningType(CodeCallback.TAG_MANIFEST, CodeCallback.TAG_GOODS);
        view.setEditTextHint("请扫描任务单，货品");
        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);

        mManifestParams = new RackDownManifestParams();
        mManifestParams.setPage(C.PAGE);
        mManifestParams.setPageSize(C.PAGE_SIZE);
        String whcode = DepotUtils.getDepot(getAty().getContext()).getWhcode();
        mManifestParams.setWhCode(whcode);

        mManifestDetailsParams = new RackDownManifestDetailsParams();
        mManifestDetailsParams.setWhCode(whcode);

        mByGoodsParams.setWhCode(whcode);

        getView().displayBtnClear();

        mManifestAdapter = new CommonAdapter<RackDownManifest>(getAty().getContext(), null, com.shqtn.enter.R.layout.item_manifest) {
            @Override
            public void setItemContent(ViewHolder holder, RackDownManifest rackDownManifest, int position) {
                holder.setLabelText(com.shqtn.enter.R.id.item_manifest_ltv, rackDownManifest.getDocNo());
            }
        };
        view.setAdapter(mManifestAdapter);
        refresh();
    }

    @Override
    public void clickItem(int position) {
        RackDownManifest rackDownManifest = mSearchManifestList.get(position - 1);
        toRackDownAreaSelect(rackDownManifest.getDocNo());
    }

    @Override
    public void clickClearSelect() {
        mSearchManifestList = mManifestList;
        mManifestAdapter.update(mSearchManifestList);
        statusNormal();
    }

    private void statusByGoods() {
        //显示 通过货品查询的 列表 下 listview 状态
        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);

    }

    private void statusNormal() {
        getView().setListViewModel(PullToRefreshBase.Mode.BOTH);

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
        getAty().startActivity(rackDownRackListActivity, bundle);
    }

    @Override
    public void refresh() {
        if (showTag == SHOW_TAG_NORMAL) {
            mManifestParams.setPage(C.PAGE);
        } else if (showTag == SHOW_TAG_BY_GOODS) {
            mByGoodsParams.setPage(C.PAGE);
        }
        loadMoreData();
    }

    private void loadMoreData() {
        if (showTag == SHOW_TAG_NORMAL) {
            ModelService.post(ApiUrl.URL_RACK_DOWN_QUERY_DOCNO_LIST, mManifestParams, mManifestCallback);
        } else if (showTag == SHOW_TAG_BY_GOODS) {
            ModelService.post(YTExitApiUrl.rack_down_manifest_by_goods, mByGoodsParams, byGoodsCallback);
        }

    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        getView().displayProgressDialog("解码成功,查询中");
        mByGoodsParams.setSkuCode(goods.getSkuCode());
        mByGoodsParams.setBatchNo(goods.getBatchNo());
        mByGoodsParams.setPage(C.PAGE);
        mByGoodsParams.setPageSize(C.PAGE_SIZE);
        ModelService.post(YTExitApiUrl.rack_down_manifest_by_goods, mByGoodsParams, byGoodsCallback);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
