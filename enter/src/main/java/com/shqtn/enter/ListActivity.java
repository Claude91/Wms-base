package com.shqtn.enter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;

/**
 * Created by android on 2017/9/21.
 */

public class ListActivity extends BaseActivity implements CodeController.View, ListActivityController.View {


    private PullToRefreshListView pullLv;
    private TitleView mTitleView;
    private SystemEditText setInputCode;
    private LabelTextView labelTextView;


    private CodeController.Presenter mCodePresenter;
    private CodeController.DecodeCallback mDecodeCallback;
    private ListActivityController.Presenter mListActivityPresenter;
    private Runnable action;


    public static <T extends ListActivityController.Presenter, D extends CodeController.DecodeCallback> Bundle createListActivityBundle(Class<T> p, Class<D> decodeImpl) {
        Bundle bundle = new Bundle();
        bundle.putString(C.PRESENTER, p.getCanonicalName());
        bundle.putString(C.DECODE_CALLBACK, decodeImpl.getCanonicalName());
        return bundle;
    }

    public String getListActivityPresenter() {
        Bundle bundle = getBundle();
        if (bundle == null) {
            return null;
        }
        return bundle.getString(C.PRESENTER);
    }

    public String getDecodeCallbackName() {
        Bundle bundle = getBundle();
        if (bundle == null) {
            return null;
        }
        return bundle.getString(C.DECODE_CALLBACK);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_list);
    }

    @Override
    public void initData() {
        super.initData();

        String decodeCallbackName = getDecodeCallbackName();
        String listActivityPresenter = getListActivityPresenter();
        try {
            mListActivityPresenter = (ListActivityController.Presenter) Class.forName(listActivityPresenter).newInstance();
            mListActivityPresenter.setAty(this);
            mListActivityPresenter.setView(this);

            if (!StringUtils.isEmpty(decodeCallbackName)) {
                if (decodeCallbackName.equals(listActivityPresenter) && mListActivityPresenter instanceof CodeController.DecodeCallback) {
                    mDecodeCallback = (CodeController.DecodeCallback) mListActivityPresenter;
                } else {
                    mDecodeCallback = (CodeController.DecodeCallback) Class.forName(decodeCallbackName).newInstance();
                }
            }


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

        mListActivityPresenter.init(getBundle());
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
        labelTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLabel() {
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
    public void setScanningType(int... tag) {
        mCodePresenter.setDecodeType(tag);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pullLv.setRefreshing();
        mListActivityPresenter.refresh();
    }
}
