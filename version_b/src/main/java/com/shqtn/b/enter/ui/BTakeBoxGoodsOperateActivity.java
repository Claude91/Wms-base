package com.shqtn.b.enter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.b.SerialAddActivity;
import com.shqtn.b.enter.AbstractBTakeBoxChild;
import com.shqtn.b.enter.BTakeBoxChildGoodsImpl;
import com.shqtn.b.enter.BTakeBoxChildLpnImpl;
import com.shqtn.b.enter.ViewInfo;
import com.shqtn.b.enter.result.BTakeBoxManifest;
import com.shqtn.base.C;
import com.shqtn.base.bean.LpnCheck;
import com.shqtn.base.bean.LpnStatus;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.TakeBoxGoods;
import com.shqtn.base.bean.enter.TakeBoxPlan;
import com.shqtn.base.bean.params.TakeBoxCheckingBoxStatusParams;
import com.shqtn.base.bean.params.TakeBoxSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;

import java.util.ArrayList;

public class BTakeBoxGoodsOperateActivity extends BaseBActivity implements SystemEditText.OnToTextSearchListener, CodeController.View {
    public static final int SCANNING_BOX = 0X222;
    public static final int SCANNING_CHILDREN = 0X221;

    public static final String OPERATE_LEVEL = "2";
    private static final int REQUEST_ADD_SERIAL = 3;//添加序列号

    private TitleView titleView;
    private LabelTextView ltvTakeBoxNo, ltvTakingQty, ltvTakeOverQty, ltvUntakeQty;
    private TextView tvSubmitF1, tvTakeOverF3;
    private ViewGroup bottomBtnGroup;

    private ViewGroup boxNoGroup;

    private ListView lv;
    private SystemEditText setInputCode;
    private TakeBoxPlan mOperateTakeBoxPlan;
    private TakeBoxGoods mOperateGoods;

    private AbstractBTakeBoxChild takeBoxChildOperate;

    private CodeController.Presenter mCodePresenter;
    private ResultCallback mCheckBoxCallback;

