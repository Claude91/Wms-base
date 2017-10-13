package com.shqtn.enter.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shqtn.base.BaseActivity;
import com.shqtn.base.CommonAdapter;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.UserClientBean;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.LoginUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.utils.UserClientUtils;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.enter.LoginActivity;
import com.shqtn.enter.R;

import java.util.ArrayList;
import java.util.List;

public class DepotChangeAty extends BaseActivity {

    private SystemEditText setInputCode;
    private PullToRefreshListView lv;
    private TitleView titleView;

    private CommonAdapter<DepotBean> mDepotAdapter;
    private List<DepotBean> mDepotList;
    private List<DepotBean> mDepotSelectList = new ArrayList<>();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_depot_change_aty);
    }

    @Override
    public void initData() {
        super.initData();
        mDepotAdapter = new CommonAdapter<DepotBean>(this, null, R.layout.item_depot_list) {
            @Override
            public void setItemContent(ViewHolder holder, DepotBean depotBean, int position) {
                holder.setText(R.id.item_depot_tv_id, depotBean.getWhcode())
                        .setText(R.id.item_depot_tv_name, depotBean.getWhname());
            }
        };
    }

    @Override
    public void bindView() {
        super.bindView();
        setInputCode = (SystemEditText) findViewById(R.id.depot_change_set_input_code);
        titleView = (TitleView) findViewById(R.id.titleView);
        lv = (PullToRefreshListView) findViewById(R.id.depot_change_listView);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lv.setAdapter(mDepotAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectDepot(mDepotList.get(i));
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        lv.setRefreshing();
        titleView.setOnToBackClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DepotBean depotBean = mDepotSelectList.get(i - 1);
                DepotUtils.saveDepot(DepotChangeAty.this, depotBean);
                finish();
            }
        });

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                mDepotSelectList.clear();
                for (DepotBean depotBean : mDepotList) {
                    if (depotBean.getWhcode().contains(content)) {
                        mDepotSelectList.add(depotBean);
                        continue;
                    }

                    if (depotBean.getWhname().contains(content)) {
                        mDepotSelectList.add(depotBean);
                        continue;
                    }
                }
                mDepotAdapter.update(mDepotSelectList);
            }
        });

        loadData();
    }

    private void loadData() {
        ModelService.post(ApiUrl.URL_DEPOT, null, new ResultCallback() {
            @Override
            public void onAfter() {
                super.onAfter();
                cancelProgressDialog();
                lv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lv.onRefreshComplete();
                    }
                }, 1_500);
            }

            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                ArrayList<DepotBean> list = getRows(response.getData(), DepotBean.class);
                mDepotSelectList.clear();
                mDepotList = list;
                if (mDepotList != null && mDepotList.size() != 0) {
                    mDepotSelectList.addAll(mDepotList);
                } else {
                    displayMsgDialog("无仓库，请通知管理员");
                }
                mDepotAdapter.update(mDepotSelectList);
            }
        });
    }

    @Override
    public void clickBack() {
        closeActivity();
    }


    private void selectDepot(DepotBean depotBean) {
        DepotUtils.saveDepot(this, depotBean);
    }

    @Override
    public void closeActivity() {
        DepotBean depot = DepotUtils.getDepot(this);
        if (depot == null) {
            displayAskMsgDialog("必须选择仓库，可继续操作，否则退出，确认-退出登录，取消-选择仓库", new AskMsgDialog.OnAskClickListener() {

                @Override
                public void clickTrue() {
                    ModelService.post(ApiUrl.URL_LOGOUT, null, new ResultCallback() {
                        @Override
                        public void onFailed(String msg) {

                        }

                        @Override
                        public void onSuccess(ResultBean response) {

                        }
                    });
                    UserClientUtils.clearLoginUser(DepotChangeAty.this);
                    LoginUtils.clearAll(DepotChangeAty.this);
                    setResult(BaseActivity.RESULT_CODE_CLOSE);
                    startActivity(LoginActivity.class);
                    finish();
                }

                @Override
                public void clickCancel() {
                    cancelAskMsgDialog();
                }
            });
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_F2) {
            //清空输入内容
            setInputCode.setText("");
            mDepotSelectList.clear();
            if (mDepotList != null)
                mDepotSelectList.addAll(mDepotList);
            mDepotAdapter.update(mDepotSelectList);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
