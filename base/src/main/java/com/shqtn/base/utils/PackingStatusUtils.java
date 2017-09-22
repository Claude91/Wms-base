package com.shqtn.base.utils;

/**
 * Created by android on 2017/8/2.
 */

public class PackingStatusUtils {
    public static final String STATUS_NOT_TAKE = "0";//未装箱
    public static final String STATUS_TAKING = "1";//装箱中
    public static final String STATUS_TAKE_OVER = "2";//装箱完成
    public static final String STATUS_OVER_OUT_DEPOT = "3";//已经出库

    public static String getStatus(String status) {
        String statusStr = null;
        switch (status) {
            case STATUS_NOT_TAKE:
                statusStr = "未装箱";
                break;
            case STATUS_OVER_OUT_DEPOT:
                statusStr = "已出库";
                break;
            case STATUS_TAKE_OVER:
                statusStr = "装箱完成";
                break;
            case STATUS_TAKING:
                statusStr = "装箱中";
                break;
        }
        return statusStr;
    }
}