    private BTakeBoxManifest mOperateManifestBean;

    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeLpn(CodeLpn lpn) {
            super.decodeLpn(lpn);
            cancelProgressDialog();
            //检测当前扫描的是否是child
            if (SCANNING_CHILDREN == scanningType && CodeCallback.TAG_LPN == takeBoxChildOperate.getDecodeType()) {
                if (!takeBoxChildOperate.isCanAdd(lpn)) {
                    displayMsgDialog(String.format("箱子:%s不能重复添加", lpn.getLpnNo()));
                    return;
                }
                checkChildLpn(lpn);
            } else if (SCANNING_BOX == scanningType) {
                checkLpnBox(lpn);
            }

        }

        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);
            if (SCANNING_CHILDREN == scanningType && CodeCallback.TAG_GOODS == takeBoxChildOperate.getDecodeType()) {
                if (goods.getQuantity() <= 0) {
                    goods.setQuantity(1);
                }
                if (!takeBoxChildOperate.isCanAdd(goods)) {
                    return;
                }
                takeBoxChildOperate.addChildren(goods);
                takeBoxChildOperate.getAdapter().notifyDataSetChanged();
                cancelProgressDialog();
            }
        }

        @Override
        public void decodeOther(AllotBean code) {
            super.decodeOther(code);
            cancelProgressDialog();
            displayMsgDialog("类型不匹配，请重新扫描");
        }
    };
    private TakeBoxCheckingBoxStatusParams mTakeBoxCheckingBoxStatusParams;

    private ArrayList<String> mAddSerials;

    private boolean isTakeChildGoods;
    private String mOperateManifest;

    private void checkChildLpn(CodeLpn lpn) {
        checkBox(lpn.getLpnNo(), TakeBoxCheckingBoxStatusParams.FLAG_CHILD, getChildLevel());
    }

    private void checkLpnBox(CodeLpn lpn) {
        checkBox(lpn.getLpnNo(), TakeBoxCheckingBoxStatusParams.FLAG_BOX, mOperateTakeBoxPlan.getPackLevel());
    }

    /**
     * 用于检测箱子状态
     *
     * @param lpnNo 箱号
     * @param flag  当前箱子是 被包装，是外层包装形式 TakeBoxCheckingBoxStatusParams.FLAG_CHILD,FLAG_BOX;
     * @param level 当前检测箱子的等级
     */

    private void checkBox(String lpnNo, String flag, String level) {
        displayProgressDialog("检测箱子状态");
        if (mTakeBoxCheckingBoxStatusParams == null) {
            mTakeBoxCheckingBoxStatusParams = new TakeBoxCheckingBoxStatusParams();
        }
        mTakeBoxCheckingBoxStatusParams.setFlag(flag);
        mTakeBoxCheckingBoxStatusParams.setIkey(mOperateManifestBean.getIkey());
        mTakeBoxCheckingBoxStatusParams.setLpnNo(lpnNo);
        mTakeBoxCheckingBoxStatusParams.setPackLevel(level);
        displayProgressDialog("检测箱子状态 ");
        if (mCheckBoxCallback == null) {
            mCheckBoxCallback = new ResultCallback() {
                @Override
                public void onFailed(String msg) {
                    cancelProgressDialog();
                    displayMsgDialog(msg);
                    String inputBox = ltvTakeBoxNo.getText();
                    if (!TextUtils.isEmpty(inputBox)) {
                        toScanningChildren();
                    }

                }

                @Override
                public void onSuccess(ResultBean response) {
                    LpnCheck data = getData(response.getData(), LpnCheck.class);
                    ArrayList<LpnStatus> packSkuList = data.getPackSkuList();

                    if (TakeBoxCheckingBoxStatusParams.FLAG_BOX.equals(mTakeBoxCheckingBoxStatusParams.getFlag())) {
                        /*
                         * 当前检测的是 外包装；
                         * 外包装检测，获得已经包装的信息
                         */
                        String boxNo = mTakeBoxCheckingBoxStatusParams.getLpnNo();
                        takeBoxChildOperate.setBoxNo(boxNo);
                        ltvTakeBoxNo.setText(boxNo);
                        takeBoxChildOperate.addOverChild(data);

                        toScanningChildren();
                    } else if (TakeBoxCheckingBoxStatusParams.FLAG_CHILD.equals(mTakeBoxCheckingBoxStatusParams.getFlag())) {
                        //检测 被包装
                        if (packSkuList != null && packSkuList.size() > 0) {
                            LpnStatus lpnStatus = packSkuList.get(0);
                            String packCode = lpnStatus.getPackCode();
                            CodeLpn codeLpn = new CodeLpn();
                            codeLpn.setLpnNo(packCode);
                            codeLpn.setPackStatus(lpnStatus.getPackStatus());
                            if (takeBoxChildOperate.isCanAdd(codeLpn)) {
                                takeBoxChildOperate.addChildren(codeLpn);
                                takeBoxChildOperate.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                    cancelProgressDialog();

                }
            };
        }
        ModelService.post(ApiUrl.URL_TAKE_BOX_CHECK_LPN_NO, mTakeBoxCheckingBoxStatusParams, mCheckBoxCallback);
    }

    private int scanningType = SCANNING_CHILDREN;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_btake_box_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        mOperateTakeBoxPlan = bundle.getParcelable(C.TAKE_BOX_PLAN);
        mOperateGoods = bundle.getParcelable(C.OPERATE_GOODS);
        mOperateManifest = bundle.getString(C.MANIFEST_STR);

        mOperateManifestBean = bundle.getParcelable(C.MANIFEST_BEAN);

        String packLevel = mOperateTakeBoxPlan.getPackLevel();
        isTakeChildGoods = OPERATE_LEVEL.equals(packLevel);
        if (isTakeChildGoods) {
            takeBoxChildOperate = new BTakeBoxChildGoodsImpl(mOperateTakeBoxPlan, mOperateGoods, this);
        } else {
            takeBoxChildOperate = new BTakeBoxChildLpnImpl(mOperateTakeBoxPlan, mOperateGoods, this);
        }

        takeBoxChildOperate.setManifest(mOperateManifest);
        takeBoxChildOperate.setOpearetManifest(mOperateManifestBean);
        mCodePresenter = new CodePresenterImpl(this);


        mCodePresenter.setDecodeCallback(mDecodeCallback);
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        ltvTakeBoxNo = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_take_box);
        ltvTakeBoxNo.setClickable(true);
        ltvTakeBoxNo.setOnClickListener(this);
        ltvTakingQty = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_taking_qty);
        ltvTakeOverQty = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_over_qty);
        ltvUntakeQty = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_untake_qty);
        tvSubmitF1 = (TextView) findViewById(R.id.activity_take_box_take_operate_tv_submit_f1);
        tvTakeOverF3 = (TextView) findViewById(R.id.activity_take_box_take_operate_tv_submit_over_f3);
        tvSubmitF1.setOnClickListener(this);
        tvTakeOverF3.setOnClickListener(this);

        boxNoGroup = (ViewGroup) findViewById(R.id.activity_take_box_take_operate_group);
        bottomBtnGroup = (ViewGroup) findViewById(R.id.activity_take_box_take_operate_btn_group);

        lv = (ListView) findViewById(R.id.activity_take_box_take_operate_lv);
        setInputCode = (SystemEditText) findViewById(R.id.activity_take_box_take_operate_set_input_code);
        setInputCode.setOnToTextSearchListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        ltvTakingQty.setText(String.valueOf(takeBoxChildOperate.getTakeingQty()));
        ltvTakeOverQty.setText(String.valueOf(takeBoxChildOperate.getTakeOverQty()));
        ltvUntakeQty.setText(String.valueOf(takeBoxChildOperate.getUntakeOverQty()));

        takeBoxChildOperate.setListView(lv);

        if (takeBoxChildOperate.isAddSerialButton()) {
            View addSerialButton = ViewInfo.createAddSerialButton(this);
            bottomBtnGroup.addView(addSerialButton);
        }
        toScanningBoxNo();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_take_box_take_operate_tv_submit_f1) {
            toSubmit();
        } else if (i == R.id.activity_take_box_take_operate_tv_submit_over_f3) {
            toOverSubmit();
        } else if (i == R.id.activity_take_box_take_operate_ltv_take_box) {
            toScanningBoxNo();
        } else if (i == R.id.btn_serial) {
            Bundle b = new Bundle();
            SerialAddActivity.put(null, takeBoxChildOperate.getAddSerial(), takeBoxChildOperate.getAddSerialSize(), b);
            startActivity(SerialAddActivity.class, b, REQUEST_ADD_SERIAL);
        }
    }

    private void toScanningBoxNo() {
        scanningType = SCANNING_BOX;
        setInputCode.setHintText("请扫描箱子编码");
        mCodePresenter.setDecodeType(CodeCallback.TAG_LPN);
        boxNoGroup.setSelected(true);
        lv.setSelected(false);
        ToastUtils.show(this, "请扫描外包装编号");
    }

    private void toScanningChildren() {
        scanningType = SCANNING_CHILDREN;
        setInputCode.setHintText(takeBoxChildOperate.getHintText());
        mCodePresenter.setDecodeType(takeBoxChildOperate.getDecodeType());
        boxNoGroup.setSelected(false);
        lv.setSelected(true);
        ToastUtils.show(this, takeBoxChildOperate.getHintText());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_ADD_SERIAL:
                mAddSerials = SerialAddActivity.getSerialList(data);
                takeBoxChildOperate.setChildsSerials(mAddSerials);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public boolean onKeyF3() {
        toOverSubmit();
        return true;
    }

    @Override
    public boolean onKeyF4() {
        return true;
    }

    @Override
    public boolean onKeyF1() {
        toSubmit();
        return true;
    }

    private void toOverSubmit() {

        TakeBoxSubmitParams overSubmit = takeBoxChildOperate.getOverSubmit();
        if (overSubmit == null) {
            return;
        }
        displayProgressDialog("提交中");
        ModelService.post(ApiUrl.URL_TAKE_BOX_SUBMIT, overSubmit, new ResultCallback() {

            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                toast("提交成功");
                finish();
            }
        });
    }


    private void toSubmit() {
        TakeBoxSubmitParams overSubmit = takeBoxChildOperate.getSubmit();
        if (overSubmit == null) {
            return;
        }
        displayProgressDialog("提交中");

        ModelService.post(ApiUrl.URL_TAKE_BOX_SUBMIT, overSubmit, new ResultCallback() {

            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                toast("提交成功");
                finish();
            }
        });
    }


    @Override
    public void onSearchText(String content) {

        if (StringUtils.isEmpty(content)) {
            return;
        }
        displayProgressDialog("解码中");
        mCodePresenter.toDecode(content);
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setEditTextHint(String hint) {

    }

    @Override
    public boolean onKeyF2() {
        toScanningBoxNo();
        return true;
    }

    public String getChildLevel() {
        String packLevel = mOperateTakeBoxPlan.getPackLevel();
        int i = Integer.parseInt(packLevel);
        String childLevel = String.valueOf(i - 1);
        return childLevel;
    }

}
