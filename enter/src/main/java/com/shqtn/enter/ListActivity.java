package com.shqtn.enter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.ListActivityController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;

/**
 * Created by android on 2017/9/21.
 */

public class ListActivity extends BaseActivity implements CodeController.View, ListActivityController.View {
    public final static String CONTROLLER = "controller";

    public final static String PRESENTER = "presenter";
    public final static String DECODE_CALLBACK = "decodeCallback";

    private PullToRefreshListView pullLv;
    private TitleView mTitleView;
    private SystemEditText setInputCode;
    private CodeController.Presenter mCodePresenter;
    private CodeController.DecodeCallback mDecodeCallback;
    private ListActivityController.Presenter mListActivityPresenter;
    private Runnable action;


    public static <T extends ListActivityController.Presenter, D extends CodeController.DecodeCallback> Bundle createListActivityBundle(Class<T> p, Class<D> decodeImpl) {
        Bundle bundle = new Bundle();
        bundle.putString(PRESENTER, p.getCanonicalName());
        bundle.putString(DECODE_CALLBACK, decodeImpl.getCanonicalName());
        return bundle;
    }

    public String getListActivityPresenter() {
        Bundle bundle = getBundle();
        return bundle.getString(PRESENTER);
    }

    public String getDecodeCallbackName() {
        Bundle bundle = getBundle();
        return bundle.getString(DECODE_CALLBACK);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_list);
    }

    @Override
    public void initData() {
        super.initData();

        String decodeCallbackName = getDecodeCallbackName();
        try {
            if (!StringUtils.isEmpty(decodeCallbackName))
                mDecodeCallback = (CodeController.DecodeCallback) Class.forName(decodeCallbackName).newInstance();

            mListActivityPresenter = (ListActivityController.Presenter) Class.forName(getListActivityPresenter()).newInstance();
            mListActivityPresenter.setAty(this);
            mListActivityPresenter.setView(this);
            mListActivityPresenter.setBundle(getBundle());

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
        pullLv.setRefreshing();
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

        mListActivityPresenter.init();
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
        pullLv.setRefreshing();
        mListActivityPresenter.refresh();
    }
}
