package com.shqtn.base.http;

import com.google.gson.Gson;
import com.shqtn.base.R;
import com.shqtn.base.bean.ResultBean;
import com.shqtn.base.utils.ActivityUtils;
import com.shqtn.base.utils.DataUtils;
import com.shqtn.base.utils.LogUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017-7-10.
 */
public abstract class ResultCallback extends Callback<ResultBean> {


    @Override
    public ResultBean parseNetworkResponse(Response response) throws IOException {
        Gson gson = new Gson();

        String json = response.body().string();

        LogUtils.i("返回参数:" + json);

        return gson.fromJson(json, ResultBean.class);
    }

    @Override
    public void onError(Request request, Exception e) {
        if (e instanceof SocketTimeoutException) {
            onFailed(ActivityUtils.getInstance().getTopAty().getString(R.string.socketTimeOutHint));
        } else if (e instanceof SocketException) {
            onFailed(ActivityUtils.getInstance().getTopAty().getString(R.string.socketException));
        } else if (e instanceof RuntimeException) {
            String content = e.getMessage();
            onFailed(content);
        } else {
            onFailed(ActivityUtils.getInstance().getTopAty().getString(R.string.httpLinkFailed));
        }
    }

    public abstract void onFailed(String msg);

    @Override
    public void onResponse(ResultBean response) {
        onBefore(response);
        if (response.isRs()) {
            onSuccess(response);
        } else {
            onFailed(response.getMessage());
        }
    }

    private void onBefore(ResultBean response) {

    }

    public abstract void onSuccess(ResultBean response);


    public <T> T getData(Object data, Class clazz) {
        return DataUtils.getResultObj(data, clazz);
    }

    public <T> ArrayList<T> getRows(Object rows, Class clazz) {
        return DataUtils.getArrayResult(rows, clazz);
    }
}
