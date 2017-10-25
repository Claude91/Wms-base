package com.shqtn.enter.presenter.in.pallet;

import android.os.Bundle;
import android.sax.StartElementListener;

import com.shqtn.base.C;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.in.PalletLpn;
import com.shqtn.base.bean.params.PalletHaveCodeInLpnDetailsParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

/**
 * Created by android on 2017/10/25.
 * 托盘管理，有号入托
 *
 * @author ql
 */

public class PalletManagerHaveCodeInLpnPresenter extends AbstractListActivityPresenter {
    private PalletHaveCodeInLpnDetailsParams mLpnDetailsParams = new PalletHaveCodeInLpnDetailsParams();
    private ResultCallback mDetailsCallback;

    @Override
    public void init() {
        super.init();

        ListActivityController.View view = getView();
        view.setTitle("托盘管理");
        view.setEditTextHint("请输入托盘");

        view.setScanningType(CodeCallback.TAG_LPN);

        view.onRefreshComplete();
    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        if (mDetailsCallback == null) {
            mDetailsCallback = new ResultCallback() {
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
                    PalletLpn lpn = getData(response.getData(), PalletLpn.class);
                    toPalletAddLpnOrGoods(lpn.getPalletNo());
                }
            };
        }
        mLpnDetailsParams.setPalletNo(lpn.getLpnNo());
        ModelService.post(ApiUrl.URL_PALLET_QUERY_CODE, mLpnDetailsParams, mDetailsCallback);

    }

    private void toPalletAddLpnOrGoods(String palletNo) {
        Bundle bundle = new Bundle();
        bundle.putString(C.OPERATE_LPN, palletNo);
        Class palletManagerHaveCodeInLpnAddGoodsOrLpnActivity = InfoLoadUtils.getInstance().getInActivityLoad().getPalletManagerHaveCodeInLpnAddGoodsOrLpnActivity(bundle);
        getAty().startActivity(palletManagerHaveCodeInLpnAddGoodsOrLpnActivity, bundle);
    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public void refresh() {
        getView().onRefreshComplete();

    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }
}
