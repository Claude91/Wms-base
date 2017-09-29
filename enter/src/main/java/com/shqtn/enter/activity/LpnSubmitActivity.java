package com.shqtn.enter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.LpnSubmitController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;
import com.shqtn.enter.controller.impl.LpnSubmitPresenterImpl;

public class LpnSubmitActivity extends BaseActivity implements SystemEditText.OnToTextSearchListener, CodeController.View, LpnSubmitController.View {

    public static final String PRESENTER = "lpn_submit_presenter";

    private TitleView titleView;
    private SystemEditText systemEditText;
    private LabelTextView ltvRack;
    private LabelTextView ltvLpn;
    private ListView lvGoods;
    private TextView tvSubmit;


    private CodeController.Presenter mCodePresenter;
    private LpnSubmitController.AbstractPresenter mLpnOperatePresenter;


    public static <T extends LpnSubmitController.AbstractPresenter> Bundle createPresenter(Class<T> presenter) {
        Bundle bundle = new Bundle();
        bundle.putString(PRESENTER, presenter.getCanonicalName());
        return bundle;
    }


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_lpn_submit);
    }


    @Override
    public void initData() {
        super.initData();
        mLpnOperatePresenter = getLpnOperatePresenter();
        if (mLpnOperatePresenter == null) {
            mLpnOperatePresenter = new LpnSubmitPresenterImpl(){

                @Override
                public void init() {

                }
            };
        }
        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeType(CodeCallback.TAG_RACK);
        mCodePresenter.setDecodeCallback(mLpnOperatePresenter);
    }

    private LpnSubmitController.AbstractPresenter getLpnOperatePresenter() {
        Bundle bundle = getBundle();
        String presenter = bundle.getString(PRESENTER);
        try {
            return (LpnSubmitController.AbstractPresenter) Class.forName(presenter).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        systemEditText = (SystemEditText) findViewById(R.id.activity_lpn_submit_set_input_code);
        ltvRack = (LabelTextView) findViewById(R.id.activity_lpn_submit_ltv_rack);
        ltvLpn = (LabelTextView) findViewById(R.id.activity_lpn_submit_ltv_lpn_no);
        lvGoods = (ListView) findViewById(R.id.activity_lpn_submit_lv_goods);
        tvSubmit = (TextView) findViewById(R.id.activity_lpn_submit_tv_submit);

        titleView.setOnToBackClickListener(this);
        systemEditText.setOnToTextSearchListener(this);

        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLpnOperatePresenter.setAty(this);
        mLpnOperatePresenter.init(getBundle());
        mLpnOperatePresenter.setView(this);
    }

    @Override
    public void clickBack() {
        mLpnOperatePresenter.clickToBack();
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
        if (i == R.id.activity_lpn_submit_tv_submit) {
            mLpnOperatePresenter.submit();
        }
    }

    @Override
    public void setTitle(String title) {
        titleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        systemEditText.setHintText(hint);
    }

    @Override
    public void setLpnNo(String lpnNo) {
        ltvLpn.setText(lpnNo);
    }

    @Override
    public void setRackCode(String poscode) {
        ltvRack.setText(poscode);
    }

    @Override
    public void setRackCodeLabel(String label) {
        ltvRack.setLabelText(label);
    }

    @Override
    public void setLpnNoLabel(String label) {
        ltvLpn.setLabelText(label);
    }

    @Override
    public String getRackCode() {
        return ltvRack.getText();
    }

    @Override
    public void setListViewAdapter(CommonAdapter adapter) {
        lvGoods.setAdapter(adapter);
    }

    @Override
    public void setDecodeType(int... types) {
        mCodePresenter.setDecodeType(types);
    }
}
