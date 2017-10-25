package com.shqtn.enter.presenter.enter;

import android.content.Intent;
import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeDeliveryGoods;
import com.shqtn.base.bean.params.TakeDeliveryLpnSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.model.TakeDeliveryModel;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/9/25.
 */
public class TakeDeliveryGoodsPresenter extends AbstractListActivityPresenter {

    private List<TakeDeliveryGoods> mGoodsList;
    private String mOperateManifest;//操作 任务单；
    private CommonAdapter<TakeDeliveryGoods> mGoodsAdapter;
    private ResultCallback mLpnSubmitCallBack;

    private TakeDeliveryModel mTakeDeliveryModel;
    private DepotBean mDepot;

    private ResultCallback mGoodsListCallback = new ResultCallback() {
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
            mGoodsList = getRows(response.getData(), TakeDeliveryGoods.class);
            mGoodsAdapter.update(mGoodsList);
        }
    };

    @Override
    public void init() {
        mOperateManifest = getBundle().getString(C.MANIFEST_STR);

        ListActivityController.View view = getView();
        view.setTitle(getAty().getString(R.string.take_delivery));
        view.setListViewModel(PullToRefreshBase.Mode.PULL_FROM_START);
        view.setLabelName(getAty().getString(R.string.operateManifest));
        view.setLabelContent(mOperateManifest);

        view.setScanningType(CodeCallback.TAG_GOODS, CodeCallback.TAG_LPN);

        mDepot = DepotUtils.getDepot(getAty().getContext());

        mGoodsAdapter = new CommonAdapter<TakeDeliveryGoods>(getAty().getContext(), null, R.layout.item_goods) {
            @Override
            public void setItemContent(ViewHolder holder, TakeDeliveryGoods takeDeliveryGoods, int position) {
                holder.setLabelText(R.id.item_goods_in_depot_qty, String.valueOf(takeDeliveryGoods.getRquantity()))
                        .setLabelText(R.id.item_goods_name, takeDeliveryGoods.getSkuName())
                        .setLabelText(R.id.item_goods_sku, takeDeliveryGoods.getSkuCode())
                        .setLabelText(R.id.item_goods_unit, takeDeliveryGoods.getUnitName())
                        .setLabelText(R.id.item_goods_batch, takeDeliveryGoods.getBatchFlag());
            }

        };
        view.setAdapter(mGoodsAdapter);

        refresh();
    }

    @Override
    public void clickItem(int position) {
        TakeDeliveryGoods takeDeliveryGoods = mGoodsList.get(position - 1);
        toGoodsOperateActivity(takeDeliveryGoods);
    }

    @Override
    public void refresh() {
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_GOODS_LIST + mOperateManifest, null, mGoodsListCallback);
    }


    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        if (mGoodsList == null || mGoodsList.size() == 0) {
            return;
        }
        getView().displayProgressDialog(getAty().getString(R.string.matching));
        ArrayList<TakeDeliveryGoods> goodsBean = GoodsUtils.getManifestOfGoodsSame(mGoodsList, goods);
        if (goodsBean != null && goodsBean.size() > 0) {
            boolean isToOperate = false;
            for (TakeDeliveryGoods takeDeliveryGoods : goodsBean) {
                if (takeDeliveryGoods.getGoodsQty() > 0) {
                    toGoodsOperateActivity(takeDeliveryGoods);
                    isToOperate = true;
                    break;
                }
            }
            if (!isToOperate) {
                getView().displayMsgDialog(getAty().getString(R.string.DoneByGoods));
            }

        } else {
            getView().displayMsgDialog(getAty().getString(R.string.noGoosOfManifest));
        }
    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        if (isCanSubmitLpn(lpn)) {
            /*
            * 添加请求参数
             */
            if (mLpnSubmitCallBack == null) {
                mLpnSubmitCallBack = createLpnSubmitCallBack();
            }
            if (mTakeDeliveryModel == null) {
                mTakeDeliveryModel = new TakeDeliveryModel();
            }

            TakeDeliveryLpnSubmitParams lpnParams = new TakeDeliveryLpnSubmitParams();
            lpnParams.setLpnNo(lpn.getLpnNo());
            lpnParams.setWhCode(mDepot.getWhcode());
            lpnParams.setSkuList(mTakeDeliveryModel.getLpnOfGoodsListParams(lpn, mGoodsList));

            ModelService.post(ApiUrl.URL_TAKE_DELIVERY_LPN_SUBMIT, lpnParams, mLpnSubmitCallBack);
        }

    }

    /**
     * 创建Lpn回调方法
     *
     * @return
     */
    private ResultCallback createLpnSubmitCallBack() {
        ResultCallback lpnSubmitCallback = new ResultCallback() {
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
                ToastUtils.show(getAty().getContext(), getAty().getString(R.string.bosSubmitSuccess));
                refresh();
            }
        };
        return lpnSubmitCallback;
    }

    private boolean isCanSubmitLpn(CodeLpn lpn) {

        ArrayList<CodeGoods> a = lpn.getGoodsList();
        if (a == null || a.size() == 0) {
            getView().displayMsgDialog(getAty().getString(R.string.noHaveGoodsFromBoxOfManifest));
            return false;
        }
        /**
         * 需要判断当前 LPN 箱子所要装的东西是否z在当前任务单中货品列表中
         */
        GoodsUtils.B<CodeGoods> b = GoodsUtils.isLpnAtManifsetOfGoodsList(lpn.getGoodsList(), mGoodsList);
        if (!b.isAt) {
            String sku = b.notAtGoods.getGoodsSku();
            if (StringUtils.isEmpty(sku)) {
                sku = "";
            }
            String batchNo = b.notAtGoods.getGoodsBatchNo();
            if (StringUtils.isEmpty(batchNo)) {
                batchNo = "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("当前货品清单中不包含箱子中的货品")
                    .append("\r\n")
                    .append("不存在货品:")
                    .append("\r\n")
                    .append("\t sku:")
                    .append(sku)
                    .append("\r\n")
                    .append("\t 批次号:")
                    .append(batchNo);

            getView().displayMsgDialog(sb.toString());

            return false;
        }

        GoodsUtils.C<TakeDeliveryGoods, CodeGoods> c = GoodsUtils.isQuantitySatisfactory(lpn.getGoodsList(), mGoodsList);
        if (!c.isSatisfactory) {
            //当前数量不符合 货品
            //不符合货品
            //c.nowGoods;
            //c.planGoods;
            double nowHasQty = 0;
            double planQty = 0;
            for (TakeDeliveryGoods nowGood : c.nowGoods) {
                nowHasQty = NumberUtils.getDouble(nowHasQty + nowGood.getGoodsQty());
            }

            for (CodeGoods planGood : c.planGoods) {
                planQty = NumberUtils.getDouble(planQty + planGood.getGoodsQty());
            }
            TakeDeliveryGoods goods = c.nowGoods.get(0);
            StringBuilder sb = new StringBuilder();
            sb.append("当前货品清单中不包含箱子中的货品")
                    .append("\r\n")
                    .append("不存在货品:")
                    .append("\r\n")
                    .append("\t sku:")
                    .append(goods.getSkuCode())
                    .append("\r\n")
                    .append("\t 货品名称:")
                    .append(goods.getSkuName()).append("\r\n")
                    .append("\t 箱子收货数量:")
                    .append(planQty).append("\r\n")
                    .append("\t 现有数量:")
                    .append(nowHasQty)
            ;
            getView().displayMsgDialog(sb.toString());
            return false;
        }

        return true;
    }

    private void toGoodsOperateActivity(TakeDeliveryGoods takeDeliveryGoods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.OPERATE_GOODS, takeDeliveryGoods);
        bundle.putString(C.MANIFEST_STR, mOperateManifest);
        getAty().startActivity(InfoLoadUtils.getInstance().getEnterActivityLoad().getTakeDelGoodsOperateActivity(bundle), bundle);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    @Override
    public boolean isOpenStartRefreshing() {
        return true;
    }
}
