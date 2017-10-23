package com.shqtn.enter;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.bean.LoginUser;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.bean.UserClientBean;
import com.shqtn.base.bean.params.LoginParams;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.info.LoginInfo;
import com.shqtn.base.utils.ActivityUtils;
import com.shqtn.base.utils.Base64Utils;
import com.shqtn.base.utils.LoginUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.utils.UserClientUtils;

public class LoginActivity extends BaseActivity {

    public EditText login_et_editAccount;
    public EditText login_et_editPassWord;
    public Button login_btn_login;
    public Button login_btn_loginChangeIp;

    public LoginUser mLastLoginUser;//上次登录的账号和密码；
    public LoginUser mSaveLoginUser;//记录这次登陆的账号和密码，并保存

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void bindView() {
        super.bindView();
        login_et_editAccount = (EditText) findViewById(R.id.login_et_editAccount);
        login_et_editPassWord = (EditText) findViewById(R.id.login_et_editPassWord);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_btn_loginChangeIp = (Button) findViewById(R.id.login_btn_loginChangeIp);

        login_btn_login.setOnClickListener(this);
        login_btn_loginChangeIp.setOnClickListener(this);

        login_et_editPassWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENDCALL) {
                    toLogin();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void initWidget() {
        super.initWidget();
        ActivityUtils.getInstance().closeOther(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.login_btn_login) {
            toLogin();
        } else if (i == R.id.login_btn_loginChangeIp) {
            toChangeIpActivity();
        }
    }

    private void toChangeIpActivity() {
        startActivity(IPChangeAty.class);
    }

    public void toLogin() {
        String account = login_et_editAccount.getText().toString();
        String password = login_et_editPassWord.getText().toString();


        if (StringUtils.isEmpty(account)) {
            toFocus(login_et_editAccount);
            ToastUtils.show(this, "请填写账号");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            toFocus(login_et_editPassWord);
            ToastUtils.show(this, "请填写密码");
            return;
        }

        if (mSaveLoginUser == null)
            mSaveLoginUser = new LoginUser();
        mSaveLoginUser.setPassword(password);
        mSaveLoginUser.setAccount(account);

        LoginParams loginParams = new LoginParams();
        long time = System.currentTimeMillis();
        loginParams.setLoginName(account);
        loginParams.setLoginPass(Base64Utils.endBase64(password, String.valueOf(time)));
        if (mLastLoginUser == null) {
            mLastLoginUser = new LoginUser();
        }
        mLastLoginUser.setAccount(account);
        mLastLoginUser.setPassword(password);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.initUnLoginHeaderParams(getApplicationContext(), time);
        displayProgressDialog("登录中");
        ModelService.post(ApiUrl.URL_LOGIN, loginParams, new ResultCallback() {
            @Override
            public void onFailed(String msg) {
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                //登陆成功 保存这次的账号和密码
                UserClientUtils.saveLastUser(getApplicationContext(), mLastLoginUser);

                UserClientBean user = getData(response.getData(), UserClientBean.class);
                LoginInfo loginInfo = new LoginInfo();

                //对请求头进行初始化
                loginInfo.initLoginAfterHeaderParams(getApplicationContext(), user.getTs(), user.getToken());

                //保存用户信息
                loginInfo.saveUser(getApplicationContext(), user);

                //设置当前为登陆状态
                LoginUtils.setLogin(getApplicationContext(), true);

                startActivity(HomeActivity.class);
                finish();
            }

            @Override
            public void onAfter() {
                cancelProgressDialog();
            }
        });
    }

}
