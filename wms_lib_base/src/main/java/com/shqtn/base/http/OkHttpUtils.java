package com.shqtn.base.http;

import com.squareup.okhttp.OkHttpClient;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017-7-5.
 */
public class OkHttpUtils {
    private static OkHttpUtils mInstance;

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public void setNormalTimeOut() {
        OkHttpClient okHttpClient = com.zhy.http.okhttp.OkHttpUtils.getInstance().getOkHttpClient();
        okHttpClient.setConnectTimeout(40_000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(40_000, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(40_000, TimeUnit.MILLISECONDS);
    }

    private OkHttpUtils() {

    }

    public static void addHeaderParams(Map<String, String> headerParams) {
        PostStringBuilder.setInitHeaderParams(headerParams);
    }

    public static PostStringBuilder post() {
        return new PostStringBuilder();
    }

    public static void cancel(Object tag) {
        com.zhy.http.okhttp.OkHttpUtils.getInstance().cancelTag(tag);
    }


}
