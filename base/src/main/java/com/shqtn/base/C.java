package com.shqtn.base;

import com.google.gson.Gson;

/**
 * 常量池
 */
public class C {


    /**
     * 初始化默认token=86e2c1a0-aba5-11e6-8ec2-162d27fbcfcd
     * 初始化手持key=b5417be0-aba5-11e6-b9e6-162d27fbcfcd
     */
    public static final String APP_NORMAL_TOKEN = "86e2c1a0-aba5-11e6-8ec2-162d27fbcfcd";
    public static final String APP_NORMAL_KEY = "b5417be0-aba5-11e6-b9e6-162d27fbcfcd";

    public static final int PAGE = 1;
    public static final int PAGE_SIZE = 20;

    public static final Gson sGson = new Gson();

    public static final String MANIFEST_STR = "manifest_str";
    public static final String PRESENTER = "presenter";
    public static final String DECODE_CALLBACK = "decode_callback";
    public static final java.lang.String SCANNING_GOODS_QTY = "scanning_qty";
    public static final String OPERATE_GOODS = "operate_goods";
    public static final String OPERATE_LPN = "operate_lpn";
}
