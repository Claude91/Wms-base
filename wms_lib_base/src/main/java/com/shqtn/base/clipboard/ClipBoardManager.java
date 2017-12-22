package com.shqtn.base.clipboard;

import android.content.ClipboardManager;

import java.util.LinkedList;
import java.util.List;

/**
 * 剪切板管理
 * Created by android on 2017/9/20.
 */

public class ClipBoardManager {

    private static ClipBoardManager mManager;

    private List<OnClipListener> mClipList;

    private ClipBoardManager() {
        mClipList = new LinkedList<>();
    }

    public static ClipBoardManager getInstance() {
        if (mManager == null) {
            synchronized (ClipboardManager.class) {
                if (mManager == null)
                    mManager = new ClipBoardManager();
            }
        }
        return mManager;

    }

    public void addListener(OnClipListener l) {
        mClipList.add(l);
    }

    public void removeListener(OnClipListener l) {
        mClipList.remove(l);
    }

    /**
     * 发送通知
     *
     * @param content
     */
    public void sendMsg(String content) {
        if (mClipList.size() > 0)
            mClipList.get(mClipList.size() - 1).onClipContent(content);
    }


    public interface OnClipListener {
        void onClipContent(String content);
    }
}
