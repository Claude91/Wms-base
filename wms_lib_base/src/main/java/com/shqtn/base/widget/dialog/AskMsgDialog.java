package com.shqtn.base.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by android on 2017/9/20.
 */

public class AskMsgDialog {


    private String msg;
    private String title;
    private OnAskClickListener onAskClickListener;
    private AlertDialog.Builder mAlertDialogBuilder;
    private AlertDialog mAlertDialog;

    public AskMsgDialog(Context context) {
        mAlertDialogBuilder = new AlertDialog.Builder(context);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOnAskClickListener(OnAskClickListener onAskClickListener) {
        this.onAskClickListener = onAskClickListener;
    }

    public void show() {
        if (mAlertDialog == null) {
            mAlertDialog = mAlertDialogBuilder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (onAskClickListener != null)
                        onAskClickListener.clickTrue();
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (onAskClickListener != null)
                        onAskClickListener.clickCancel();
                }
            }).create();
        }

        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(msg);
        mAlertDialog.show();
    }

    public void cancel() {
        if (mAlertDialog == null)
            return;

        if (mAlertDialog.isShowing()) {
            mAlertDialog.cancel();

        }
    }

    public boolean isShowing() {
        return true;
    }

    public interface OnAskClickListener {
        void clickTrue();

        void clickCancel();

    }
}
