package com.shqtn.enter.print.aty;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.BaseEnterActivity;
import com.shqtn.enter.InputCodeActivity;
import com.shqtn.enter.R;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.device.ZebraIllegalArgumentException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import static com.shqtn.enter.R.id.activity_box_print_batch_no_group;
import static com.shqtn.enter.R.id.switcher;

public class BoxPrintActivity extends BaseEnterActivity {
    public static final int REQUEST_INPUT_BATCH_NO = 2;

    private TextView tvBatchNo, tvQty;
    private View batchNoGroup, qtyGroup;
    private LabelTextView ltvSku, ltvUnit, ltvStd, ltvName;
    private EditQuantityDialog.OnResultListener mInputQtyListener = new EditQuantityDialog.OnResultListener() {
        @Override
        public void clickOk(double quantity) {
            tvQty.setText(String.valueOf(quantity));
        }

        @Override
        public void clickCancel() {

        }
    };

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_box_print);
    }

    @Override
    public void bindView() {
        super.bindView();
        tvBatchNo = (TextView) findViewById(R.id.activity_box_print_tv_batch_no);
        tvQty = (TextView) findViewById(R.id.activity_box_print_tv_qty);
        batchNoGroup = findViewById(activity_box_print_batch_no_group);
        qtyGroup = findViewById(R.id.activity_box_print_qty_group);

        batchNoGroup.setOnClickListener(this);
        qtyGroup.setOnClickListener(this);

        ltvSku = (LabelTextView) findViewById(R.id.activity_box_print_ltv_sku);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_box_print_ltv_unit);
        ltvStd = (LabelTextView) findViewById(R.id.activity_box_print_ltv_std);
        ltvName = (LabelTextView) findViewById(R.id.activity_box_print_ltv_name);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        setInputCode.setHintText("请输入编码");
        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {

            }
        });
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        int i = v.getId();
        if (i == R.id.activity_box_print_batch_no_group) {
            Bundle bundle = new Bundle();
            InputCodeActivity.set("批次号编辑", "批次号", "请输入批次号", bundle);
            startActivity(InputCodeActivity.class, bundle, REQUEST_INPUT_BATCH_NO);
        } else if (i == R.id.activity_box_print_qty_group) {
            displayEditQty(mInputQtyListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_INPUT_BATCH_NO == requestCode && Activity.RESULT_OK == resultCode) {
            String inputBatchNo = InputCodeActivity.getBatchNo(data);
            tvBatchNo.setText(inputBatchNo);
        }
    }

    private void decode(String code) {

    }


    private void printPhotoFromExternal(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Looper.prepare();
                    Connection connection = getZebraPrinterConn();
                    connection.open();
                    ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);

                    printer.printImage(new ZebraImageAndroid(bitmap), 0, 0, 550, 412, false);
                    connection.close();
                } catch (ConnectionException e) {
                } catch (ZebraPrinterLanguageUnknownException e) {
                } finally {
                    bitmap.recycle();
                    Looper.myLooper().quit();
                }
            }
        }).start();

    }

    private Connection getZebraPrinterConn() {
        return new BluetoothConnection(getMaxAddress());
    }

    public String getMaxAddress() {
        return "";
    }
}
