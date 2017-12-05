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
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 554;
    public static final int BOTTOM_LEFT = 0;
    public static final int BOTTOM_RIGHT = 554;

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
        imageSize.setStartY(TOP_LEFT);
        imageSize.setStartX(TOP_RIGHT);
        imageSize.setEndY(BOTTOM_LEFT);
        imageSize.setEndX(BOTTOM_RIGHT);
        ImageSizePreference.save(context, imageSize);

    }
}
