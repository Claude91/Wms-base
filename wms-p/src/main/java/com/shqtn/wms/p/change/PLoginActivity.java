package com.shqtn.wms.p.change;

import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.enter.LoginActivity;
import com.shqtn.wms.p.R;

/**
 * Created by android on 2017/10/23.
 */

public class PLoginActivity  extends LoginActivity {

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_login_p);
    }

    @Override
    public void bindView() {
        super.bindView();
        login_et_editAccount = (EditText) findViewById(com.shqtn.enter.R.id.login_et_editAccount);
        login_et_editPassWord = (EditText) findViewById(com.shqtn.enter.R.id.login_et_editPassWord);
        login_btn_login = (Button) findViewById(com.shqtn.enter.R.id.login_btn_login);
        login_btn_loginChangeIp = (Button) findViewById(com.shqtn.enter.R.id.login_btn_loginChangeIp);

        login_btn_login.setOnClickListener(this);
        login_btn_loginChangeIp.setOnClickListener(this);

        login_et_editPassWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENDCALL){
                    toLogin();
                    return true;
                }
                return false;
            }
        });

    }

}
