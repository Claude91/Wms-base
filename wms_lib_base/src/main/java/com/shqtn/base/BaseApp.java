package com.shqtn.base;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.shqtn.base.clipboard.ClipBoardManager;
import com.shqtn.base.http.OkHttpUtils;
import com.shqtn.base.utils.LogUtils;
import com.shqtn.base.utils.VibrateHelper;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * Created by android on 2017/9/20.
 */

public abstract class BaseApp extends Application {
    public static BaseApp baseApp;

    public static BaseApp getInstance() {
        return baseApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.setNormalTimeOut();

        clipboardmanager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardmanager.addPrimaryClipChangedListener(mClipListener);
        init();
        CrashHandler.getInstance().init(this);

        VibrateHelper.getInstance().init(this);
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
