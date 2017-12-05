package com.shqtn.enter.print.aty;

import android.widget.EditText;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.widget.TitleView;
import com.shqtn.enter.BaseEnterActivity;
import com.shqtn.enter.R;
import com.shqtn.enter.print.bean.ImageSize;
import com.shqtn.enter.print.preferences.ImageSizePreference;

public class PrintImageSettingActivity extends BaseEnterActivity implements TitleView.OnRightTextClickListener {

    EditText etStartX, etEndX, etStartY, etEndY;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_print_image_setting);
    }

    @Override
    public void bindView() {
        super.bindView();
        etStartX = (EditText) findViewById(R.id.activity_print_image_setting_et_start_x);
        etEndX = (EditText) findViewById(R.id.activity_print_image_setting_et_end_x);

        etStartY = (EditText) findViewById(R.id.activity_print_image_setting_et_start_y);
        etEndY = (EditText) findViewById(R.id.activity_print_image_setting_et_end_y);

    }

    @Override
    public void initWidget() {
        super.initWidget();
        titleView.setOnRightTextClickListener(this);
        ImageSize imageSize = ImageSizePreference.getImageSize(this);

        if (imageSize != null) {
            etStartX.setText(String.valueOf(imageSize.getStartY()));
            etEndX.setText(String.valueOf(imageSize.getStartX()));
            etStartY.setText(String.valueOf(imageSize.getEndY()));
            etEndY.setText(String.valueOf(imageSize.getEndX()));
        }
    }

    @Override
    public void onClickTitleRightText() {
        String startX = etStartX.getText().toString();
        String endX = etEndX.getText().toString();
        String startY = etStartY.getText().toString();
        String endY = etEndY.getText().toString();

        if (!checkSize(startX)) {
            displayMsgDialog("请输入正确大小");
            return;
        }
        if (!checkSize(endX)) {
            displayMsgDialog("请输入正确大小");
            return;
        }
        if (!checkSize(startY)) {
            displayMsgDialog("请输入正确大小");
            return;
        }
        if (!checkSize(endY)) {
            displayMsgDialog("请输入正确大小");
            return;
        }

        ImageSize imageSize = new ImageSize();
        imageSize.setStartY(NumberUtils.getInt(startX));
        imageSize.setEndX(NumberUtils.getInt(endX));
        imageSize.setStartY(NumberUtils.getInt(startY));
        imageSize.setEndY(NumberUtils.getInt(endY));


        ImageSizePreference.save(this, imageSize);
        finish();
    }

    private boolean checkSize(String size) {
        int topLeftD = NumberUtils.getInt(size);
        if (topLeftD < 0) {
            return false;
        }
        return true;
    }
}
