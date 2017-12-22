package com.shqtn.base.info.code.help;

import android.support.annotation.IntDef;

import com.shqtn.base.BaseApp;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.http.ResultCallback;
import com.shqtn.base.info.code.AllotBean;
import com.shqtn.base.info.code.CodeGoods;
import com.shqtn.base.info.code.CodeLpn;
import com.shqtn.base.info.code.CodeManifest;
import com.shqtn.base.info.code.CodeRack;
import com.shqtn.base.info.code.CodeUtils;
import com.shqtn.base.utils.MediaPlayUtils;
import com.shqtn.base.utils.VibrateHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by android on 2017/7/14.
 */


public class CodeCallback extends ResultCallback {
    @IntDef({TAG_GOODS, TAG_LPN, TAG_RACK, TAG_MANIFEST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanningTag {
    }

    public static final int TAG_GOODS = 0X110;
    public static final int TAG_LPN = 0X111;
    public static final int TAG_RACK = 0X112;
    public static final int TAG_MANIFEST = 0X113;

    private int[] mTags;
    private OnCodeListener mOnCodeListener;

    public interface OnCodeListener {
        void onFailed(String msg);

        void onGoods(CodeGoods goods);

        void onLpn(CodeLpn lpn);

        void onRack(CodeRack rack);

        void onManifest(CodeManifest manifest);

        void onOtherCode(AllotBean codeBean);
    }

    /**
     * 需要传入想要获得的标签类型，不传则获得全部
     *
     * @param tags
     */
    public CodeCallback(@ScanningTag int... tags) {
        mTags = tags;
    }

    public CodeCallback() {
    }

    public CodeCallback(OnCodeListener codeListener) {
        this.mOnCodeListener = codeListener;
    }

    public void setOnCodeListener(OnCodeListener l) {
        this.mOnCodeListener = l;
    }

    public void setTags(int... tags) {
        mTags = tags;
    }

    public void onGoods(CodeGoods goods) {
        if (mOnCodeListener != null) mOnCodeListener.onGoods(goods);
    }

    public void onLpn(CodeLpn lpn) {
        if (mOnCodeListener != null) mOnCodeListener.onLpn(lpn);

    }

    public void onRack(CodeRack rack) {
        if (mOnCodeListener != null) mOnCodeListener.onRack(rack);

    }

    public void onManifest(CodeManifest manifest) {
        if (mOnCodeListener != null) mOnCodeListener.onManifest(manifest);

    }


    @Override
    public void onSuccess(ResultBean response) {
        AllotBean codeBean = getData(response.getData(), AllotBean.class);
        if (CodeUtils.isGoods(codeBean) && isHasTag(TAG_GOODS)) {
            onGoods(CodeUtils.getGoods(codeBean));
        } else if (CodeUtils.isLpn(codeBean) && isHasTag(TAG_LPN)) {
            onLpn(CodeUtils.getLpn(codeBean));
        } else if (CodeUtils.isManifest(codeBean) && isHasTag(TAG_MANIFEST)) {
            onManifest(CodeUtils.getManifest(codeBean));
        } else if (CodeUtils.isRack(codeBean) && isHasTag(TAG_RACK)) {
            onRack(CodeUtils.getRack(codeBean));
        } else {
            onOtherCode(codeBean);
        }

    }

    /**
     * 判断是否 包含当前的tag
     *
     * @param tag
     * @return
     */
    private boolean isHasTag(int tag) {
        if (isTagsEmpty()) return true;
        for (int i = 0; i < mTags.length; i++) {
            if (mTags[i] == tag) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onFailed(String msg) {
        if (mOnCodeListener != null) mOnCodeListener.onFailed(msg);
    }


    public void onOtherCode(AllotBean codeBean) {
        MediaPlayUtils.getInstance().playError(BaseApp.getInstance());
        VibrateHelper.getInstance().vibrate();
        if (mOnCodeListener != null) mOnCodeListener.onOtherCode(codeBean);

    }


    private boolean isTagsEmpty() {
        if (mTags == null || mTags.length == 0) {
            return true;
        }
        return false;
    }

}
