package com.shqtn.enter.presenter.in;

import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.params.GoodsAdjustLpnSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.ActivityUtils;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.enter.controller.impl.LpnSubmitPresenterImpl;

/**
 * Created by android on 2017/9/29.
 */

public class GoodsAdjustLpnSubmitPresenter extends LpnSubmitPresenterImpl {

    private CodeLpn mOperateLpn;
    private String mSrcRackNo;
    private String mTargetRackNo;

    @Override
    public void init() {
        mOperateLpn = (CodeLpn) getOperateLpnBean();
        mSrcRackNo = getBundle().getString(C.RACK_NO);

        getView().setLpnNo(mOperateLpn.getLpnNo());
        getView().setRackCodeLabel("目标货位");
        getView().setTitle("货位调整托盘提交");

        getView().setEditTextHint("目标货位");
        getView().setDecodeType(CodeCallback.TAG_RACK);

    }

    @Override
    public void decodeRack(CodeRack rack) {
        super.decodeRack(rack);
        mTargetRackNo = rack.getRackNo();
        getView().setRackCode(mTargetRackNo);
    }

    @Override
    public void submit() {
        GoodsAdjustLpnSubmitParams submitParams = new GoodsAdjustLpnSubmitParams();
        submitParams.setLpnNo(mOperateLpn.getLpnNo());
        submitParams.setSourceLocCode(mSrcRackNo);
        submitParams.setTargetLocCode(mTargetRackNo);
        submitParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        ModelService.post(ApiUrl.URL_GOODS_ADJUST_LPN_SUBMIT, submitParams, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                getView().cancelProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                getView().displayMsgDialog("msg");
            }

            @Override
            public void onSuccess(ResultBean response) {
                ToastUtils.show(ActivityUtils.getInstance().getTopAty(),"提交成功");
                getAty().closeActivity();
            }
        });

    }
}
