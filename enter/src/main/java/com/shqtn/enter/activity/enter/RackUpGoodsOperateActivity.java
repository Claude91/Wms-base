package com.shqtn.enter.activity.enter;

import android.view.View;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.enter.RackUpGoods;
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
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.EditQtyController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.EditQtyPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 上架货品操作
 */
public class RackUpGoodsOperateActivity extends BaseActivity implements SystemEditText.OnToTextSearchListener
        , EditQtyController.View, CodeController.View, CodeController.DecodeCallback {

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

    private TextView tvSubmit;

    private RackUpGoods mOperateGoods;

    private EditQtyController.Presenter mEditQtyPresenter; //编辑数量的操作类
    private CodeController.Presenter mCodePresenter;//解码操作类
    private RackUpGoodsOperateModel mRackUpGoodsOperateModel;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_rack_up_goods_operate);
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
        setInputCode = (SystemEditText) findViewById(R.id.activity_rack_up_goods_operate_set_input_code);
        ltvTargetRack = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_target_rack);
        ltvGoodsName = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_name);
        ltvGoodsSku = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_sku);
        ltvBatchNo = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_batch_no);
        ltvStd = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_std);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_unit);
        ltvPlanQty = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_goods_ltv_plan_qty);
        ltvCanRackQty = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_can_up_qty);
        ltvOperateQty = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_operate_qty);
        ltvRecommendRack = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_recommend_rack);
        tvSubmit = (TextView) findViewById(R.id.activity_rack_up_goods_operate_tv_submit);
        ltvOperateManifest = (LabelTextView) findViewById(R.id.activity_rack_up_goods_operate_ltv_manifest);

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

    }

    @Override
    public void widgetForbid(View v) {
        int i = v.getId();
        if (i == R.id.activity_rack_up_goods_operate_ltv_operate_qty) {
            toEditQty();
        } else if (i == R.id.activity_rack_up_goods_operate_tv_submit) {
            submitGoods();
        }

    }

    @Override
    public boolean onKeyF1() {
        submitGoods();
        return true;
    }

    private void submitGoods() {

        String operateQtyText = ltvOperateQty.getText();
        double operateQty = NumberUtils.getDouble(operateQtyText);
        String targetRack = ltvTargetRack.getText();
        if (!mRackUpGoodsOperateModel.isCanSubmit(operateQty, targetRack, mOperateGoods)) {
            String failedHint = mRackUpGoodsOperateModel.getFailedHint();
            displayMsgDialog(failedHint);
            return;
        }

        displayProgressDialog("提交中");

        RackUpGoodsSubmitParams params = new RackUpGoodsSubmitParams();
        DepotBean depot = DepotUtils.getDepot(this);
        params.setWhCode(depot.getWhcode());
        List<RackUpGoodsSubmitParams.Pis> list = new ArrayList<>();
        RackUpGoodsSubmitParams.Pis pis = new RackUpGoodsSubmitParams.Pis();
        pis.setIkey(mOperateGoods.getIkey());
        pis.setIquantity(operateQty);
        pis.setPoscode(targetRack);
        list.add(pis);
        params.setPisList(list);
        ModelService.post(ApiUrl.URL_RACK_UP_SUBMIT, params, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                ToastUtils.show(RackUpGoodsOperateActivity.this, "提交成功");
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
        if (i == R.id.activity_rack_up_goods_operate_tv_submit) {
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

    @Override
    public void decodeManifest(CodeManifest manifest) {

    }

    @Override
    public void decodeOther(AllotBean code) {

    }
}
