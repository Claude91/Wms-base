package com.shqtn.b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.CommonAdapter;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.AskMsgDialog;

import java.util.ArrayList;

public class SerialAddActivity extends BaseBActivity implements TitleView.OnRightTextClickListener {
    public static final int REQUEST_CODE_SERIAL_SRC = 3;

    public static final String SRC_SERIALS = "src_serials";
    public static final String ADD_SERIALS_SIZE = "addSerialsSize";
    public static final String ADD_SERIALS = "addSerials";

    public static final String NO_SERIALS = "noSerials";

    public static final String RESULT_ADD_SERIALS = "resultAddSerials";

    private ArrayList<String> srcSerials;//需要进行匹配的序列号
    private double addSerialsSize;
    private TextView tvYes;
    private LabelTextView ltvAddQty, ltvTotalQty;
    private ListView lv;
    private CommonAdapter<String> mAddSerialAdapter;
    private ArrayList<String> addSerials = new ArrayList<>();
    private ArrayList<String> noSerials;//不可采集这些序列号
    private int removePosition;
    private AskMsgDialog.OnAskClickListener listener = new AskMsgDialog.OnAskClickListener() {
        @Override
        public void clickTrue() {
            addSerials.remove(removePosition);
            changeAddSerialData();
        }

        @Override
        public void clickCancel() {

        }
    };

    /**
     * 不可采集的序列号
     *
     * @param noSerials
     * @param bundle
     */
    public static void putNoSerial(ArrayList<String> noSerials, Bundle bundle) {
        bundle.putStringArrayList(NO_SERIALS, noSerials);
    }

    public static void put(ArrayList<String> srcSerials, ArrayList<String> addSerials, double totalSize, Bundle bundle) {
        bundle.putStringArrayList(SRC_SERIALS, srcSerials);
        bundle.putStringArrayList(ADD_SERIALS, addSerials);

        bundle.putDouble(ADD_SERIALS_SIZE, totalSize);
    }


    public static ArrayList<String> getSerialList(Intent data) {
        return data.getStringArrayListExtra(RESULT_ADD_SERIALS);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_serial_add);
    }


    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        if (bundle != null) {
            srcSerials = bundle.getStringArrayList(SRC_SERIALS);
            addSerials = bundle.getStringArrayList(ADD_SERIALS);
            addSerialsSize = bundle.getDouble(ADD_SERIALS_SIZE);

            noSerials = bundle.getStringArrayList(NO_SERIALS);
        }

        if (addSerials == null) {
            addSerials = new ArrayList<>();
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

        if (srcSerials != null && srcSerials.size() > 0) {
            //显示右上角的 显示 当前需要添加的序列号
            titleView.setRightText("序列号列表");
            titleView.setOnRightTextClickListener(this);
        }
        setInputCode.setHintText("请扫描序列号");


        ltvAddQty.setText(String.valueOf(addSerials.size()));
        ltvTotalQty.setText(String.valueOf(
                getCanAddSerialMaxSize()
        ));

        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
                                                   @Override
                                                   public void onSearchText(String content) {
                                                       if (!isCanAdd(content)) {
                                                           return;
                                                       }
                                                       addSerials.add(0, content);
                                                       changeAddSerialData();
                                                   }
                                               }

        );

        mAddSerialAdapter = new CommonAdapter<String>(R.layout.item_add_serial)

        {
            @Override
            public void setItemContent(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_add_serial_tv, s);
            }
        }

        ;
        mAddSerialAdapter.update(addSerials);
        lv.setAdapter(mAddSerialAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                          removePosition = position;
                                          displayAskMsgDialog("删除", String.format("是否确定删除此序列号:%s", addSerials.get(removePosition)), listener);
                                      }
                                  }

        );
    }

    private void changeAddSerialData() {
        ltvAddQty.setText(String.valueOf(addSerials.size()));
        mAddSerialAdapter.update(addSerials);
    }

    private boolean isCanAdd(String content) {
        if (noSerials != null) {
            for (int i = 0; i < noSerials.size(); i++) {
                String s = noSerials.get(i);
                if (!StringUtils.isEmpty(s) && s.equals(content)) {
                    displayMsgDialog(String.format("序列号:%s,已经提交，不能再次添加", content));
                    return false;
                }
            }
        }

        if (getCanAddSerialMaxSize() == addSerials.size()) {
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
            displayMsgDialog(String.format("录入序列号:%s \n与指定序列号不匹配，请检查序列号", content));
            return false;
        }

        return true;
    }

    /**
     * 可以添加的最大数量
     *
     * @return
     */
    private double getCanAddSerialMaxSize() {
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
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onClickTitleRightText() {
        Bundle bundle = new Bundle();
        SerialSrcActivity.putSerials(srcSerials, addSerials, bundle);
        startActivity(SerialSrcActivity.class, bundle, REQUEST_CODE_SERIAL_SRC);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SERIAL_SRC && resultCode == Activity.RESULT_OK) {
            ArrayList<String> addSerials = SerialSrcActivity.getAddSerials(data);
            if (addSerials == null) {
                this.addSerials.clear();
            } else {
                this.addSerials = addSerials;
            }

            mAddSerialAdapter.update(addSerials);
            ltvAddQty.setText(String.valueOf(this.addSerials.size()));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
