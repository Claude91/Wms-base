package com.shqtn.enter.presenter;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeDeliveryGoods;
import com.shqtn.base.bean.params.TakeDeliveryGoodsSubmitParams;
import com.shqtn.base.controller.view.IAty;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.model.TakeDeliveryModel;
import com.shqtn.base.utils.ActivityUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.controller.enter.TakeDelGoodsOperateController;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

/**
 * Created by android on 2017/9/26.
 */

public class TakeDeliveryGoodsOperatePresenter extends DecodeCallbackImpl implements TakeDelGoodsOperateController.Presenter {

    private TakeDelGoodsOperateController.View mView;
    private IAty mAty;
    private String mOperateManifest;

    private TakeDeliveryGoods mOperateGoods;
    private TakeDeliveryModel mTakeDeliveryModel;
    private EditQuantityDialog.OnResultListener mEditQtyDialogResultListener;

    public TakeDeliveryGoodsOperatePresenter(TakeDelGoodsOperateController.View mView, IAty mAty) {
        this.mView = mView;
        this.mAty = mAty;
    }

    @Override
    public void init(Bundle bundle) {
        mOperateGoods = bundle.getParcelable(C.OPERATE_GOODS);

        mOperateManifest = bundle.getString(C.MANIFEST_STR);

        mTakeDeliveryModel = new TakeDeliveryModel();

        double aDouble = bundle.getDouble(C.SCANNING_GOODS_QTY);
        if (aDouble <= 0) {
            mView.setTakeQty(String.valueOf(aDouble));
        } else {
            mView.setTakeQty(String.valueOf(0));
        }

        mView.setOperateManifest(mOperateManifest);

        mView.setGoodsName(mOperateGoods.getSkuName());
        String qty = String.valueOf(NumberUtils.getDouble(mOperateGoods.getRquantity()));
        mView.setPlanQty(qty);

        mView.setUnit(mOperateGoods.getUnitName());

        mView.setDecodeType(CodeCallback.TAG_GOODS);

        if (mTakeDeliveryModel.isAddBatchNo(mOperateGoods.getBatchFlag(), mOperateGoods.getBatchNoFlag())) {
            mView.setInputBatchNoHint("请输入批次号");
            mView.setIsInputBatchNo(true);
        } else {
            mView.setIsInputBatchNo(false);
            mView.setInputBatchNoHint("不需要添加批次号");
        }
    }

    @Override
    public void submit() {

        TakeDeliveryGoodsSubmitParams submitParams = new TakeDeliveryGoodsSubmitParams();
        double qty = NumberUtils.getDouble(mView.getTakeQty());

        if (qty <= 0) {
            mView.displayMsgDialog("请添加货品");
            return;
        }

        submitParams.setAccQuantity(qty);
        submitParams.setTs(mOperateGoods.getTs());
        submitParams.setSkuCode(mOperateGoods.getSkuCode());
        submitParams.setSkuName(mOperateGoods.getSkuName());
        submitParams.setIkey(mOperateGoods.getIkey());
        submitParams.setIhkey(mOperateGoods.getIhkey());
        if (mTakeDeliveryModel.isAddBatchNo(mOperateGoods.getBatchFlag(), mOperateGoods.getSerialNoFlag())) {
            submitParams.setBatchNo(mView.getEtInputBatchNo());
        }
        mView.displayProgressDialog("提交中");
        ModelService.post(ApiUrl.URL_TAKE_DELIVERY_GOODS_SUBMIT, submitParams, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                mView.cancelProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                mView.displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                if (response.isRs()) {
                    ToastUtils.show(ActivityUtils.getInstance().getTopAty(), "提交成功");
                    mAty.closeActivity();
                } else {
                    mView.displayMsgDialog(response.getMessage());
                }
            }

        });
    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        if (GoodsUtils.isSame(mOperateGoods, goods)) {
            String takeQtyStr = mView.getTakeQty();
            double takeQty = NumberUtils.getDouble(takeQtyStr);
            double goodsQty = goods.getGoodsQty();
            double totalQty = NumberUtils.getDouble(takeQty + goodsQty);
            mView.setTakeQty(String.valueOf(totalQty));
        } else {
            mView.displayMsgDialog("输入货品与当前操作货品不匹配");
        }
    }

    @Override
    public void clickToEditQty() {
        if (mEditQtyDialogResultListener == null) {
            mEditQtyDialogResultListener = createResultListener();
        }
        mView.displayEditQty(mEditQtyDialogResultListener);
    }

    private EditQuantityDialog.OnResultListener createResultListener() {
        return new EditQuantityDialog.OnResultListener() {
            @Override
            public void clickOk(double quantity) {
                mView.setTakeQty(String.valueOf(quantity));
            }

            @Override
            public void clickCancel() {
                mView.cancelEditQty();
            }
        };
    }


    @Override
    public void clickToBack() {
        mAty.closeActivity();
    }
}
