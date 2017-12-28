package com.shqtn.wms.presenter.enter;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeDeliveryGoods;
import com.shqtn.base.bean.enter.TakeDeliveryManifest;
import com.shqtn.base.bean.params.TakeDelManifestListParams;
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
import com.shqtn.enter.utils.NormalInitView;
import com.shqtn.wms.info.YTEnterApiUrl;
import com.shqtn.wms.presenter.enter.params.TakeDeliveryScanningGoodsParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2017/12/18
 * 产品经理：鲍国军 添加需求，并决定将此功能归为该项目所有，不属于产品范畴。
 * 描述:裕田项目中，与产品差别：
 * 1.可以扫描货品，通过货品匹配任务单号。
 *
 * @author ql
 */
public class YTTakeDeliveryManifestPresenter extends AbstractListActivityPresenter {
    public static final int MANIFEST_SCANNING_GOODS = 1;
    public static final int MANIFEST_NORMAL = 0;
    private CommonAdapter mManifestAdapter;

    private TakeDelManifestListParams mManifestParams = new TakeDelManifestListParams();
    private TakeDeliveryScanningGoodsParams mScanningGoodsParams = new TakeDeliveryScanningGoodsParams();

    private List<TakeDeliveryManifest> mManifestList = new ArrayList<>();


    /**
     * 用于表示当前显示的list
     */
    private List<TakeDeliveryManifest> mNowShowList;

    private int showTag = MANIFEST_NORMAL;

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
            ArrayList<TakeDeliveryManifest> rows = getRows(response.getRows(), TakeDeliveryManifest.class);
            int page = mManifestParams.getPage();
            if (page == com.shqtn.base.C.PAGE) {
                mManifestList.clear();
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.not_manifest));
                    mManifestAdapter.update(mManifestList);
                    return;
                }
                mManifestList.addAll(rows);
            } else {
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.not_more_manifest));
                } else {
                    mManifestList.addAll(rows);
                }
            }
            mNowShowList = mManifestList;
            changeShow(MANIFEST_NORMAL);
            mManifestAdapter.update(mNowShowList);
            page++;
            mManifestParams.setPage(page);
        }
    };
    private ResultCallback scanningGoodsCallback;


    @Override
    public void init() {
        ListActivityController.View view = getView();

        view.setTitle(getAty().getString(com.shqtn.enter.R.string.take_delivery));
        mManifestAdapter = createManifestAdapter();
        view.setAdapter(mManifestAdapter);
        view.setEditTextHint("请扫描任务单号或货品编码");
        view.setListViewModel(PullToRefreshBase.Mode.BOTH);
        view.setScanningType(CodeCallback.TAG_MANIFEST, CodeCallback.TAG_GOODS);

        view.displayBtnClear();


        mManifestParams = new TakeDelManifestListParams();
        mManifestParams.setPage(com.shqtn.base.C.PAGE);
        mManifestParams.setPageSize(com.shqtn.base.C.PAGE_SIZE);

        DepotBean depot = DepotUtils.getDepot(getAty().getContext());

        mScanningGoodsParams.setWhCode(depot.getWhcode());

        mManifestAdapter.update(mManifestList);

        if (depot == null) {
            NormalInitView.notSelectDepot(getView());
            return;
        }
        mManifestParams.setWhCode(depot.getWhcode());
        refresh();
    }

    @Override
    public void clickClearSelect() {
        mNowShowList = mManifestList;
        changeShow(MANIFEST_NORMAL);
        mManifestAdapter.update(mNowShowList);
    }

    private void onLoadMoreData() {
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_DEPOT_MANIFEST_LIST, mManifestParams, mManifestCallback);
    }

    @Override
    public void clickItem(int position) {
        TakeDeliveryManifest takeDeliveryManifest = mNowShowList.get(position - 1);
        toGoodsListActivity(takeDeliveryManifest.getManifest());
    }

    private void toGoodsListActivity(String manifest) {
        Bundle bundle = new Bundle();
        bundle.putString(com.shqtn.base.C.MANIFEST_STR, manifest);
        Class<BaseActivity> goodsListActivityName = InfoLoadUtils.getInstance().getEnterActivityLoad().getTakeDelGoodsListActivity(bundle);

        getAty().startActivity(goodsListActivityName, bundle);
    }

    @Override
    public void decodeManifest(final CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog(getAty().getString(com.shqtn.enter.R.string.matching));
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_GOODS_LIST + manifest.getDocNo(), null, new ResultCallback() {
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
                ArrayList<TakeDeliveryGoods> arrayList = getRows(response.getData(), TakeDeliveryGoods.class);
                if (arrayList == null || arrayList.size() == 0) {
                    getView().displayMsgDialog(getAty().getString(com.shqtn.enter.R.string.noGoodsOfManifest));
                } else {
                    toGoodsListActivity(manifest.getDocNo());
                }
            }
        });
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        //扫描货品，
        mScanningGoodsParams.setBatchNo(goods.getBatchNo());
        mScanningGoodsParams.setSkuCode(goods.getSkuCode());
        if (scanningGoodsCallback == null) {
            scanningGoodsCallback = new ResultCallback() {
                @Override
                public void onFailed(String msg) {
                    getView().displayMsgDialog(msg);
                    getView().cancelProgressDialog();
                }

                @Override
                public void onSuccess(ResultBean response) {
                    ArrayList<TakeDeliveryManifest> rows = getRows(response.getRows(), TakeDeliveryManifest.class);
                    getView().cancelProgressDialog();
                    changeShow(MANIFEST_SCANNING_GOODS);
                    mNowShowList = rows;
                    mManifestAdapter.update(mNowShowList);
                    ToastUtils.show(getAty().getContext(), "查询成功");
                }
            };
        }
        ModelService.post(YTEnterApiUrl.take_delivery_decode_goods_get_manifest_list, mScanningGoodsParams, scanningGoodsCallback);
    }

    private void changeShow(int showTag) {
        this.showTag = showTag;

        switch (showTag) {
            case MANIFEST_NORMAL:
                getView().setListViewModel(PullToRefreshBase.Mode.BOTH);
                break;
            case MANIFEST_SCANNING_GOODS:
                getView().setListViewModel(PullToRefreshBase.Mode.DISABLED);
                break;
        }
    }


    @Override
    public void onPullDownToRefresh() {
        super.onPullDownToRefresh();
        mManifestParams.setPage(com.shqtn.base.C.PAGE);
        onLoadMoreData();
    }

    @Override
    public void onPullUpToRefresh() {
        super.onPullUpToRefresh();
        onLoadMoreData();

    }

    @Override
    public void refresh() {
        if (mManifestParams.getWhCode() == null) {
            return;
        }
        mManifestList.clear();
        onPullDownToRefresh();
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
