package com.shqtn.base.http;

import com.squareup.okhttp.OkHttpClient;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017-7-5.
 */
public class OkHttpUtils {
    private static OkHttpUtils mInstance;

    private OkHttpUtils(){
        OkHttpClient okHttpClient = com.zhy.http.okhttp.OkHttpUtils.getInstance().getOkHttpClient();
        okHttpClient.setConnectTimeout(20_000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(20_000, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(20_000, TimeUnit.MILLISECONDS);
    }
    public static void addHeaderParams(Map<String,String> headerParams){
        PostStringBuilder.setInitHeaderParams(headerParams);
    }

    public static PostStringBuilder post(){
        return new PostStringBuilder();
    }

    public static void cancel(Object tag){
        com.zhy.http.okhttp.OkHttpUtils.getInstance().cancelTag(tag);
    }


}
