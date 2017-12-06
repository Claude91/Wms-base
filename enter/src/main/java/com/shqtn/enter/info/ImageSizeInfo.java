package com.shqtn.enter.info;

import android.content.Context;

import com.shqtn.enter.print.bean.ImageSize;
import com.shqtn.enter.print.preferences.ImageSizePreference;

/**
 * 创建时间:2017/12/4
 * 描述:
 *
 * @author ql
 */

public class ImageSizeInfo {
    public static final int X_START = 50;
    public static final int X_END = 500;
    public static final int Y_START = 40;
    public static final int Y_END = 400;

    /**
     * 打印大小进行初始化操作
     *
     * @param context
     */
    public static void initPrintImageSize(Context context) {
        ImageSize imageSize = ImageSizePreference.getImageSize(context);
        if (imageSize != null) {
            return;
        }

        resetImageSize(context);
    }

    /**
     * 使用默认大小
     *
     * @param context
     */
    public static void resetImageSize(Context context) {
        ImageSize imageSize = new ImageSize();
        imageSize.setStartY(Y_START);
        imageSize.setStartX(X_START);
        imageSize.setEndY(Y_END);
        imageSize.setEndX(X_END);
        ImageSizePreference.save(context, imageSize);

    }
}
