package com.shqtn.base.clipboard;

import android.os.Handler;
import android.os.Looper;
import android.text.TextWatcher;

import com.shqtn.base.utils.StringUtils;

/**
 * Created by android on 2017/9/20.
 */

public class TextChangeManager {
    public static int SYSTEM_MIN_LENGTH = 6;

    private final static Handler mHandler = new Handler(Looper.getMainLooper());
    private IChangeView mIChangeView;

    private OnTimeAfterTextChangeListener mOnTimeAfterTextChangeListener;


    private long waitingGetTime = 800L;//经过多少时间获取输入框时间


    private Runnable mGetSystemRunnable;
    private String lastText;
    private boolean isDoing;

    public void startReaderTime() {
        if (isDoing) return;
        isDoing = true;
        //开始计数
        if (mGetSystemRunnable == null) {
            mGetSystemRunnable = new Runnable() {
                @Override
                public void run() {
                    if (mIChangeView == null) {
                        isDoing = false;
                        return;
                    }
                    String text = mIChangeView.getText();
                    if (mOnTimeAfterTextChangeListener == null) {
                        isDoing = false;
                        return;
                    }
                    if (StringUtils.isEmpty(text)) {
                        isDoing = false;
                        return;
                    }
                    if (StringUtils.isEmpty(lastText)) {
                        if (text.length() > SYSTEM_MIN_LENGTH) {
                            mOnTimeAfterTextChangeListener.onTextChange(text);
                        }
                    } else {
                        int lastTextLength = lastText.length();
                        int nowTextLength = text.length();
                        //当前是删除
                        if (nowTextLength < lastTextLength) {
                            isDoing = false;
                            return;
                        }
                        //当前录入不足
                        if (nowTextLength - lastTextLength < SYSTEM_MIN_LENGTH) {
                            isDoing = false;
                            return;
                        }

                        String systemInputText = text.substring(lastTextLength, nowTextLength);
                        mOnTimeAfterTextChangeListener.onTextChange(systemInputText);
                    }
                }
            };
        }
        lastText = mIChangeView.getText();
        mHandler.postDelayed(mGetSystemRunnable, waitingGetTime);
    }

    public void setIChangeView(IChangeView mIChangeView) {
        this.mIChangeView = mIChangeView;
    }

    public void setOnTimeAfterTextChangeListener(OnTimeAfterTextChangeListener mOnTimeAfterTextChangeListener) {
        this.mOnTimeAfterTextChangeListener = mOnTimeAfterTextChangeListener;
    }


    public interface IChangeView {
        void clearText();

        String getText();
    }

    public interface OnTimeAfterTextChangeListener {
        void onTextChange(String content);
    }
}
