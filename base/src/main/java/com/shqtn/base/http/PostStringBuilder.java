package com.shqtn.base.http;

import com.shqtn.base.utils.JsonUtils;
import com.shqtn.base.utils.LogUtils;
import com.squareup.okhttp.MediaType;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.IdentityHashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017-7-5.
 */
public class PostStringBuilder {
    private com.zhy.http.okhttp.builder.PostStringBuilder builder;
    private final static String MEDIA_TYPE_CONTENT_TYPE = "application/json; charset=utf-8";

    private static Map<String, String> sHeaderParams;
    private boolean isAddHeaderInitParams;//是否添加默认的头信息

    private Map<String, String> mParams;
    private Object mParamsObj;

    public PostStringBuilder() {
        isAddHeaderInitParams = true;
        builder = new com.zhy.http.okhttp.builder.PostStringBuilder();
        builder.mediaType(MediaType.parse(MEDIA_TYPE_CONTENT_TYPE));
    }

    public static final void setInitHeaderParams(Map<String, String> headerParams) {
        sHeaderParams = headerParams;
    }

    public PostStringBuilder content(String content) {
        builder.content(content);
        return this;
    }

    private PostStringBuilder mediaType(MediaType mediaType) {
        builder.mediaType(mediaType);
        return this;
    }

    public PostStringBuilder setAddInitParams(boolean isAdd) {
        isAddHeaderInitParams = isAdd;
        return this;
    }

    public RequestCall build() {
        if (isAddHeaderInitParams && sHeaderParams != null) {
            headers(sHeaderParams);
        }
        String content = null;
        if (mParamsObj != null) {

            content = JsonUtils.toJson(mParamsObj);

        } else if (mParams == null) {

            content = "";

        } else {

            content = JsonUtils.toJson(mParams);

        }
        builder.content(content);
        builder.params(mParams);
        LogUtils.i("请求参数" + content);
        return builder.build();
    }

    public PostStringBuilder url(String url) {
        builder.url(url);
        return this;
    }

    public PostStringBuilder tag(Object tag) {
        builder.tag(tag);
        return this;
    }

    public PostStringBuilder params(Map<String, String> params) {
        mParams = params;
        return this;
    }

    public PostStringBuilder params(Object params) {
        mParamsObj = params;
        return this;
    }

    public PostStringBuilder addParams(String key, String val) {
        builder.addParams(key, val);
        if (mParams == null) {
            mParams = new IdentityHashMap<>();
        }
        mParams.put(key, val);
        return this;
    }

    public PostStringBuilder headers(Map<String, String> headers) {
        builder.headers(headers);
        return this;
    }

    public PostStringBuilder addHeader(String key, String val) {
        builder.addHeader(key, val);
        return this;
    }
}
