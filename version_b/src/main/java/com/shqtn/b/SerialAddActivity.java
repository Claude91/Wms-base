package com.shqtn.b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shqtn.base.CommonAdapter;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.help.CodeCallback;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.enter.controller.CodeController;
import com.shqtn.enter.controller.impl.CodePresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class SerialAddActivity extends BaseBActivity implements TitleView.OnRightTextClickListener, CodeController.View {
    public static final int REQUEST_CODE_SERIAL_SRC = 3;

    public static final String SRC_SERIALS = "src_serials";
    public static final String ADD_SERIALS_SIZE = "addSerialsSize";
    public static final String ADD_SERIALS = "addSerials";

    public static final String NO_SERIALS = "noSerials";

    public static final String RESULT_ADD_SERIALS = "resultAddSerials";

    private ArrayList<String> srcSerials;//需要进行匹配的序列号
    private double addSerialsSize;
    private TextView tvYes, tvModeSerial, tvModeBarcode;
    private LabelTextView ltvAddQty, ltvTotalQty;
    private ViewGroup modeGroup;
    private ListView lv;
    private CommonAdapter<String> mAddSerialAdapter;
    private ArrayList<String> addSerials = new ArrayList<>();
    private ArrayList<String> noSerials;//不可采集这些序列号

    private int removePosition;

    private CodeController.Presenter codePresenter;

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

        /*
         * 删除 不可添加的 序列号
         */
        if (noSerials != null && srcSerials != null) {
            for (String noSerial : noSerials) {
                srcSerials.remove(noSerial);
            }
        }
        codePresenter = new CodePresenterImpl(this);
        codePresenter.setDecodeType(CodeCallback.TAG_GOODS);

        codePresenter.setDecodeCallback(this);

    }

    @Override
    public void decodeGoods(CodeGoods goods) {
        super.decodeGoods(goods);
        cancelProgressDialog();
        toAddSerial(goods.getSerialNo());
    }

    @Override
    public void bindView() {
        super.bindView();
        modeGroup = (ViewGroup) findViewById(R.id.activity_add_serial_mode_group);
        tvYes = (TextView) findViewById(R.id.activity_add_serial_tv_yes);
        ltvAddQty = (LabelTextView) findViewById(R.id.activity_add_serial_ltv_add_qty);
        ltvTotalQty = (LabelTextView) findViewById(R.id.activity_add_serial_ltv_total_qty);
        lv = (ListView) findViewById(R.id.activity_add_serial_lv);
        tvModeBarcode = (TextView) findViewById(R.id.activity_add_serial_tv_mode_barcode);
        tvModeSerial = (TextView) findViewById(R.id.activity_add_serial_tv_mode_serial);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvYes.setOnClickListener(this);
        modeGroup.setOnClickListener(this);
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
                                                       if (isModeBarcode()) {
                                                           displayProgressDialog("解码中");
                                                           codePresenter.toDecode(content);
                                                       } else if (isModeSerial()) {
                                                           toAddSerial(content);
                                                       }
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

        setMode(tvModeSerial);


    }

    private void setMode(View modeView) {
        if (modeView == tvModeBarcode) {
            tvModeBarcode.setSelected(true);
            tvModeSerial.setSelected(false);
        } else if (modeView == tvModeSerial) {
            tvModeBarcode.setSelected(false);
            tvModeSerial.setSelected(true);
        }
    }

    private void toAddSerial(String content) {
        if (!isCanAdd(content)) {
            return;
        }
        addSerials.add(0, content);
        changeAddSerialData();
    }

    private void changeAddSerialData() {
        ltvAddQty.setText(String.valueOf(addSerials.size()));
        mAddSerialAdapter.update(addSerials);
    }


    @Override
    public boolean onKeyF4() {
        changeMode();

        return true;
    }

    private void changeMode() {
        if (tvModeBarcode.isSelected()) {
            setMode(tvModeSerial);
        } else {
            setMode(tvModeBarcode);
        }
    }

    /**
     * 是否是扫描货品二维码模式
     *
     * @return
     */
    private boolean isModeBarcode() {
        return tvModeBarcode.isSelected();
    }

    /**
     * 是否 扫描序列号模式
     *
     * @return
     */
    private boolean isModeSerial() {
        return tvModeSerial.isSelected();
    }

    private boolean isCanAdd(String content) {
        if (noSerials != null) {
            for (int i = 0; i < noSerials.size(); i++) {
                String s = noSerials.get(i);
                if (!StringUtils.isEmpty(s) && s.equals(content)) {
                    displayMsgDialog(String.format("序列号:%s,已经提交,或已经进行其他操作不可输入", content));
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
        } else if (v.getId() == R.id.activity_add_serial_mode_group) {
            changeMode();
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

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setEditTextHint(String hint) {

    }
}
