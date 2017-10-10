package com.shqtn.enter.presenter.exit;

import android.os.Bundle;

import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.RackDownGoods;
import com.shqtn.base.bean.params.RackDownLpnSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.enter.controller.impl.AbstractLpnSubmitPresenterImpl;

import java.util.ArrayList;

/**
 * Created by android on 2017/10/10.
 */

public class RackDownLpnSubmitPresenter extends AbstractLpnSubmitPresenterImpl {
    private String mOperateManifestNo;
    private CodeLpn mOperateLpn;
    private String mOperateRack;//操作的货位
    private ArrayList<RackDownGoods> mGoodsList;
    private RackDownGoods rackDownGoods;

    /**
     * bundle.putParcelableArrayList(C.GOODS_LIST, mGoodsList);
     * bundle.putString(C.OPERATE_RACK_NO, mOperateRack);
     * bundle.putString(C.MANIFEST_STR, mOperateManifest);
     * bundle.putParcelable(C.OPERATE_LPN,lpn);
     */
    @Override
    public void init() {
        Bundle bundle = getBundle();
        mOperateManifestNo = bundle.getString(C.MANIFEST_STR);
        mOperateLpn = bundle.getParcelable(C.OPERATE_LPN);
        mOperateRack = bundle.getString(C.OPERATE_RACK_NO);
        mGoodsList = bundle.getParcelableArrayList(C.GOODS_LIST);
        /**
         * 之前已经判断是否可以提交该箱子，在该箱子中获得一个货品，用于展示目标货位，
         * <t>老鮑 现在普及版不用，目前先搁置问题. 询问无人解决</t>
         */
        ArrayList<CodeGoods> goodsList = mOperateLpn.getGoodsList();
        CodeGoods codeGoods = goodsList.get(0);
        ArrayList<RackDownGoods> manifestOfGoodsSame = GoodsUtils.getManifestOfGoodsSame(mGoodsList, codeGoods);
        rackDownGoods = manifestOfGoodsSame.get(0);
    }

    @Override
    public void submit() {
        String rackCode = getView().getRackCode();
        if (StringUtils.isEmpty(rackCode)) {
            getView().displayMsgDialog("请添加货位");
            return;
        }
        String targetLocCode = rackDownGoods.getTargetLocCode();
        if (!rackCode.equals(targetLocCode)) {
            StringBuffer sb = new StringBuffer();
            sb.append("推荐货位:").append(targetLocCode).append("\r\n")
                    .append("目标货位:").append(rackCode).append("\r\n")
                    .append("确认提交吗?");
            getView().displayAskMsgDialog(sb.toString(), new AskMsgDialog.OnAskClickListener() {
                @Override
                public void clickTrue() {
                    toSubmit();
                }

                @Override
                public void clickCancel() {

                }
            });
        } else {
            toSubmit();
        }

    }

    private void toSubmit() {
        getView().displayProgressDialog("提交中");
        String rackCode = getView().getRackCode();
        RackDownLpnSubmitParams submitParams = new RackDownLpnSubmitParams();
        submitParams.setLpnNo(mOperateLpn.getLpnNo());
        submitParams.setDocNo(mOperateManifestNo);
        submitParams.setPickIkey(rackDownGoods.getPickingIkey());
        submitParams.setSourceLocCode(mOperateRack);
        submitParams.setTargetLocCode(rackCode);
        submitParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        ModelService.post(ApiUrl.URL_RACK_DOWN_LPN_SUBMIT, submitParams, new ResultCallback() {
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
                ToastUtils.show(getAty().getContext(), "提交成功");
                getAty().closeActivity();
            }
        });
    }
}
