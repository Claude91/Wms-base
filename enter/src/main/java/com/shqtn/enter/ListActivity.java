package com.shqtn.enter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.controller.presenter.ActivityResultCallback;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;

/**
 * Created by android on 2017/9/21.
 */

public class ListActivity extends BaseActivity implements CodeController.View, ListActivityController.View, ListActivityController.BottomView {


    private PullToRefreshListView pullLv;
    private TitleView mTitleView;
    private SystemEditText setInputCode;
    private LabelTextView labelTextView;
    private Button btnClearSelect;

    private View bottomGroup;
    private TextView tvLeft;
    private TextView tvRight;

    /**
     * 用于处理解码操作
     */
    private CodeController.Presenter mCodePresenter;
    private CodeController.DecodeCallback mDecodeCallback;
    /**
     * 该接口实现了 解码的回调方法，用于接收解码后的结果
     */
    private ListActivityController.Presenter mListActivityPresenter;
    private Runnable action;


    public static <T extends ListActivityController.Presenter> Bundle createListActivityBundle(Class<T> p) {
        Bundle bundle = new Bundle();
        bundle.putString(C.PRESENTER, p.getCanonicalName());
        return bundle;
    }

    public String getListActivityPresenter() {
        Bundle bundle = getBundle();
        if (bundle == null) {
            return null;
        }
        return bundle.getString(C.PRESENTER);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_list);
    }

    @Override
    public void initData() {
        super.initData();

        String listActivityPresenter = getListActivityPresenter();
        try {
            mListActivityPresenter = (ListActivityController.Presenter) Class.forName(listActivityPresenter).newInstance();
            mListActivityPresenter.setAty(this);
            mListActivityPresenter.setView(this);
            mListActivityPresenter.setBottomView(this);

            //设置resultCallback 回调
            setActivityResultCallback(mListActivityPresenter);
            //设置监听键盘点击
            setOnKeyDownPresenter(mListActivityPresenter);
            //设置解码的回调
            mDecodeCallback = mListActivityPresenter;


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mCodePresenter = new CodePresenterImpl(this);
        mCodePresenter.setDecodeCallback(mDecodeCallback);
    }

    @Override
    public void bindView() {
        super.bindView();
        pullLv = (PullToRefreshListView) findViewById(R.id.activity_list_pulllv);
        mTitleView = (TitleView) findViewById(R.id.activity_list_title);
        setInputCode = (SystemEditText) findViewById(R.id.activity_list_set_input_code);
        labelTextView = (LabelTextView) findViewById(R.id.activity_list_label);
        btnClearSelect = (Button) findViewById(R.id.activity_list_btn_clear_select);
        bottomGroup = findViewById(R.id.activity_list_bottom_group);
        tvLeft = (TextView) findViewById(R.id.activity_list_bottom_tv_right);
        tvRight = (TextView) findViewById(R.id.activity_list_bottom_tv_right);

    }

    @Override
    public void initWidget() {
        super.initWidget();
        mTitleView.setOnToBackClickListener(this);
        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                mCodePresenter.toDecode(content);
            }
        });

        pullLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListActivityPresenter.clickItem(i);
            }
        });
        if (mListActivityPresenter.isOpenStartRefreshing()) {
            pullLv.setRefreshing();
        }
        pullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListActivityPresenter.onPullDownToRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListActivityPresenter.onPullUpToRefresh();
            }
        });

        btnClearSelect.setOnClickListener(this);

        mListActivityPresenter.init(getBundle());
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_list_btn_clear_select) {
            mListActivityPresenter.clickClearSelect();

        }
    }

    @Override
    public void clickBack() {
        mListActivityPresenter.clickToBack();
    }

    @Override
    public void setTitle(String title) {
        mTitleView.setTitle(title);
    }

    @Override
    public void setEditTextHint(String hint) {
        setInputCode.setHintText(hint);
    }


    @Override
    public void onBackPressed() {
        clickBack();
    }


    @Override
    public void setAdapter(CommonAdapter adapter) {
        pullLv.setAdapter(adapter);
    }

    @Override
    public void setListViewModel(PullToRefreshBase.Mode mode) {
        pullLv.setMode(mode);
    }

    @Override
    public void setLabelName(String labelName) {
        labelTextView.setLabelText(labelName);
    }

    @Override
    public void setLabelContent(String content) {
        labelTextView.setText(content);
    }

    @Override
    public void displayLabel() {
        if (labelTextView.getVisibility() != View.VISIBLE) {
            labelTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLabel() {
        if (labelTextView.getVisibility() != View.GONE)
            labelTextView.setVisibility(View.GONE);
    }


    @Override
    public void onRefreshComplete() {
        if (action == null) {
            action = new Runnable() {
                @Override
                public void run() {
                    pullLv.onRefreshComplete();
                }
            };
        }
        pullLv.postDelayed(action, 1000);
    }

    @Override
    public void setRefreshing() {
        pullLv.setRefreshing();
    }

    @Override
    public void displayBtnClear() {
        if (btnClearSelect.getVisibility() != View.VISIBLE)
            btnClearSelect.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnClear() {
        if (btnClearSelect.getVisibility() != View.GONE)
            btnClearSelect.setVisibility(View.GONE);
    }

    @Override
    public void setScanningType(@CodeCallback.ScanningTag int... tag) {
        mCodePresenter.setDecodeType(tag);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pullLv.setRefreshing();
        mListActivityPresenter.refresh();
    }

    @Override
    public void displayBottomGroup() {
        if (bottomGroup.getVisibility() != View.VISIBLE) {
            bottomGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideBottomGroup() {
        if (bottomGroup.getVisibility() != View.GONE) {
            bottomGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void setRightText(String text) {
        tvRight.setText(text);
    }

    @Override
    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    @Override
    public void hideRightText() {
        tvRight.setVisibility(View.GONE);
    }

    @Override
    public void hideLeftText() {
        tvLeft.setVisibility(View.GONE);
    }

    @Override
    public void setRightTextOnClickListener(View.OnClickListener l) {
        tvRight.setOnClickListener(l);
    }

    @Override
    public void setLeftTextOnClickListener(View.OnClickListener l) {
        tvLeft.setOnClickListener(l);
    }


}
