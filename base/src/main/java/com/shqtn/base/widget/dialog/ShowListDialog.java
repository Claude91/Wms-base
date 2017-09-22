package com.shqtn.base.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2017-4-10.
 */
public class ShowListDialog {
    private AlertDialog mAlertDialog;
    private ListView lv;

    public ShowListDialog(Context context) {
        init(context);
    }

    private void init(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        lv = new ListView(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lv.setLayoutParams(lp);

        mAlertDialog = builder.setView(lv)
                .setNeutralButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
    }

    public void setAdapter(ListAdapter adapter){
        lv.setAdapter(adapter);
    }

    public void show(){
        mAlertDialog.show();
    }
    public void cancelDialog(){
        mAlertDialog.cancel();
    }
}
