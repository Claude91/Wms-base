package com.shqtn.base.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.base.widget.dialog.EditQuantityDialog;


/**
 * Created by android on 2017/7/11.
 */

public class DialogFactory {


    public static AlertDialog createMsgDialog(Context context) {
        AlertDialog ad = new AlertDialog.Builder(context)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create();
        return ad;
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        return pd;
    }

    public static AskMsgDialog createAskMsgDialog(Context context) {
        return null;
    }

    public static EditQuantityDialog createEditQuantityDialog(Context context) {
        return null;
    }
}
