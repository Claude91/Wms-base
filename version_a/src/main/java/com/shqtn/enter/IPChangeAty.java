package com.shqtn.enter;

import android.ql.bindview.BindView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.IpChangeUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.utils.ToastUtils;
import com.shqtn.base.widget.TitleView;


public class IPChangeAty extends BaseActivity {


    private TitleView tvTitle;
    private EditText ipChangeEtIp;
    private EditText ipChangeEtPort;
    private Button ipChangeBtnYes;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_ip_change_aty);
    }

    @Override
    public void bindView() {
        super.bindView();
        tvTitle = (TitleView) findViewById(R.id.tv_title);
        ipChangeEtIp = (EditText) findViewById( R.id.ip_change_et_ip);
        ipChangeEtPort = (EditText) findViewById(R.id.ip_change_et_port);
        ipChangeBtnYes = (Button) findViewById(R.id.ip_change_btn_yes);

    }

    @Override
    public void initWidget() {
        String ip = IpChangeUtils.getIp(this);
        String port = IpChangeUtils.getPort(this);
        if (StringUtils.isEmpty(ip)) {
            ip = ApiUrl.IP;
        }
        if (StringUtils.isEmpty(port)) {
            port = ApiUrl.POST;
        }

        ipChangeEtIp.setText(ip);
        ipChangeEtPort.setText(port);

        ipChangeBtnYes.setOnClickListener(this);
        tvTitle.setOnToBackClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.ip_change_btn_yes) {
            onViewClicked();

        }
    }


    public void onViewClicked() {
        String port = ipChangeEtPort.getText().toString();
        String ip = ipChangeEtIp.getText().toString();

        if (!isEmpty(ip, port)) {
            IpChangeUtils.saveIp(this, ip);
            IpChangeUtils.savePort(this, port);

            IpChangeUtils.changeIp(ip, port);

            ToastUtils.show(this, "修改成功");
            finish();
        }


    }

    private boolean isEmpty(String ip, String port) {
        if (StringUtils.isEmpty(ip)) {
            displayMsgDialog("请填写IP");
            return true;
        }
        if (StringUtils.isEmpty(port)) {
            displayMsgDialog("请填写端口号");
            return true;
        }
        return false;
    }
}
