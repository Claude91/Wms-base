package com.shqtn.base;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.shqtn.base.clipboard.ClipBoardManager;

/**
 * Created by android on 2017/9/20.
 */

public abstract class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardmanager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardmanager.addPrimaryClipChangedListener(mClipListener);
        init();
    }

    /**
     * 注册一个剪切板监听器
     */
    private ClipboardManager clipboardmanager;
    private ClipboardManager.OnPrimaryClipChangedListener mClipListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            ClipData clipData = clipboardmanager.getPrimaryClip();
            String content = clipData.getItemAt(0).getText().toString();
            ClipBoardManager manager = ClipBoardManager.getInstance();
            manager.sendMsg(content);
        }
    };

    public abstract void init();

}
