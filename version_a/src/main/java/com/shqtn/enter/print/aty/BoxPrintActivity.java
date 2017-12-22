package com.shqtn.enter.print.aty;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.http.ErrorHint;
import com.shqtn.base.http.ModelService;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.ApiUrl;
import com.shqtn.base.utils.NumberUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.base.widget.LabelTextView;
import com.shqtn.base.widget.SystemEditText;
import com.shqtn.base.widget.TitleView;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.base.widget.dialog.EditQuantityDialog;
import com.shqtn.enter.BaseEnterActivity;
import com.shqtn.enter.InputCodeActivity;
import com.shqtn.enter.R;
import com.shqtn.enter.even.BluetoothAddressEvent;
import com.shqtn.enter.print.BluetoothHelper;
import com.shqtn.enter.print.bean.BarCode;
import com.shqtn.enter.print.bean.Decode;
import com.shqtn.enter.print.bean.ImageSize;
import com.shqtn.enter.print.bean.PrintImagePathBean;
import com.shqtn.enter.print.bean.params.PrintDecodeParams;
import com.shqtn.enter.print.preferences.BluetoothAddressPreferences;
import com.shqtn.enter.print.preferences.ImageSizePreference;
import com.squareup.okhttp.Request;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class BoxPrintActivity extends BaseEnterActivity implements TitleView.OnRightTextClickListener {
    public static final int REQUEST_INPUT_BATCH_NO = 2;
    private String mBluetoothAddress;
    private TextView tvBatchNo, tvQty;
    private View batchNoGroup, qtyGroup;
    private LabelTextView ltvSku, ltvUnit, ltvStd, ltvName;
    private TextView tvPrintImg;
    private ScrollView mScrollView;
    final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
    private LabelTextView ltvBluetoothAddress;

    private Decode mOperateDecode;//当前操作的货品

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
    public void initData() {
        super.initData();
        BluetoothAddressEvent.getInstance().register(this);
        mBluetoothAddress = BluetoothAddressPreferences.getAddress(this);
    }

    @Override
    public void bindView() {
        super.bindView();
        tvBatchNo = (TextView) findViewById(R.id.activity_box_print_tv_batch_no);
        tvQty = (TextView) findViewById(R.id.activity_box_print_tv_qty);
        batchNoGroup = findViewById(R.id.activity_box_print_batch_no_group);
        qtyGroup = findViewById(R.id.activity_box_print_qty_group);

        batchNoGroup.setOnClickListener(this);
        qtyGroup.setOnClickListener(this);

        ltvSku = (LabelTextView) findViewById(R.id.activity_box_print_ltv_sku);
        ltvUnit = (LabelTextView) findViewById(R.id.activity_box_print_ltv_unit);
        ltvStd = (LabelTextView) findViewById(R.id.activity_box_print_ltv_std);
        ltvName = (LabelTextView) findViewById(R.id.activity_box_print_ltv_name);

        ltvBluetoothAddress = (LabelTextView) findViewById(R.id.activity_box_print_ltv_bluetooth_address);

        tvPrintImg = (TextView) findViewById(R.id.activity_box_print_tv_print);

        mScrollView = (ScrollView) findViewById(R.id.activity_box_print_scroll_view);

        titleView.setOnRightTextClickListener(this);
    }

    private boolean checkBluetooth() {

        if (defaultAdapter == null) {
            displayMsgDialog("当前设备不支持蓝牙设备");
            return false;
        }

        if (!defaultAdapter.isEnabled()) {
            displayAskMsgDialog("蓝牙设备问题", "蓝牙设备未打开,是否前往开启蓝牙，并连接打印设备", new AskMsgDialog.OnAskClickListener() {
                @Override
                public void clickTrue() {
                    BluetoothHelper.openBluetooth(BoxPrintActivity.this, defaultAdapter);
                }

                @Override
                public void clickCancel() {

                }
            });
            return false;
        }
        return true;
    }

    private void toEditBluetoothAddress() {
        startActivity(InputBluetoothAddressActivity.class);
    }

    @Override
    public void onClickTitleRightText() {
        startActivity(PrintImageSettingActivity.class);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        if (!StringUtils.isEmpty(mBluetoothAddress)) {
            ltvBluetoothAddress.setText(mBluetoothAddress);
        }

        ltvBluetoothAddress.setClickable(true);
        ltvBluetoothAddress.setOnClickListener(this);
        tvPrintImg.setOnClickListener(this);
        tvPrintImg.setSelected(true);

        setInputCode.setHintText("请输入编码");
        setInputCode.setOnToTextSearchListener(new SystemEditText.OnToTextSearchListener() {
            @Override
            public void onSearchText(String content) {
                decode(content);
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
            toEditQty();
        }
    }

    private void toEditQty() {
        displayEditQty(mInputQtyListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_INPUT_BATCH_NO == requestCode && Activity.RESULT_OK == resultCode) {
            String inputBatchNo = InputCodeActivity.getBatchNo(data);
            tvBatchNo.setText(inputBatchNo);
        }
    }

    @Override
    public boolean onKeyF2() {
        toEditQty();
        return true;

    }

    private void decode(String code) {
        displayProgressDialog("解码中");
        PrintDecodeParams params = new PrintDecodeParams();
        params.setPbarcode(code);
        ModelService.post(ApiUrl.print_decode, params, new ResultCallback() {
            @Override
            public void onFailed(String msg) {
                cancelProgressDialog();
                displayMsgDialog(msg);
            }

            @Override
            public void onSuccess(ResultBean response) {
                cancelProgressDialog();
                BarCode barCode = getData(response.getData(), BarCode.class);
                if (barCode == null) {
                    displayMsgDialog("解码失败，请联系管理员");
                    return;
                }

                List<Decode> decodes = barCode.getCODE_LIST();
                if (decodes == null || decodes.size() == 0) {
                    displayMsgDialog("解码失败，请联系管理员");
                    return;
                }

                mOperateDecode = decodes.get(0);
                resetGoodsView(mOperateDecode);
            }
        });
    }

    @Override
    public void widgetForbid(View v) {
        int i = v.getId();
        if (i == R.id.activity_box_print_ltv_bluetooth_address) {
            toEditBluetoothAddress();
        } else if (i == R.id.activity_box_print_tv_print) {

            checkPrintStatus();
            if (mOperateDecode == null) {
                displayMsgDialog("请扫描货品");
                return;
            }

            if (tvPrintImg.isSelected()) {
                return;
            }
            if (!checkBluetooth()) {
                return;
            }
            String qty = tvQty.getText().toString();

            mOperateDecode.setQuantity(NumberUtils.getInt(qty));
            String batchNo = tvBatchNo.getText().toString();
            if (!StringUtils.isEmpty(batchNo)) {
                mOperateDecode.setBatchNo(batchNo);
            }
            mOperateDecode.setGoodsBatchNo(null);
            displayProgressDialog("生成标签中");

            ModelService.post(ApiUrl.print_create_image, mOperateDecode, new ResultCallback() {
                @Override
                public void onFailed(String msg) {
                    cancelProgressDialog();
                    displayMsgDialog(msg);
                }

                @Override
                public void onSuccess(ResultBean response) {
                    PrintImagePathBean printImagePath = getData(response.getData(), PrintImagePathBean.class);

                    toDownloadImage(printImagePath);
                }


            });
        }
    }

    private void toDownloadImage(PrintImagePathBean printImagePath) {
        displayProgressDialog("网络获取图片中,请等待");
        String fileDir = getCacheDir().getAbsolutePath();
        String fileName = printImagePath.getImgPath();
        File file = new File(fileDir, fileName);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                cancelProgressDialog();
                displayMsgDialog("创建本地文件失败");
                return;
            }
        }

        ModelService.post(String.format("%s%s:%s%s", ApiUrl.HTTP, ApiUrl.IP, ApiUrl.POST, printImagePath.getImgPath()), null, new FileCallBack(fileDir, fileName) {
            @Override
            public void inProgress(float progress) {

            }

            @Override
            public void onError(Request request, Exception e) {
                cancelProgressDialog();
                displayMsgDialog(ErrorHint.getErrorHint(e));
            }

            @Override
            public void onResponse(File response) {
                Bitmap bitmap = BitmapFactory.decodeFile(response.getAbsolutePath());
                showPrintBitmap(bitmap);
                printPhotoFromExternal(bitmap);
            }
        });
    }

    private void showPrintBitmap(Bitmap bitmap) {
        ImageView iv = (ImageView) findViewById(R.id.print_iv);
        iv.setImageBitmap(bitmap);
        mScrollView.scrollTo(0, mScrollView.getMeasuredHeight());
    }

    private void checkPrintStatus() {
        if (!checkParams()) {
            if (!tvPrintImg.isSelected()) {
                tvPrintImg.setSelected(true);
            }
        } else {
            if (tvPrintImg.isSelected()) {
                tvPrintImg.setSelected(false);
            }
        }
    }

    private boolean checkParams() {
        if (StringUtils.isEmpty(mBluetoothAddress)) {
            displayMsgDialog("请输入蓝牙连接地址");
            return false;
        }
        String packingQty = tvQty.getText().toString();
        if (StringUtils.isEmpty(packingQty)) {
            displayMsgDialog("请输入数量");
            return false;
        }
        double aDouble = NumberUtils.getDouble(packingQty);
        if (aDouble <= 0) {
            displayMsgDialog("请输入正确数量");
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeBluetoothAddress(String address) {
        this.mBluetoothAddress = address;
        ltvBluetoothAddress.setText(address);
    }

    /**
     * 打印图片
     *
     * @param bitmap
     */
    private void printPhotoFromExternal(final Bitmap bitmap) {
        displayProgressDialog("打印图片中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Connection connection = getZebraPrinterConn();
                    connection.open();
                    ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
                    ImageSize imageSize = ImageSizePreference.getImageSize(BoxPrintActivity.this);
                    printer.printImage(new ZebraImageAndroid(bitmap), imageSize.getStartX(), imageSize.getStartY(), imageSize.getEndX(), imageSize.getEndY(), false);
                    connection.close();
                } catch (ConnectionException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cancelProgressDialog();
                            displayMsgDialog("连接蓝牙设备异常ConnectionException");
                        }
                    });
                } catch (ZebraPrinterLanguageUnknownException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cancelProgressDialog();
                            displayMsgDialog("设备打印异常,ZebraPrinterLanguageUnknownException");
                        }
                    });
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cancelProgressDialog();
                        }
                    });
                    bitmap.recycle();
                    Looper.myLooper().quit();
                }
            }
        }).start();

    }

    private Connection getZebraPrinterConn() {
        return new BluetoothConnection(mBluetoothAddress);
    }

    public void resetGoodsView(Decode decodeView) {
        String skuName = decodeView.getSkuName();
        if (StringUtils.isEmpty(skuName)) {
            skuName = "";
        }
        ltvName.setText(skuName);
        String std = decodeView.getStd();
        if (StringUtils.isEmpty(std)) {
            std = "";
        }
        ltvStd.setText(std);
        String unitName = decodeView.getUnitName();
        if (StringUtils.isEmpty(unitName)) {
            unitName = "";
        }
        ltvUnit.setText(unitName);
        String skuCode = decodeView.getSkuCode();
        if (StringUtils.isEmpty(skuCode)) {
            skuCode = "";
        }
        ltvSku.setText(skuCode);

        String batchNo = decodeView.getBatchNo();
        if (StringUtils.isEmpty(batchNo)) {
            batchNo = "";
        }
        tvBatchNo.setText(batchNo);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothAddressEvent.getInstance().unregister(this);
    }
}
