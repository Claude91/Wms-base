package com.shqtn.base.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shqtn.base.R;
import com.shqtn.base.listener.OnClickDeleteListener;
import com.shqtn.base.utils.CashierInputFilter;
import com.shqtn.base.widget.NameTextView;


/**
 * Created by Administrator on 2017-2-17.
 */
public class EditQuantityDialog implements View.OnClickListener {

    private OnResultListener mListener;
    private EditText etQuantity;
    private TextView tvName;
    private CashierInputFilter cashierInputFilter;
    private AlertDialog mDialog;
    private TextView tvMaxValue;

    private View editProDetailsGroup;
    private NameTextView ntvSku, ntvName, ntvUnit, ntvBatchNo;

    private Button btnDelete;

    private OnClickDeleteListener mOnClickDeleteListener;

    public EditQuantityDialog(Context con) {
        init(con);
    }

    public void setOnResultListener(OnResultListener listener) {
        this.mListener = listener;
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialog = li.inflate(R.layout.dialog_edit_quantity_layout, null, false);

        editProDetailsGroup = dialog.findViewById(R.id.ll_dialog_edit_pro_details);
        ntvBatchNo = (NameTextView) dialog.findViewById(R.id.ntv_dialog_edit_batchNo);
        ntvName = (NameTextView) dialog.findViewById(R.id.ntv_dialog_edit_name);
        ntvSku = (NameTextView) dialog.findViewById(R.id.ntv_dialog_edit_sku);
        ntvUnit = (NameTextView) dialog.findViewById(R.id.ntv_dialog_edit_unit);
        btnDelete = (Button) dialog.findViewById(R.id.btn_dialog_delete);
        etQuantity = (EditText) dialog.findViewById(R.id.et_dialogQuantity);
        cashierInputFilter = new CashierInputFilter();
        etQuantity.setFilters(new InputFilter[]{cashierInputFilter});
        tvName = (TextView) dialog.findViewById(R.id.tv_dialog_name);
        tvMaxValue = (TextView) dialog.findViewById(R.id.tv_maxValue);
        dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(this);
        dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(this);
        dialog.findViewById(R.id.btn_dialog_delete).setOnClickListener(this);
        mDialog = new AlertDialog.Builder(context)
                .setView(dialog)
                .create();

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_F2:
                        onClickYesF2(dialog);
                        return true;
                    case KeyEvent.KEYCODE_F3:
                        onClickCancelF3(dialog);
                        return true;
                    case KeyEvent.KEYCODE_F10:
                        onClickF10(dialog);
                }
                return false;
            }


        });


    }

    public void setOnClickDeleteListener(OnClickDeleteListener listener) {

        this.mOnClickDeleteListener = listener;
    }

    private void onClickF10(DialogInterface dialog) {
        onClickDelete();
    }

    private void onClickCancelF3(DialogInterface dialog) {
        mListener.clickCancel();
        dialog.cancel();

    }

    public void setTvName(String name) {
        tvName.setText(name);
    }

    public void setName(String name) {
        ntvName.setText(name);
    }

    public void setSku(String sku) {
        ntvSku.setText(sku);
    }

    public void setUnit(String unit) {
        ntvUnit.setText(unit);
    }

    public void setBatchNo(String batchNo) {
        ntvBatchNo.setText(batchNo);
    }

    public void setShowProDetailsGroup() {
        editProDetailsGroup.setVisibility(View.VISIBLE);
    }

    private double getEtQuantity() {
        double b = 0;
        try {
            b = Double.parseDouble(etQuantity.getText().toString());
        } catch (Exception e) {
            b = 0;
        }
        return b;
    }

    public void setMaxQuantity(double maxQuantity) {
        tvMaxValue.setText("可输入最大数量" + String.valueOf(maxQuantity));
        cashierInputFilter.setMAX_VALUE(maxQuantity);
    }

    private void onClickYesF2(DialogInterface dialog) {
        mListener.clickOk(getEtQuantity());
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_dialog_cancel) {
            mListener.clickCancel();
            mDialog.cancel();

        } else if (i == R.id.btn_dialog_ok) {
            mListener.clickOk(getEtQuantity());
            mDialog.cancel();

        } else if (i == R.id.btn_dialog_delete) {
            onClickDelete();

        }
    }

    private void onClickDelete() {
        if (mOnClickDeleteListener != null) {
            mOnClickDeleteListener.clickDelete();
            mDialog.cancel();
        }
    }

    public void show() {
        etQuantity.setText("");
        mDialog.show();
    }

    public void showBtnDelete() {
        btnDelete.setVisibility(View.VISIBLE);
    }

    public void hideBtnDelete() {
        btnDelete.setVisibility(View.GONE);
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void cancel() {
        mDialog.cancel();
    }

    public interface OnResultListener {
        void clickOk(double quantity);

        void clickCancel();
    }
}
