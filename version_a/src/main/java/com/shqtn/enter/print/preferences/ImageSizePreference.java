package com.shqtn.enter.print.preferences;

import android.content.Context;

import com.shqtn.base.utils.JsonUtils;
import com.shqtn.base.utils.PreferencesUtils;
import com.shqtn.base.utils.StringUtils;
import com.shqtn.enter.print.bean.ImageSize;

/**
 * 创建时间:2017/12/4
 * 描述:
 *
 * @author ql
 */

public class ImageSizePreference {

    public static final String FILE_NAME = "print_image_size";
    public static final String KEY_SIZE = "image_size_key";

    public static void save(Context context, ImageSize imageSize) {
        String imageSizeJson = JsonUtils.toJson(imageSize);
        PreferencesUtils.saveString(context, FILE_NAME, KEY_SIZE, imageSizeJson);
    }

    public static ImageSize getImageSize(Context context) {
        String imageSizeJson = PreferencesUtils.queryString(context, FILE_NAME, KEY_SIZE);
        if (StringUtils.isEmpty(imageSizeJson)) {
            return null;
        }

        return JsonUtils.getObject(imageSizeJson, ImageSize.class);
    }
}
