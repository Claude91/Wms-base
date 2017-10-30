package com.shqtn.enter.controller.impl.lpn;

import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.RackUpGoods;
import com.shqtn.base.bean.params.RackUpLpnSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.LpnSubmitController;
import com.shqtn.enter.controller.impl.AbstractLpnSubmitPresenterImpl;

import java.util.ArrayList;

/**
 * Created by android on 2017/9/28.
 */

public class RackUpLpnSubmitPresenter extends AbstractLpnSubmitPresenterImpl {

    private RackUpGoods mOperateLpn;
    AskMsgDialog.OnAskClickListener listener = new AskMsgDialog.OnAskClickListener() {
        @Override
        public void clickTrue() {
            getView().displayProgressDialog("提交中");
            uploadSubmit();

            getView().cancelAskMsgDialog();
        }


        @Override
        public void clickCancel() {
            getView().cancelAskMsgDialog();
        }
    };


    @Override
    public void setView(LpnSubmitController.View view) {
        super.setView(view);

        mOperateLpn = (RackUpGoods) getOperateLpnBean();
        view.setRackCode(mOperateLpn.getPoscode());
        view.setLpnNo(mOperateLpn.getPalletno());
        view.setDecodeType(CodeCallback.TAG_RACK);
    }

    @Override
    public void decodeRack(CodeRack rack) {
        super.decodeRack(rack);
        getView().setRackCode(rack.getRackNo());
    }

    @Override
    public void submit() {
        StringBuffer sb = new StringBuffer();
        sb.append("托盘号:")
                .append(mOperateLpn.getPalletno())
                .append("\r\n")
                .append("推荐货位:")
                .append(mOperateLpn.getPoscode())
                .append("\r\n")
                .append("上架货位:")
                .append(getView().getRackCode())
                .append("\r\n")
                .append("请确定信息，是否进行提交?");

        getView().displayAskMsgDialog(sb.toString(), listener);
    }

    @Override
    public void init() {

    }

    @Override
    public void clickToBack() {
        getAty().closeActivity();
    }

    private void uploadSubmit() {
        RackUpLpnSubmitParams submitParams = new RackUpLpnSubmitParams();
        submitParams.setWhCode(DepotUtils.getDepot(getAty().getContext()).getWhcode());
        submitParams.setLpnNo(mOperateLpn.getPalletno());
        ArrayList<RackUpLpnSubmitParams.Pis> list = new ArrayList<>();
        RackUpLpnSubmitParams.Pis pis = new RackUpLpnSubmitParams.Pis();

        pis.setPoscode(getView().getRackCode());//目标货位
        pis.setCcode(mOperateLpn.getCcode());
        pis.setRdcode(mOperateLpn.getRdcode());

        list.add(pis);
        submitParams.setPisList(list);
        ModelService.post(ApiUrl.URL_RACK_UP_SUBMIT, submitParams, new ResultCallback() {
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
                ToastUtils.show(getAty().getContext(), getAty().getString(R.string.submitSuccessHint));
                getAty().closeActivity();
            }
        });
    }

}
