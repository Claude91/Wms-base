package com.shqtn.base.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.shqtn.base.C;
import com.shqtn.base.bean.DepotBean;


/**
 * Created by android on 2017/7/14.
 */

public class DepotUtils {
    static {
        sGson = C.sGson;
    }

    private static Gson sGson;


    private static final String FILE_NAME = "depot";

    private static final String KEY_DEPOT = "DEPOT";


    public static void clear(Context context) {
        PreferencesUtils.deleteKey(context, FILE_NAME, KEY_DEPOT);
    }

    public static void saveDepot(Context context, DepotBean depotBean) {
        String json = sGson.toJson(depotBean);
        PreferencesUtils.saveString(context, FILE_NAME, KEY_DEPOT, json);
    }

    public static DepotBean getDepot(Context context) {
        String json = PreferencesUtils.queryString(context, FILE_NAME, KEY_DEPOT);
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        DepotBean depotBean = sGson.fromJson(json, DepotBean.class);
        return depotBean;
    }

}
