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
                .create();
        return ad;
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        return pd;
    }

    public static AskMsgDialog createAskMsgDialog(Context context) {
        return new AskMsgDialog(context);
    }

    public static EditQuantityDialog createEditQuantityDialog(Context context) {
        return new EditQuantityDialog(context);
    }
}
