package com.shqtn.enter.activity.in;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.params.GoodsAdjustGoodsSubmitParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.ListActivity;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.DecodeCallbackImpl;
import com.shqtn.enter.presenter.in.GoodsAdjustAddMoveGoodsPresenter;
import com.shqtn.enter.presenter.in.GoodsAdjustRackPresenter;

import java.util.ArrayList;

public class GoodsAdjustTargetRackActivity extends BaseActivity {

    private TitleView titleView;
    private LabelTextView ltvTargetRackNo;
    private LabelTextView ltvSrcRackNo;
    private ListView lvMoveGoods;
    SystemEditText setInputCode;

    private ArrayList<GoodsAdjustAddMoveGoodsPresenter.ItemGoods> mMoveGoodsList;
    private CommonAdapter<GoodsAdjustAddMoveGoodsPresenter.ItemGoods> mGoodsAdapter;
    private String mSrcRackNo;
    private String mTargetRackNo;

    private CodeController.Presenter mCodePresenter;
    private CodeController.DecodeCallback mDecodeCallback = new DecodeCallbackImpl() {
        @Override
        public void decodeRack(CodeRack rack) {
            super.decodeRack(rack);
            mTargetRackNo = rack.getRackNo();
            ltvTargetRackNo.setText(rack.getRackNo());
        }
    };

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_goods_adjust_target_rack);
    }

    @Override
    public void initData() {
        super.initData();

        mMoveGoodsList = getBundle().getParcelableArrayList(C.GOODS_LIST);
        mSrcRackNo = getBundle().getString(C.RACK_NO);

    }

    @Override
    public void bindView() {
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);
        titleView.setOnRightTextClickListener(new TitleView.OnRightTextClickListener() {
            @Override
            public void onOnClick() {
                submit();
            }
        });

        ltvSrcRackNo = (LabelTextView) findViewById(R.id.activity_goods_adjust_target_rack_ltv_src_rack_no);
        ltvTargetRackNo = (LabelTextView) findViewById(R.id.activity_goods_adjust_target_rack_ltv_target_rack_no);

        lvMoveGoods = (ListView) findViewById(R.id.activity_goods_adjust_target_rack_move_goods_list);
        setInputCode = (SystemEditText) findViewById(R.id.activity_goods_adjust_target_rack_set_input_code);

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {

            }
        });
    }

    private void submit() {
        if (isCanSubmit()) {
            displayProgressDialog("提交中");
            GoodsAdjustGoodsSubmitParams submitParams = new GoodsAdjustGoodsSubmitParams();
            ArrayList<GoodsAdjustGoodsSubmitParams.SubmitMovePro> l = new ArrayList<>();

            for (GoodsAdjustAddMoveGoodsPresenter.ItemGoods itemGoods : mMoveGoodsList) {
                GoodsAdjustGoodsSubmitParams.SubmitMovePro goods = new GoodsAdjustGoodsSubmitParams.SubmitMovePro();
                goods.setSkuCode(itemGoods.getSkuCode());
                goods.setWhCode(DepotUtils.getDepot(this).getWhcode());
                goods.setAdjLocPos(mTargetRackNo);
                goods.setAdjQty(itemGoods.getAdjQty());
                goods.setBatchNo(itemGoods.getBatchNo());
                goods.setLocCode(mSrcRackNo);
                goods.setOwnerId(itemGoods.getOwnerId());
                goods.setSkuIkey(itemGoods.getSkuIkey());
                l.add(goods);
            }
            submitParams.setStockposSearchList(l);
            ModelService.post(ApiUrl.URL_GOODS_ADJUST_GOODS_SUBMIT, submitParams, new ResultCallback() {
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
                    cancelProgressDialog();
                    Bundle bundle = new Bundle();
                    Bundle listActivityBundle = ListActivity.createListActivityBundle(GoodsAdjustRackPresenter.class);
                    bundle.putAll(listActivityBundle);
                    startActivity(ListActivity.class, bundle);
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            });
        }
    }

    public boolean isCanSubmit() {

        if (StringUtils.isEmpty(mTargetRackNo)) {
            displayMsgDialog("请添加移动到的货位");
            return false;
        }
        return true;
    }


    @Override
    public boolean onKeyF1() {
        submit();
        return true;

    }


}
