package com.shqtn.base.utils;

/**
 * Created by android on 2017/8/11.
 */

public class LpnStatusUtils {

    //（10：未收货；20：已收货；30：理货完成；40：已上架；50：已下架；60：已出库；
    private static final String plan = "10";
    private static final String over_take = "20";
    private static final String over_sorting = "30";
    private static final String over_rack_up = "40";
    private static final String over_rack_down = "50";
    private static final String over_depot_out = "60";

    public static String getStatus(String status) {
        switch (status) {
            case plan:
                return "未收货";
            case over_take:
                return "已收货";
            case over_sorting:
                return "理货完成";
            case over_rack_up:
                return "已上架";
            case over_rack_down:
                return "已下架";
            case over_depot_out:
                return "已出库";
        }
        return "";
    }
}
