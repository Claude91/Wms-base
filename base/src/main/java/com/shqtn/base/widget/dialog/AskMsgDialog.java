package com.shqtn.base.widget.dialog;

/**
 * Created by android on 2017/9/20.
 */

public class AskMsgDialog {


    private String msg;
    private String title;
    private OnAskClickListener onAskClickListener;

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
        
    }

    public void cancel() {
        
    }

    public boolean isShowing() {
        return true;
    }

    public interface OnAskClickListener{

    }
}
