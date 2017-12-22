package com.shqtn.enter.print.aty;

import android.app.Activity;
import android.text.Editable;
import android.widget.EditText;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.BaseEnterActivity;
import com.shqtn.enter.R;
import com.shqtn.enter.even.BluetoothAddressEvent;
import com.shqtn.enter.print.preferences.BluetoothAddressPreferences;

public class InputBluetoothAddressActivity extends BaseEnterActivity implements TitleView.OnRightTextClickListener {

    EditText etInputAddress;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_input_bluetooth_address);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void bindView() {
        super.bindView();
        etInputAddress = (EditText) findViewById(R.id.activity_input_bluetooth_address_et_input);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        titleView.setOnRightTextClickListener(this);
    }

    /**
     * 单机完成
     */
    @Override
    public void onClickTitleRightText() {
        Editable address = etInputAddress.getText();
        if (StringUtils.isEmpty(address)) {
            displayMsgDialog("请输入蓝牙地址");
            return;
        }

        BluetoothAddressPreferences.save(InputBluetoothAddressActivity.this, address.toString());
        toast("操作成功");
        setResult(Activity.RESULT_OK);
        BluetoothAddressEvent.getInstance().postMsg(address.toString());
        finish();

    }
}
