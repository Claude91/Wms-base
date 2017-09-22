package com.shqtn.enter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.BaseFragment;
import com.shqtn.base.widget.RadioGroupView;
import com.shqtn.enter.frag.FunctionFragment;
import com.shqtn.enter.frag.UserDetailsFragment;


public class HomeActivity extends BaseActivity {

    private static final int BUTTON_TAG_FUNCTION = 0X333;
    private static final int BUTTON_TAG_USER_DETAILS = 0X222;

    private View btnFunction;
    private View btnUserDetails;
    private RadioGroupView rgvBtnGroup;

    private BaseFragment mFunctionFragment;
    private BaseFragment mUserDetailsFragment;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initData() {
        super.initData();
        mFunctionFragment = FunctionFragment.newInstance();
        mUserDetailsFragment = UserDetailsFragment.newInstance();
    }

    @Override
    public void bindView() {
        btnFunction = findViewById(R.id.home_btn_function);
        btnUserDetails = findViewById(R.id.home_btn_userDetails);
        rgvBtnGroup = (RadioGroupView) findViewById(R.id.home_bottom_btn_group);
        btnFunction.setTag(BUTTON_TAG_FUNCTION);
        btnUserDetails.setTag(BUTTON_TAG_USER_DETAILS);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    }

    @Override
    public void initWidget() {
        super.initWidget();
        changeFragment(R.id.home_fl_content,mFunctionFragment);
        rgvBtnGroup.setOnRadioChildChangeListener(new RadioGroupView.OnRadioChildChangeListener() {
            @Override
            public void onChangeChildren(Object tag, View view) {
                int index = (int) tag;
                switch (index) {
                    case BUTTON_TAG_FUNCTION:
                        changeFragment(R.id.home_fl_content,mFunctionFragment);
                        break;
                    case BUTTON_TAG_USER_DETAILS:
                        changeFragment(R.id.home_fl_content,mUserDetailsFragment);
                        break;
                }
            }
        });


    }
}
