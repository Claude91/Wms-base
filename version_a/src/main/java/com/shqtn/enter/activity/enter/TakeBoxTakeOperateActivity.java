package com.shqtn.enter.activity.enter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
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
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;
import com.shqtn.enter.presenter.AbstractTakeBoxChild;
import com.shqtn.enter.presenter.TakeChildGoodsImpl;
import com.shqtn.enter.presenter.TakeChildLpnImpl;

import java.util.ArrayList;

public class TakeBoxTakeOperateActivity extends BaseActivity implements SystemEditText.OnToTextSearchListener, CodeController.View {
    public static final int SCANNING_BOX = 0X222;
    public static final int SCANNING_CHILDREN = 0X221;

    public static final String OPERATE_LEVEL = "2";

    private TitleView titleView;
    private LabelTextView ltvTakeBoxNo, ltvTakingQty, ltvTakeOverQty, ltvUntakeQty;
    private TextView tvSubmitF1, tvTakeOverF4;

    private ListView lv;
    private SystemEditText setInputCode;
    private TakeBoxPlan mOperateTakeBoxPlan;
    private TakeBoxGoods mOperateGoods;

    private AbstractTakeBoxChild takeBoxChildOperate;

    private CodeController.Presenter mCodePresenter;
    private ResultCallback mCheckBoxCallback;
    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeLpn(CodeLpn lpn) {
            super.decodeLpn(lpn);
            //检测当前扫描的是否是child
            if (SCANNING_CHILDREN == scanningType && CodeCallback.TAG_LPN == takeBoxChildOperate.getDecodeType()) {
                checkChildLpn(lpn);
            } else if (SCANNING_BOX == scanningType) {
                checkLpnBox(lpn);
            }
            cancelProgressDialog();
        }

        @Override
        public void decodeGoods(CodeGoods goods) {
            super.decodeGoods(goods);
            if (SCANNING_CHILDREN == scanningType && CodeCallback.TAG_GOODS == takeBoxChildOperate.getDecodeType()) {
                if (goods.getQuantity() <= 0) {
                    goods.setQuantity(1);
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
        if (mTakeBoxCheckingBoxStatusParams == null) {
            mTakeBoxCheckingBoxStatusParams = new TakeBoxCheckingBoxStatusParams();
        }
        mTakeBoxCheckingBoxStatusParams.setFlag(flag);
        mTakeBoxCheckingBoxStatusParams.setIkey(mOperateGoods.getIkey());
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

                        ArrayList childs = takeBoxChildOperate.getChilds();
                        childs.clear();
                        if (packSkuList != null) {

                            for (LpnStatus lpnStatus : packSkuList) {
                                if (!TextUtils.isEmpty(lpnStatus.getSkuCode())) {
                                    //当前是货品
                                    CodeGoods codeGoods = new CodeGoods();
                                    codeGoods.setBatchNo(lpnStatus.getBatchNo());
                                    codeGoods.setSkuCode(lpnStatus.getSkuCode());
                                    codeGoods.setQuantity(lpnStatus.getSkuQty());
                                    codeGoods.setTag(true);
                                    childs.add(codeGoods);
                                    continue;
                                }
                                CodeLpn codeLpn = new CodeLpn();
                                codeLpn.setLpnNo(lpnStatus.getPackCode());
                                codeLpn.setTag(true);
                                childs.add(codeLpn);

                            }
                        }
                        takeBoxChildOperate.setChilds(childs);
                        takeBoxChildOperate.getAdapter();
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
        setContentView(R.layout.activity_take_box_take_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateTakeBoxPlan = getBundle().getParcelable(C.TAKE_BOX_PLAN);
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);

        String packLevel = mOperateTakeBoxPlan.getPackLevel();
        if (OPERATE_LEVEL.equals(packLevel)) {
            takeBoxChildOperate = new TakeChildGoodsImpl(mOperateTakeBoxPlan, mOperateGoods, this);
        } else {
            takeBoxChildOperate = new TakeChildLpnImpl(mOperateTakeBoxPlan, mOperateGoods, this);
        }
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
        tvTakeOverF4 = (TextView) findViewById(R.id.activity_take_box_take_operate_tv_submit_over_f4);
        tvSubmitF1.setOnClickListener(this);
        tvTakeOverF4.setOnClickListener(this);

        lv = (ListView) findViewById(R.id.activity_take_box_take_operate_lv);
        setInputCode = (SystemEditText) findViewById(R.id.activity_take_box_take_operate_set_input_code);
        setInputCode.setOnToTextSearchListener(this);
    }


    @Override
    public boolean onKeyF4() {
        toOverSubmit();
        return true;
    }

    @Override
    public boolean onKeyF1() {
        toSubmit();
        return true;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        ltvTakingQty.setText(String.valueOf(takeBoxChildOperate.getTakeingQty()));
        ltvTakeOverQty.setText(String.valueOf(takeBoxChildOperate.getTakeingQty()));
        ltvUntakeQty.setText(String.valueOf(takeBoxChildOperate.getUntakeOverQty()));

        takeBoxChildOperate.setListView(lv);

        toScanningBoxNo();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_take_box_take_operate_tv_submit_f1) {
            toSubmit();
        } else if (i == R.id.activity_take_box_take_operate_tv_submit_over_f4) {
            toOverSubmit();
        } else if (i == R.id.activity_take_box_take_operate_ltv_take_box) {
            toScanningBoxNo();
        }
    }

    private void toScanningBoxNo() {
        scanningType = SCANNING_BOX;
        setInputCode.setHintText("请扫描箱子编码");
        mCodePresenter.setDecodeType(CodeCallback.TAG_LPN);
        ToastUtils.show(this, "请扫描外包装编号");
    }

    private void toScanningChildren() {
        scanningType = SCANNING_CHILDREN;
        setInputCode.setHintText(takeBoxChildOperate.getHintText());
        mCodePresenter.setDecodeType(takeBoxChildOperate.getDecodeType());
        ToastUtils.show(this, takeBoxChildOperate.getHintText());
    }

    private void toOverSubmit() {
        displayProgressDialog("提交中");
        TakeBoxSubmitParams overSubmit = takeBoxChildOperate.getOverSubmit();
        ModelService.post(ApiUrl.URL_TAKE_BOX_SUBMIT, overSubmit, new ResultCallback() {

            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                ToastUtils.show(TakeBoxTakeOperateActivity.this, "提交成功");
                finish();
            }
        });
    }


    private void toSubmit() {
        displayProgressDialog("提交中");
        TakeBoxSubmitParams overSubmit = takeBoxChildOperate.getSubmit();
        ModelService.post(ApiUrl.URL_TAKE_BOX_SUBMIT, overSubmit, new ResultCallback() {

            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                ToastUtils.show(TakeBoxTakeOperateActivity.this, "提交成功");
                finish();
            }
        });
    }


    @Override
    public void onSearchText(String content) {
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
