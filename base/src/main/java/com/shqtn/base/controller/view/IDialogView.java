package com.shqtn.base.controller.view;

import com.shqtn.base.listener.OnClickDeleteListener;
import com.shqtn.base.widget.dialog.AskMsgDialog;
import com.shqtn.base.widget.dialog.EditQuantityDialog;

/**
 * Created by android on 2017/9/20.
 */

public interface IDialogView {

    void displayMsgDialog(String title, String msg);

    void displayMsgDialog(String msg);

    void cancelMsgDialog();

    void displayProgressDialog(String title, String msg);

    void displayProgressDialog(String msg);

    void cancelProgressDialog();

    void displayAskMsgDialog(String msg, AskMsgDialog.OnAskClickListener listener);

    void displayAskMsgDialog(String title, String msg, AskMsgDialog.OnAskClickListener listener);

    void cancelAskMsgDialog();

    void displayEditQty(Double maxQty);

    void displayEditQty(EditQuantityDialog.OnResultListener resultListener);

    void displayEditQtyDelBtn(OnClickDeleteListener l);

    void hideEditQtyDelBtn();

    void displayEditQty(double maxQty, EditQuantityDialog.OnResultListener resultListener);

    void cancelEditQty();
}
