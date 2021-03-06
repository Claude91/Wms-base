package com.shqtn.enter;

import android.os.Bundle;

import com.shqtn.base.BaseActivity;
import com.shqtn.base.C;

/**
 * Created by android on 2017/7/13.
 */

public class FunctionBean {
    /**
     * @param label      功能名称
     * @param srcId      显示图片
     * @param controller 操作
     * @return
     */
    public static FunctionBean newListViewFunction(String label, int srcId, Class controller) {
        FunctionBean function = new FunctionBean();
        function.setAtyClazzName(ListActivity.class.getCanonicalName());
        function.setControllerName(controller.getCanonicalName());
        function.setIconId(srcId);
        function.setName(label);
        return function;
    }

    public static <T extends BaseActivity> FunctionBean newFunction(String title, int srcId, Class<T> aty) {

        FunctionBean fragment = new FunctionBean();
        fragment.setName(title);
        fragment.setAtyClazzName(aty.getCanonicalName());
        fragment.setIconId(srcId);
        return fragment;
    }


    public FunctionBean() {
    }

    private Bundle bundle = new Bundle();
    private String name;
    private String title;
    private int iconId;
    private String atyClazzName;
    private String controllerName;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getAtyClazzName() {
        return atyClazzName;
    }

    public void setAtyClazzName(String atyClazzName) {
        this.atyClazzName = atyClazzName;
    }
}
