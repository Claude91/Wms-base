package com.shqtn.b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.CommonAdapter;
import com.shqtn.base.widget.TitleView;

import java.util.ArrayList;

public class SerialSrcActivity extends BaseBActivity {
    public static final String BUNDLE_SERIAL = "serial";
    public static final String BUNDLE_ADD_SERIAL = "addSerial";
    public static final String RESULT_SELECT_SERIAL = "selectSerial";

    private ListView lvSerialSrc;
    private CommonAdapter<String> serialAdapter;
    private ArrayList<String> selectSerials;
    private ArrayList<String> srcSerials;

    public static ArrayList<String> getAddSerials(Intent data) {
        if (data == null) {
            return null;
        }

        return data.getStringArrayListExtra(RESULT_SELECT_SERIAL);
    }

    public static void putSerials(@NonNull ArrayList<String> serials, @Nullable ArrayList<String> addSerials, Bundle bundle) {
        bundle.putStringArrayList(BUNDLE_SERIAL, serials);
        bundle.putStringArrayList(BUNDLE_ADD_SERIAL, addSerials);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_serial_src);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        srcSerials = bundle.getStringArrayList(BUNDLE_SERIAL);
        selectSerials = bundle.getStringArrayList(BUNDLE_ADD_SERIAL);
    }

    @Override
    public void bindView() {
        lvSerialSrc = (ListView) findViewById(R.id.activity_serial_src_lv);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        titleView.setRightText("完成");
        titleView.setOnRightTextClickListener(new TitleView.OnRightTextClickListener() {
            @Override
            public void onClickTitleRightText() {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(RESULT_SELECT_SERIAL, selectSerials);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        serialAdapter = new CommonAdapter<String>(R.layout.item_add_serial) {
            @Override
            public void setItemContent(ViewHolder holder, String s, int position) {
                TextView tvSerial = holder.getViewById(R.id.item_add_serial_tv);
                boolean isSelect = selectSerials != null && selectSerials.contains(s);
                setSelect(tvSerial, isSelect);
                tvSerial.setText(s);
            }

            private void setSelect(View view, boolean isSelect) {
                if (view.isSelected() != isSelect) {
                    view.setSelected(isSelect);
                }
            }
        };
        lvSerialSrc.setAdapter(serialAdapter);

        serialAdapter.update(srcSerials);

        lvSerialSrc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectSerials == null) {
                    selectSerials = new ArrayList<String>(srcSerials.size());
                }
                String serial = srcSerials.get(position);
                if (selectSerials.contains(serial)) {
                    //包含则删除
                    selectSerials.remove(serial);
                } else {
                    selectSerials.add(serial);
                }
                serialAdapter.notifyDataSetChanged();
            }
        });
    }
}
