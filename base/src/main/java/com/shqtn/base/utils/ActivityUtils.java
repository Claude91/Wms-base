package com.shqtn.base.utils;

import com.shqtn.base.BaseActivity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017-4-28.
 */
public class ActivityUtils {
    private static ActivityUtils mUtils = null;
    public ArrayList<BaseActivity> atyList;

    public static ActivityUtils getInstance() {
        if (mUtils == null) {
            synchronized (ActivityUtils.class) {
                if (mUtils == null) {
                    mUtils = new ActivityUtils();
                }
            }
        }
        return mUtils;
    }

    private ActivityUtils() {
        atyList = new ArrayList<>();
    }

    public void addAty(BaseActivity aty) {
        atyList.add(aty);
    }

    public void removeAty(BaseActivity aty) {
        atyList.remove(aty);
    }

    public void removeAllAty() {
        atyList.clear();
    }

    public BaseActivity getTopAty() {
        try {
            return atyList.get(atyList.size() - 1);
        } catch (Exception e) {
            return null;
        }
    }

    public void closeOther(BaseActivity loginActivity) {
        for (BaseActivity baseActivity : atyList) {
            if (!baseActivity.equals(loginActivity)) {
                baseActivity.finish();
            }
        }
    }
}
