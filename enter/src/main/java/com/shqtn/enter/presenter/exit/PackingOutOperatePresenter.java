package com.shqtn.enter.presenter.exit;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.exit.PackingOutLpn;
import com.shqtn.base.bean.params.PackingOutManifestParams;
import com.shqtn.base.bean.params.PackingOutManifestSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.PackingStatusUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.AbstractListActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2017/10/12.
 */

public class PackingOutOperatePresenter extends AbstractListActivityPresenter {
    private static final int TYPE_ADD = 0X333;
    private List<PackingOutLpn> mLpnList;//当前任务单中的货品
    private PackingOutManifestParams mManifestParams;
    private ResultCallback mManifestDetailsCallback;
    private CommonAdapter<PackingOutLpn> mLpnAdapter;

    private String mOperateManifest;//当前操作的任务单据号

    @Override
    public void init() {
        super.init();

        mLpnAdapter = new CommonAdapter<PackingOutLpn>(getAty().getContext(), null, R.layout.item_packing) {
            @Override
            public void setItemContent(ViewHolder holder, PackingOutLpn packingOutLpn, int position) {
                LabelTextView ltvLpnNO = holder.getViewById(R.id.item_packing_ltv_lpn_no);
                TextView tvStatus = holder.getViewById(R.id.item_packing_tv_status);
                int color;
                if (packingOutLpn.getType() == TYPE_ADD) {
                    holder.getConvertView().setBackgroundColor(Color.YELLOW);
                    color = ContextCompat.getColor(getAty().getContext(), R.color.colorBlack);
                } else {
                    color = ContextCompat.getColor(getAty().getContext(), R.color.text_white_color);
                    holder.getConvertView().setBackgroundColor(ContextCompat.getColor(getAty().getContext(), R.color.colorBlue));
                }

                ltvLpnNO.setTextColorAll(color);
                tvStatus.setTextColor(color);

                String status = PackingStatusUtils.getStatus(packingOutLpn.getPackStatus());
                ltvLpnNO.setText(packingOutLpn.getBoxCode());
                tvStatus.setText(status);
            }
        };

        ListActivityController.View view = getView();
        view.setTitle("包装出库");

        view.setEditTextHint("请输入任务单号");
        view.setScanningType(CodeCallback.TAG_LPN, CodeCallback.TAG_MANIFEST);

        view.setListViewModel(PullToRefreshBase.Mode.DISABLED);
        getBottomView().hideLeftText();
        getBottomView().setRightText(getAty().getString(R.string.submitF1));
        getBottomView().setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSubmit();
            }


        });
    }

    private void toSubmit() {
        if (isCanSubmit()) {
            PackingOutManifestSubmitParams submitParams = new PackingOutManifestSubmitParams();
            submitParams.setPackageNo(mOperateManifest);
            submitParams.setIkey(mLpnList.get(0).getIkey());

            ModelService.post(ApiUrl.URL_DOB_SUBMIT, submitParams, new ResultCallback() {
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
                    getView().setLabelContent("");
                    mOperateManifest = null;
                    mLpnList.clear();
                    mLpnAdapter.update(mLpnList);
                    changeTopLabel();
                    changeBottomGroup();
                    getView().displayMsgDialog("提交成功");
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_F1:
                toSubmit();
                return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void changeTopLabel() {
        if (StringUtils.isEmpty(mOperateManifest)) {
            getView().hideLabel();
        } else {
            getView().displayLabel();
            getView().setLabelContent(mOperateManifest);
        }
    }

    private void changeBottomGroup() {
        if (StringUtils.isEmpty(mOperateManifest)) {
            getBottomView().hideBottomGroup();
        } else {
            getBottomView().displayBottomGroup();
        }
    }

    @Override
    public void decodeLpn(CodeLpn lpn) {
        super.decodeLpn(lpn);
        if (isCanAddLpn(lpn)) {
            for (PackingOutLpn packingOutLpn : mLpnList) {
                if (packingOutLpn.getBoxCode().equals(lpn.getLpnNo())) {
                    packingOutLpn.setType(TYPE_ADD);
                    mLpnAdapter.update(mLpnList);
                    return;
                }
            }
        }
    }

    public boolean isCanAddLpn(CodeLpn lpn) {
        if (StringUtils.isEmpty(mOperateManifest)) {
            getView().displayMsgDialog("请扫描任务单号");
            return false;
        }
        if (mLpnList == null || mLpnList.size() == 0) {
            getView().displayMsgDialog("当前任务单号无数据，不能添加箱子");
            return false;
        }

        for (PackingOutLpn packingOutLpn : mLpnList) {
            if (packingOutLpn.getBoxCode().equals(lpn.getLpnNo())) {
                if (packingOutLpn.getType() == TYPE_ADD) {
                    getView().displayMsgDialog("箱子不允许重复添加");
                    return false;
                }

                return true;
            }
        }
        getView().displayMsgDialog("查询的箱子:" + lpn.getLpnNo() + "不在当前任务单中");
        return false;
    }

    @Override
    public void decodeManifest(CodeManifest manifest) {
        super.decodeManifest(manifest);
        getView().displayProgressDialog("查询任务单号中内容");
        queryManifestDetail(mOperateManifest);
    }

    private void queryManifestDetail(String manifest) {
        if (mManifestParams == null) {
            mManifestParams = new PackingOutManifestParams();
        }

        mManifestParams.setPackageNo(manifest);
        if (mManifestDetailsCallback == null) {
            mManifestDetailsCallback = createManifestDetailsCallback();
        }
        ModelService.post(ApiUrl.URL_DOB_GET_LPN_LIST, mManifestParams, mManifestDetailsCallback);
    }

    private ResultCallback createManifestDetailsCallback() {
        return new ResultCallback() {

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

                ArrayList<PackingOutLpn> rows = getRows(response.getRows(), PackingOutLpn.class);
                if (rows == null || rows.size() == 0) {
                    getView().displayMsgDialog("当前无数据");
                    return;
                }
                getView().displayLabel();
                getView().setLabelName("操作任务单号");
                getView().setLabelContent(mManifestParams.getPackageNo());
                mOperateManifest = mManifestParams.getPackageNo();
                changeBottomGroup();
                mLpnList = rows;
                mLpnAdapter.update(mLpnList);
            }
        };
    }

    public boolean isCanSubmit() {
        if (StringUtils.isEmpty(mOperateManifest)) {
            return false;
        }
        if (mLpnList == null || mLpnList.size() == 0) {
            getView().displayMsgDialog("当前任务单号无数据,不能进行操作");
            return false;
        }

        for (PackingOutLpn packingOutLpn : mLpnList) {
            if (packingOutLpn.getType() != TYPE_ADD) {
                getView().displayMsgDialog("还有箱子没有添加箱子: " + packingOutLpn.getBoxCode());
                return false;
            }
        }

        return true;
    }


    @Override
    public void clickItem(int position) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean isOpenStartRefreshing() {
        return false;
    }
}
