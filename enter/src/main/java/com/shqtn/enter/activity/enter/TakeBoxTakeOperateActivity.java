package com.shqtn.enter.activity.enter;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.R;

public class TakeBoxTakeOperateActivity extends BaseActivity implements SystemEditText.OnToTextSearchListener {
    private TitleView titleView;
    private LabelTextView ltvTakeBoxNo,ltvTakeMaxQty,ltvTakeOverQty;
    private TextView tvSubmitF1, tvTakeOverF4;

    private ListView lv;
    private SystemEditText setInputCode;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_take_box_take_operate);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void bindView() {
        super.bindView();
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setOnToBackClickListener(this);

        ltvTakeBoxNo = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_take_box);
        ltvTakeMaxQty = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_max_qty);
        ltvTakeOverQty = (LabelTextView) findViewById(R.id.activity_take_box_take_operate_ltv_over_qty);
        tvSubmitF1 = (TextView) findViewById(R.id.activity_take_box_take_operate_tv_submit_f1);
        tvTakeOverF4 = (TextView) findViewById(R.id.activity_take_box_take_operate_tv_submit_f4);
        tvSubmitF1.setOnClickListener(this);
        tvTakeOverF4.setOnClickListener(this);

        lv = (ListView) findViewById(R.id.activity_take_box_take_operate_lv);
        setInputCode = (SystemEditText) findViewById(R.id.activity_take_box_take_operate_set_input_code);
        setInputCode.setOnToTextSearchListener(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);

    }

    @Override
    public void onSearchText(String content) {

    }
}
