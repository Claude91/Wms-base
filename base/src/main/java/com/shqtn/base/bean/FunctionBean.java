package com.shqtn.base.bean;


import android.app.ListActivity;

import com.shqtn.base.BaseActivity;

/**
 * Created by android on 2017/7/13.
 */

public class FunctionBean {
    /**
     * @param name       功能名称
     * @param srcId      显示图片
     * @param controller 操作
     * @return
     */
    public static FunctionBean newListViewFunction(String name, int srcId, Class controller) {
        FunctionBean function = new FunctionBean();
        function.setClazzName(ListActivity.class.getCanonicalName());
        function.setControllerName(controller.getCanonicalName());
        function.setIconId(srcId);
        function.setName(name);
        return function;
    }

    public static<T extends BaseActivity> FunctionBean newFunction(String title, int srcId, Class<T> aty) {

        FunctionBean fragment = new FunctionBean();
        fragment.setName(title);
        fragment.setClazzName(aty.getCanonicalName());
        fragment.setIconId(srcId);
        return fragment;
    }


    public FunctionBean() {
    }

    public FunctionBean(String name, String title, int iconId, String clazzName) {
        this.name = name;
        this.title = title;
        this.iconId = iconId;
        this.clazzName = clazzName;
    }

    private String name;
    private String title;
    private int iconId;
    private String clazzName;
    private String controllerName;

    public String getControllerName() {
        return controllerName;
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

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }
}
