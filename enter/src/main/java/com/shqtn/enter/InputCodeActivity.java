package com.shqtn.enter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;

public class InputCodeActivity extends BaseEnterActivity implements TitleView.OnRightTextClickListener {
    public static final String INPUT_CODE = "input_code";
    public static final String BUNDLE_LABEL = "bundle_label";
    public static final String BUNDLE_HINT = "bundle_hint";
    public static final String BUNDLE_TITLE = "bundle_title";
    TextView tvInputCode, tvLabel;

    public static String getBatchNo(Intent data) {
        return data.getStringExtra(INPUT_CODE);
    }

    public static void setTitle(String title, Bundle bundle) {
        bundle.putString(BUNDLE_TITLE, title);
    }

    public static void setLabel(String label, Bundle bundle) {
        bundle.putString(BUNDLE_LABEL, label);
    }

    public static void setInputCodeHint(String hint, Bundle bundle) {
        bundle.putString(BUNDLE_HINT, hint);
    }

    public static void set(String title, String label, String hint, Bundle bundle) {
        setTitle(title, bundle);
        setLabel(label, bundle);
        setInputCodeHint(hint, bundle);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_input_code);
    }

    @Override
    public void bindView() {
        tvInputCode = (TextView) findViewById(R.id.activity_input_code_tv_input_code);
        tvLabel = (TextView) findViewById(R.id.activity_input_code_tv_label);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        Bundle bundle = getBundle();
        if (bundle != null) {
            String hint = bundle.getString(BUNDLE_HINT);
            if (!StringUtils.isEmpty(hint)) {
                setInputCode.setHintText(hint);
            }
            String label = bundle.getString(BUNDLE_LABEL);
            if (!StringUtils.isEmpty(label)) {
                tvLabel.setText(String.format("%s:", label));
            }
            String title = bundle.getString(BUNDLE_TITLE);
            if (!StringUtils.isEmpty(title)) {
                titleView.setTitle(title);
            }
        }

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                tvInputCode.setText(content);
            }
        });
        titleView.setOnRightTextClickListener(this);
    }

    @Override
    public void onOnClick() {
        CharSequence inputCode = tvInputCode.getText();
        if (StringUtils.isEmpty(inputCode)) {
            toast("请输入字符");
            return;
        }

        Intent result = new Intent();
        result.putExtra(INPUT_CODE, inputCode.toString());
        setResult(Activity.RESULT_OK, result);
        finish();
    }


}
