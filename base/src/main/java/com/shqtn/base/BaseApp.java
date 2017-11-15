package com.shqtn.base;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.shqtn.base.clipboard.ClipBoardManager;

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
        clipboardmanager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardmanager.addPrimaryClipChangedListener(mClipListener);
        init();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.loop();
                handler = new Handler();
                Looper.prepare();
            }
        }).start();
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

    Handler handler;
    public static final String name = "wms_error.txt";

    public void saveError(final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();

                File file = new File(absolutePath, name);
                if (!file.exists()) {
                    try {
                        if (file.createNewFile()) {
                            return;
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                FileOutputStream fileOutputStream = null;
                BufferedWriter bw = null;
                try {
                    fileOutputStream = new FileOutputStream(file, true);
                    bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    e.printStackTrace(new PrintStream(baos));
                    StringBuilder sb = new StringBuilder();

                    sb.append("*****************************************************************************").
                            append("错误时间:")
                            .append(System.currentTimeMillis()).append("\r\n").append(baos.toString());
                    bw.write(sb.toString());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    public void saveError(final String e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();

                File file = new File(absolutePath, name);
                if (!file.exists()) {
                    try {
                        if (file.createNewFile()) {
                            return;
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                FileOutputStream fileOutputStream = null;
                BufferedWriter bw = null;
                try {
                    fileOutputStream = new FileOutputStream(file, true);
                    bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    bw.write(e);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (bw != null) {
                        try {
                            bw.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    public abstract void init();

}
