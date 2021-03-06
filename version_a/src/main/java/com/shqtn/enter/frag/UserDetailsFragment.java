package com.shqtn.enter.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shqtn.base.BaseFragment;
import com.shqtn.base.bean.DepotBean;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.UserClientBean;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.DepotUtils;
import com.shqtn.base.utils.LoginUtils;
import com.shqtn.base.utils.UserClientUtils;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.EventBusFactory;
import com.shqtn.enter.InfoLoadUtils;
import com.shqtn.enter.LoginActivity;
import com.shqtn.enter.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by android on 2017/9/21.
 */

public class UserDetailsFragment extends BaseFragment {

    private TitleView titleView;
    private TextView tvUserName;
    private TextView tvWorkId;
    private TextView tvDepot;
    private Button btnQuit;

    private DepotBean mDepot;
    private UserClientBean mUserClientBean;

    public static UserDetailsFragment newInstance() {
        Bundle args = new Bundle();

        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCreateViewId() {
        return R.layout.frag_user_details;
    }

    @Override
    public void initData() {
        super.initData();
        EventBusFactory.getDepotEventBus().register(this);
        mDepot = DepotUtils.getDepot(getContext());
        mUserClientBean = UserClientUtils.getLoginUser(getContext());
    }

    @Override
    public void bindView(View view) {
        super.bindView(view);
        titleView = view.findViewById(R.id.titleView);
        tvUserName = view.findViewById(R.id.frag_user_details_tv_user_name);
        tvWorkId = view.findViewById(R.id.frag_user_details_tv_user_work_id);
        tvDepot = view.findViewById(R.id.frag_user_details_tv_changeDepot);
        btnQuit = view.findViewById(R.id.frag_user_details_btn_quit);


        btnQuit.setOnClickListener(this);
        tvDepot.setOnClickListener(this);
    }

    @Override
    public void initWidget(View view) {
        super.initWidget(view);

        if (mUserClientBean != null) {
            tvUserName.setText(mUserClientBean.getUserName());
            tvWorkId.setText(mUserClientBean.getUserCode());
        }

        if (mDepot != null) {
            setDepotTextView(mDepot);
        }
    }

    private void setDepotTextView(DepotBean depot) {
        tvDepot.setText(depot.getWhname() + "(" + depot.getWhcode() + ")");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int i = view.getId();
        if (i == R.id.frag_user_details_tv_changeDepot) {
            Bundle bundle = new Bundle();
            Class depotSelectActivity = InfoLoadUtils.getInstance().getFunctionMainActivityLoad().getDepotSelectActivity(bundle);
            startActivity(depotSelectActivity, bundle);
        } else if (i == R.id.frag_user_details_btn_quit) {
            ModelService.post(ApiUrl.URL_LOGOUT, null, new ResultCallback() {
                @Override
                public void onFailed(String msg) {

                }

                @Override
                public void onSuccess(ResultBean response) {

                }
            });
            UserClientUtils.clearLoginUser(getContext());
            DepotUtils.clear(getContext());
            LoginUtils.clearAll(getContext());
            startActivity(new Intent(getActivity(), InfoLoadUtils.getInstance().getActivityLoad().getLoginActivity()));
            getActivity().finish();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeDepotEvent(DepotBean depot) {
        setDepotTextView(depot);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusFactory.getDepotEventBus().unregister(this);
    }
}
