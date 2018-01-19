package com.shqtn.b.enter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.b.BaseBActivity;
import com.shqtn.b.R;
import com.shqtn.b.SerialAddActivity;
import com.shqtn.b.enter.ViewInfo;
import com.shqtn.b.enter.params.BRackUpGoodsSubmitParams;
import com.shqtn.b.enter.result.BRackUpGoods;
import com.shqtn.base.C;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.SerialNoVo;
import com.shqtn.base.bean.params.RackUpGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.model.RackUpGoodsOperateModel;
import com.shqtn.base.model.impl.RackUpGoodsOperateModelImpl;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.GoodsUtils;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.EditQtyController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.EditQtyPresenterImpl;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

public class BRackUpGoodsOperateActivity extends BaseBActivity implements SystemEditText.OnToTextSearchListener
        , EditQtyController.View, CodeController.View, CodeController.DecodeCallback {

    public static final int REQUEST_ADD_SERAIL = 33;

    private TitleView titleView;
    private SystemEditText setInputCode;
    private LabelTextView ltvRecommendRack;
    private LabelTextView ltvTargetRack;
    private LabelTextView ltvGoodsName;
    private LabelTextView ltvGoodsSku;
    private LabelTextView ltvBatchNo;
    private LabelTextView ltvStd;
    private LabelTextView ltvUnit;
    private LabelTextView ltvPlanQty;
    private LabelTextView ltvCanRackQty;
    private LabelTextView ltvOperateQty;

    private LabelTextView ltvOperateManifest;

    private LinearLayout bottomGrouop;

    private TextView tvSubmit;

    private BRackUpGoods mOperateGoods;

    private EditQtyController.Presenter mEditQtyPresenter; //编辑数量的操作类
    private CodeController.Presenter mCodePresenter;//解码操作类
    private RackUpGoodsOperateModel mRackUpGoodsOperateModel;

    private ArrayList<String> mSrcSerialList;//原始序列号集合
    private ArrayList<String> mAddSerialList;//已经添加的序列号集合;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_brack_up_goods_operate);
    }

    @Override
    public void initData() {
        super.initData();
        mOperateGoods = getBundle().getParcelable(C.OPERATE_GOODS);

        mRackUpGoodsOperateModel = new RackUpGoodsOperateModelImpl();

        mEditQtyPresenter = new EditQtyPresenterImpl(mRackUpGoodsOperateModel.getCanUpQuantity(mOperateGoods), this);

        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeCallback(this);
        mCodePresenter.setDecodeType(CodeCallback.TAG_GOODS, CodeCallback.TAG_RACK);

    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        setInputCode = (SystemEditText) findViewById(R.id.activity_brack_up_goods_operate_set_input_code);
        ltvTargetRack = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_ltv_target_rack);
        ltvGoodsName = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_goods_ltv_name);
        ltvGoodsSku = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_goods_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_goods_ltv_batch_no);
        ltvStd = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_goods_ltv_std);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_goods_ltv_unit);
        ltvPlanQty = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_goods_ltv_plan_qty);
        ltvCanRackQty = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_ltv_can_up_qty);
        ltvOperateQty = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_ltv_operate_qty);
        ltvRecommendRack = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_ltv_recommend_rack);
        tvSubmit = (TextView) findViewById(R.id.activity_brack_up_goods_operate_tv_submit);
        ltvOperateManifest = (LabelTextView) findViewById(R.id.activity_brack_up_goods_operate_ltv_manifest);
        bottomGrouop = (LinearLayout) findViewById(R.id.activity_brack_up_goods_operate_bottom_group);
        ltvOperateQty.setOnClickListener(this);

        titleView.setOnToBackClickListener(this);
        setInputCode.setOnToTextSearchListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        ltvRecommendRack.setText(mOperateGoods.getPoscode());
        ltvTargetRack.setText(mOperateGoods.getPoscode());
        ltvPlanQty.setText(String.valueOf(mOperateGoods.getPquantity()));

        double canUpQty = mRackUpGoodsOperateModel.getCanUpQuantity(mOperateGoods);
        ltvCanRackQty.setText(String.valueOf(canUpQty));

        ltvGoodsName.setText(mOperateGoods.getSkuName());
        ltvGoodsSku.setText(mOperateGoods.getInvcode());
        ltvBatchNo.setText(mOperateGoods.getBatchno());
        ltvStd.setText(mOperateGoods.getStd());
        ltvUnit.setText(mOperateGoods.getUnitName());
        ltvOperateManifest.setText(mOperateGoods.getCcode());
        if (isAddSerial()) {
            ArrayList<SerialNoVo> serialList = mOperateGoods.getSerialList();
            if (serialList != null) {
                mSrcSerialList = new ArrayList<>();
                for (SerialNoVo serialNoVo : serialList) {
                    mSrcSerialList.add(serialNoVo.getSerialNo());
                }
            }

            View addSerialButton = ViewInfo.createAddSerialButton(this);
            bottomGrouop.addView(addSerialButton);
        }


    }

    /**
     * 用于判断是否是序列号管理
     *
     * @return
     */
    private boolean isAddSerial() {
        ArrayList<SerialNoVo> serialList = mOperateGoods.getSerialList();
        return serialList != null && serialList.size() > 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_SERAIL && resultCode == Activity.RESULT_OK) {
            ArrayList<String> serialList = SerialAddActivity.getSerialList(data);
            mAddSerialList = serialList;
            changeAddCount();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 通知 修改当前页面数量；
     */
    private void changeAddCount() {
        double count;
        if (mAddSerialList != null) {

            count = mAddSerialList.size();
        } else {
            count = 0;
        }

        ltvOperateQty.setText(String.valueOf(count));

    }


    @Override
    public boolean onKeyF1() {
        submitGoods();
        return true;
    }

    boolean submiting;

    private void submitGoods() {
        if (submiting) {
            return;
        }

        String operateQtyText = ltvOperateQty.getText();
        double operateQty = NumberUtils.getDouble(operateQtyText);
        String targetRack = ltvTargetRack.getText();
        if (!mRackUpGoodsOperateModel.isCanSubmit(operateQty, targetRack, mOperateGoods)) {
            String failedHint = mRackUpGoodsOperateModel.getFailedHint();
            displayMsgDialog(failedHint);
            return;
        }

        submiting = true;


        displayProgressDialog("提交中");

        BRackUpGoodsSubmitParams params = new BRackUpGoodsSubmitParams();
        DepotBean depot = DepotUtils.getDepot(this);
        params.setWhCode(depot.getWhcode());
        List<RackUpGoodsSubmitParams.Pis> list = new ArrayList<>();
        RackUpGoodsSubmitParams.Pis pis = new RackUpGoodsSubmitParams.Pis();
        pis.setIkey(mOperateGoods.getIkey());
        pis.setIquantity(operateQty);
        pis.setPoscode(targetRack);
        pis.setCcode(mOperateGoods.getCcode());
        pis.setRdcode(mOperateGoods.getRdcode());

        if (isAddSerial()) {
            ArrayList<SerialNoVo> l = new ArrayList<>();
            if (mAddSerialList != null) {
                for (String s : mAddSerialList) {

                    SerialNoVo vo = new SerialNoVo(s);
                    l.add(vo);
                }
            }
            pis.setSerialList(l);
        }
        list.add(pis);
        params.setPisList(list);


        ModelService.post(ApiUrl.URL_RACK_UP_SUBMIT, params, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                submiting = false;
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                cancelProgressDialog();
                submiting = false;
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                ToastUtils.show(BRackUpGoodsOperateActivity.this, "提交成功");
                finish();
            }
        });

    }

    @Override
    public boolean onKeyF2() {
        toEditQty();
        return true;
    }

    private void toEditQty() {
        if (isAddSerial()) {
            displayMsgDialog("当前序列号管理，请添加序列号");
            return;
        }
        mEditQtyPresenter.editQty();
    }

    @Override
    public void onSearchText(String content) {
        if (!StringUtils.isEmpty(content)) {
            mCodePresenter.toDecode(content);
        }
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_brack_up_goods_operate_ltv_operate_qty) {

            toEditQty();
        } else if (i == R.id.activity_brack_up_goods_operate_tv_submit) {
            submitGoods();
        } else if (i == R.id.btn_serial) {
            Bundle bundle = new Bundle();
            SerialAddActivity.put(mSrcSerialList, mAddSerialList, mSrcSerialList.size(), bundle);
            startActivity(SerialAddActivity.class, bundle, REQUEST_ADD_SERAIL);
        } else if (i == R.id.activity_brack_up_goods_operate_tv_submit) {
            submitGoods();
        }
    }

    @Override
    public void setEditQty(double qty) {
        ltvOperateQty.setText(String.valueOf(qty));
    }

    @Override
    public void setTitle(String title) {
        titleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        setInputCode.setHintText(hint);
    }


    @Override
    public void decodeLpn(CodeLpn lpn) {

    }

    @Override
    public void decodeRack(CodeRack rack) {
        ltvTargetRack.setText(rack.getRackNo());
    }

    @Override
    public void decodeGoods(CodeGoods goods) {

        if (!mRackUpGoodsOperateModel.isSame(mOperateGoods, goods)) {
            displayMsgDialog("添加货品与当前操作货品不匹配，不能添加货品");
            return;
        }
        if (isAddSerial()) {
            String serialNo = goods.getSerialNo();
            if (StringUtils.isEmpty(serialNo)) {
                displayMsgDialog("序列号管控，解码后该货品未包含序列号不可添加");
                return;
            }
            if (!isCanAddSerial()) {
                displayMsgDialog("超出限制不能添加");
                return;
            }
            addSerial(serialNo);
            changeAddCount();
            return;
        }
        String operateQtyText = ltvOperateQty.getText();
        double operateQty = NumberUtils.getDouble(operateQtyText);

        if (!mRackUpGoodsOperateModel.isAddQty(mOperateGoods, goods, operateQty)) {
            displayMsgDialog("超出限制不能添加");
            return;
        }

        double inputQty = GoodsUtils.getGoodsQty(goods);
        double inputTotalQty = NumberUtils.getDouble(operateQty + inputQty);
        ltvOperateQty.setText(String.valueOf(inputTotalQty));
    }

    private void addSerial(String serialNo) {
        if (mAddSerialList == null) {
            mAddSerialList = new ArrayList<>();
        }
        mAddSerialList.add(serialNo);
    }


    @Override
    public void decodeManifest(CodeManifest manifest) {

    }

    @Override
    public void decodeOther(AllotBean code) {
        displayMsgDialog("扫描编码不正确，请从新扫描");
    }

    public boolean isCanAddSerial() {
        int totalCount = 0;
        if (mSrcSerialList != null) {
            totalCount = mSrcSerialList.size();
        }
        int nowCount = 0;
        if (mAddSerialList != null) {
            nowCount = mAddSerialList.size();
        }

        return totalCount > nowCount;
    }
}
