package com.shqtn.b.enter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shqtn.b.R;
import com.shqtn.base.BaseActivity;

/**
 * 创建时间:2018/1/5
 * 描述:
 *
 * @author ql
 */

public class ViewInfo {

    /**
     * 创建 添加序列号按钮
     *
     * @param aty
     * @return
     */
    public static View createAddSerialButton(BaseActivity aty) {
        TextView tvSerial = new TextView(aty);
        tvSerial.setText("添加序列号");
        tvSerial.setOnClickListener(aty);
        tvSerial.setId(R.id.btn_serial);
        tvSerial.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        tvSerial.setLayoutParams(params);
        tvSerial.setTextColor(ContextCompat.getColor(aty, R.color.text_white_color));
        tvSerial.setBackgroundColor(ContextCompat.getColor(aty, R.color.colorAccent));
        return tvSerial;
    }
}
