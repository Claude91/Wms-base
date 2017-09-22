package com.shqtn.base.info.code.mode;

import com.shqtn.base.http.ModelService;
import com.zhy.http.okhttp.callback.Callback;

import com.shqtn.base.bean.params.CodeParams;
import com.shqtn.base.info.ApiUrl;

/**
 * Created by android on 2017/7/14.
 */

public class CodeModel {

    private static CodeParams sCodeParams;

    public static void queryCode(String code, Callback callback) {
        if (sCodeParams == null) {
            sCodeParams = new CodeParams();
        }
        sCodeParams.setPbarcode(code);
        ModelService.post(ApiUrl.URL_BASE_CODE, sCodeParams, callback);
    }
}
