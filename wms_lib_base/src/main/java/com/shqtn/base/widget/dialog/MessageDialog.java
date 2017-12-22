package com.shqtn.base.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shqtn.base.R;


/**
 * Created by Administrator on 2017-3-28.
 */
public class MessageDialog {

    private Context context;
    private AlertDialog.Builder builder;


    public MessageDialog(Context context, String msg) {
        this.context = context;
        init(msg);
    }

    private OnDialogClickListener mListener;
    private AlertDialog mAlerDialog;

    private void init(String msg) {
        builder = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.dialog_msg, null, false);
        TextView tvMsg = (TextView) itemView.findViewById(R.id.tv_dialogMsg);
        tvMsg.setText(msg);

        itemView.findViewById(R.id.tv_dialogTrue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickTrue();
                }
            }
        });
        itemView.findViewById(R.id.tv_dialogFalse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickFalse();
                }
            }
        });
        builder.setView(itemView);
        mAlerDialog = builder.create();
    }

    public void setMessage(String msg){
        mAlerDialog.setMessage(msg);
    }

    public void show() {
        mAlerDialog.show();
    }
    public void cancel(){
        mAlerDialog.cancel();
    }

    public void setOnDialogClickListener(OnDialogClickListener l) {
        this.mListener = l;
    }

    public interface OnDialogClickListener {
        void clickTrue();

        void clickFalse();
    }


}
