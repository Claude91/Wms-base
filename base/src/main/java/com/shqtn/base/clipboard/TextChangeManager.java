package com.shqtn.base.clipboard;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.shqtn.base.utils.StringUtils;

/**
 * Created by android on 2017/9/20.
 */

public class TextChangeManager {
    public static int SYSTEM_MIN_LENGTH = 6;

    private final static Handler mHandler = new Handler(Looper.getMainLooper());
    private IChangeView mIChangeView;

    private boolean isTextInputSearch;//用于判断是否已经输入 查询过

    private OnTimeAfterTextChangeListener mOnTimeAfterTextChangeListener;


    private long waitingGetTime = 800L;//经过多少时间获取输入框时间


    private Runnable mGetSystemRunnable;
    private String lastText;
    private boolean isDoing;

    private String readText;//解码得到的字符串
    private long readTime; //得到解码的字符串时间 ；

    public void startReaderTime() {
        if (isTextInputSearch) {
            isTextInputSearch = false;
            return;
        }
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


    /**
     * 由于symbol 品牌的SDK 测试调不通，监听输入框输入字符数量，
     * symbol 输入模式 类似于 在中文输入法中，输入英文 + enter键直接赋值到 输入框中。
     *
     * @param s     输入后输入框内容
     * @param start 开始输入的位置
     * @param end
     * @param count 输入字符的数量
     */
    public void textChange(CharSequence s, int start, int end, int count) {
        if (count > SYSTEM_MIN_LENGTH) {
            CharSequence charSequence = s.subSequence(start, s.length());
            mOnTimeAfterTextChangeListener.onTextChange(charSequence.toString());
            isTextInputSearch = true;
            readText = charSequence.toString();
            readTime = System.currentTimeMillis();
        }
    }

    public void setIChangeView(IChangeView mIChangeView) {
        this.mIChangeView = mIChangeView;
    }

    public void setOnTimeAfterTextChangeListener(OnTimeAfterTextChangeListener mOnTimeAfterTextChangeListener) {
        this.mOnTimeAfterTextChangeListener = mOnTimeAfterTextChangeListener;
    }


    /**
     * 是否经过当前读码器
     *
     * @param text     解码 字符串
     * @param readTime 解码时间
     * @return
     */
    public boolean isPassRead(String text, long readTime) {
        if (Math.abs(this.readTime - readTime) > 600) {
            return false;
        }

        if (this.readText == null) {
            return false;
        }

        if (readText.length() != text.length()) {
            return false;
        }

        if (!readText.equals(text)) {
            return false;
        }

        return true;
    }

    public interface IChangeView {
        void clearText();

        String getText();
    }

    public interface OnTimeAfterTextChangeListener {
        void onTextChange(String content);
    }
}
