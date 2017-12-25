package com.shqtn.b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.ql.bindview.BindView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.CommonAdapter;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.AskMsgDialog;

import java.util.ArrayList;

public class AddSerialActivity extends BaseBActivity implements TitleView.OnRightTextClickListener {
    public static final String SRC_SERIALS = "src_serials";
    public static final String ADD_SERIALS_SIZE = "addSerialsSize";

    public static final String RESULT_ADD_SERIALS = "resultAddSerials";

    private ArrayList<String> srcSerials;//需要进行匹配的序列号
    private int addSerialsSize;
    private TextView tvYes;
    private LabelTextView ltvAddQty, ltvTotalQty;
    private ListView lv;
    private CommonAdapter<String> mAddSerialAdapter;
    private ArrayList<String> addSerials = new ArrayList<>();
    private int removePosition;
    private AskMsgDialog.OnAskClickListener listener = new AskMsgDialog.OnAskClickListener() {
        @Override
        public void clickTrue() {

            if (srcSerials != null) {
                srcSerials.add(addSerials.get(removePosition));
            }
            addSerials.remove(removePosition);
            changeAddSerialData();
        }

        @Override
        public void clickCancel() {

        }
    };


    public static void put(ArrayList<String> srcSerials, Bundle bundle) {
        bundle.putStringArrayList(SRC_SERIALS, srcSerials);
    }

    public static void putAddSize(int size, Bundle bundle) {
        bundle.putInt(ADD_SERIALS_SIZE, size);
    }

    public static ArrayList<String> getSerialList(Intent data){
        return data.getStringArrayListExtra(RESULT_ADD_SERIALS);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_add_serial);
    }


    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        if (bundle != null) {
            srcSerials = bundle.getStringArrayList(SRC_SERIALS);
            addSerialsSize = bundle.getInt(ADD_SERIALS_SIZE);
        }
    }

    @Override
    public void bindView() {
        super.bindView();

        tvYes = (TextView) findViewById(R.id.activity_add_serial_tv_yes);
        ltvAddQty = (LabelTextView) findViewById(R.id.activity_add_serial_ltv_add_qty);
        ltvTotalQty = (LabelTextView) findViewById(R.id.activity_add_serial_ltv_total_qty);
        lv = (ListView) findViewById(R.id.activity_add_serial_lv);

    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvYes.setOnClickListener(this);


        titleView.setOnRightTextClickListener(this);

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                if (!isCanAdd(content)) {
                    return;
                }
                addSerials.add(0, content);
                if (srcSerials != null) {
                    srcSerials.remove(content);
                }
                changeAddSerialData();
            }
        });

        mAddSerialAdapter = new CommonAdapter<String>(R.layout.item_add_serial) {
            @Override
            public void setItemContent(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_add_serial_tv, s);
            }
        };
        lv.setAdapter(mAddSerialAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removePosition = position;
                displayAskMsgDialog("删除", "是否确定删除此序列号", listener);
            }
        });
    }

    private void changeAddSerialData() {
        ltvAddQty.setText(String.valueOf(addSerials.size()));
        mAddSerialAdapter.update(addSerials);
    }

    private boolean isCanAdd(String content) {
        if (getCanAddSerialMaxSize() < addSerials.size()) {
            displayMsgDialog("数量已经达到最大数量，不可添加");
            return false;
        }
        for (String addSerial : addSerials) {
            if (addSerial.equals(content)) {
                displayMsgDialog(String.format("不能重复添加序列号:%s", content));
                return false;
            }
        }

        if (srcSerials != null) {
            for (String srcSerial : srcSerials) {
                if (srcSerial.equals(content)) {
                    return true;
                }
            }
            return false;
        }

        return true;
    }

    /**
     * 可以添加的最大数量
     *
     * @return
     */
    private int getCanAddSerialMaxSize() {
        if (srcSerials != null) {
            return srcSerials.size();
        }
        return addSerialsSize;
    }

    @Override
    public void widgetClick(View v) {
        if (v.getId() == R.id.activity_add_serial_tv_yes) {
            if (addSerials.size() == 0) {
                displayMsgDialog("请添加序列号");
                return;
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra(RESULT_ADD_SERIALS, addSerials);
            setResult(Activity.RESULT_OK);
        }
    }

    @Override
    public void onClickTitleRightText() {
        // TODO: 2017/12/22 跳转到 需要添加序列号的列表中 查看详情
    }
}
