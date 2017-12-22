package com.shqtn.base.http;

import android.support.annotation.NonNull;

import com.shqtn.base.utils.ActivityUtils;
import com.shqtn.base.utils.LogUtils;
import com.zhy.http.okhttp.callback.Callback;


/**
 * Created by android on 2017/7/13.
 */

public class ModelService {

    public static void post(@NonNull String url, Object params, Callback callback) {
        LogUtils.i("请求地址:" + url);
        try {
            OkHttpUtils.post().params(params)
                    .url(url)
                    .tag(ActivityUtils.getInstance().getTopAty())
                    .build()
                    .execute(callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(null, e);
        }
    }
}
